package org.tiger.codegen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.tiger.cfg.*;
import org.tiger.cfg.statements.*;

import java.util.Map;

/**
 * 字节码生成器，负责将CFG转换为JVM字节码
 */
public class BytecodeGenerator {
    private static final int CLASS_VERSION = Opcodes.V11;
    
    // 符号表，用于管理变量索引
    private final Map<String, Integer> variableIndices = new java.util.HashMap<>();
    
    /**
     * 生成类的字节码
     * @param className 类名
     * @param methodCFGs 方法的CFG集合
     * @return 类的字节码
     */
    public byte[] generateClass(String className, Map<String, MethodCFG> methodCFGs) {
        // 创建ClassWriter，计算栈帧和局部变量表
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        
        // 生成类的基本信息
        cw.visit(CLASS_VERSION, 
                Opcodes.ACC_PUBLIC, 
                className.replace('.', '/'), 
                null, 
                "java/lang/Object", 
                null);
        
        // 生成默认构造函数
        generateDefaultConstructor(cw, className);
        
        // 生成所有方法
        for (Map.Entry<String, MethodCFG> entry : methodCFGs.entrySet()) {
            generateMethod(cw, entry.getKey(), entry.getValue());
        }
        
        // 完成类的生成
        cw.visitEnd();
        return cw.toByteArray();
    }
    
    /**
     * 生成默认构造函数
     */
    private void generateDefaultConstructor(ClassWriter cw, String className) {
        MethodVisitor mv = cw.visitMethod(
                Opcodes.ACC_PUBLIC,
                "<init>",
                "()V",
                null,
                null);
        
        mv.visitCode();
        // 调用父类构造函数
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, 
                "java/lang/Object", 
                "<init>", 
                "()V", 
                false);
        mv.visitInsn(Opcodes.RETURN);
        
        // 设置最大栈深度和局部变量表大小
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
    
    /**
     * 生成方法
     */
    private void generateMethod(ClassWriter cw, String methodName, MethodCFG methodCFG) {
        MethodVisitor mv = cw.visitMethod(
                Opcodes.ACC_PUBLIC | (methodName.equals("main") ? Opcodes.ACC_STATIC : 0),
                methodName,
                methodCFG.getDescriptor(),
                null,
                null);
        
        mv.visitCode();
        
        // 生成方法体的字节码
        generateMethodBody(mv, methodCFG);
        
        // 设置最大栈深度和局部变量表大小
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
    
    /**
     * 生成方法体
     */
    private void generateMethodBody(MethodVisitor mv, MethodCFG methodCFG) {
        // 遍历基本块
        for (Map.Entry<String, BasicBlock> entry : methodCFG.getBasicBlocks().entrySet()) {
            BasicBlock block = entry.getValue();
            
            // 生成标签
            mv.visitLabel(new org.objectweb.asm.Label());
            
            // 生成基本块中的语句
            for (Statement stmt : block.getStatements()) {
                generateStatement(mv, stmt);
            }
        }
    }
    
    /**
     * 生成语句的字节码
     */
    private void generateStatement(MethodVisitor mv, Statement stmt) {
        switch (stmt.getType()) {
            case ASSIGN:
                generateAssignStatement(mv, (AssignStatement) stmt);
                break;
            case CALL:
                generateCallStatement(mv, (CallStatement) stmt);
                break;
            case RETURN:
                generateReturnStatement(mv, (ReturnStatement) stmt);
                break;
            case PRINT:
                generatePrintStatement(mv, (PrintStatement) stmt);
                break;
            case NEW_OBJECT:
                generateNewObjectStatement(mv, (NewObjectStatement) stmt);
                break;
            case PLUS:
                generatePlusStatement(mv, (PlusStatement) stmt);
                break;
            case MINUS:
                generateMinusStatement(mv, (MinusStatement) stmt);
                break;
            case TIMES:
                generateTimesStatement(mv, (TimesStatement) stmt);
                break;
            case LESS_THAN:
                generateLessThanStatement(mv, (LessThanStatement) stmt);
                break;
            case AND:
                generateAndStatement(mv, (AndStatement) stmt);
                break;
            case NOT:
                generateNotStatement(mv, (NotStatement) stmt);
                break;
            case INTEGER_LITERAL:
                generateIntegerLiteralStatement(mv, (IntegerLiteralStatement) stmt);
                break;
            case TRUE:
                generateTrueStatement(mv, (TrueStatement) stmt);
                break;
            case FALSE:
                generateFalseStatement(mv, (FalseStatement) stmt);
                break;
            case THIS:
                generateThisStatement(mv, (ThisStatement) stmt);
                break;
            default:
                throw new IllegalArgumentException("Unsupported statement type: " + stmt.getType());
        }
    }
    
    /**
     * 生成赋值语句的字节码
     */
    private void generateAssignStatement(MethodVisitor mv, AssignStatement stmt) {
        // 获取变量的索引
        int varIndex = getVariableIndex(stmt.getTarget());
        
        // 生成右值的字节码
        String source = stmt.getSource();
        if (source.matches("-?\\d+")) {
            // 如果是整数常量
            int value = Integer.parseInt(source);
            if (value >= -1 && value <= 5) {
                mv.visitInsn(Opcodes.ICONST_0 + value);
            } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
                mv.visitIntInsn(Opcodes.BIPUSH, value);
            } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                mv.visitIntInsn(Opcodes.SIPUSH, value);
            } else {
                mv.visitLdcInsn(value);
            }
        } else {
            generateExpression(mv, source);
        }
        
        // 存储到局部变量
        mv.visitVarInsn(Opcodes.ISTORE, varIndex);
    }
    
    /**
     * 生成方法调用语句的字节码
     */
    private void generateCallStatement(MethodVisitor mv, CallStatement stmt) {
        // 如果有目标变量，先加载到栈上
        if (stmt.getTarget() != null) {
            mv.visitVarInsn(Opcodes.ALOAD, getVariableIndex(stmt.getTarget()));
        }
        
        // 加载方法参数
        for (String arg : stmt.getArguments()) {
            generateExpression(mv, arg);
        }
        
        // 调用方法
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/Object",
                stmt.getMethodName(),
                "()V",
                false);
    }
    
    /**
     * 生成返回语句的字节码
     */
    private void generateReturnStatement(MethodVisitor mv, ReturnStatement stmt) {
        if (stmt.getValue() != null) {
            generateExpression(mv, stmt.getValue());
        }
        mv.visitInsn(Opcodes.RETURN);
    }
    
    /**
     * 生成打印语句的字节码
     */
    private void generatePrintStatement(MethodVisitor mv, PrintStatement stmt) {
        // 加载System.out
        mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;");
        
        // 生成要打印的表达式的字节码
        generateExpression(mv, stmt.getSource());
        
        // 检查是否需要类型转换
        if (stmt.getSource().matches("-?\\d+")) {
            // 如果是整数，转换为字符串
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "java/lang/String",
                    "valueOf",
                    "(I)Ljava/lang/String;",
                    false);
        }
        
        // 调用println方法
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",
                false);
    }
    
    /**
     * 生成创建对象语句的字节码
     */
    private void generateNewObjectStatement(MethodVisitor mv, NewObjectStatement stmt) {
        mv.visitTypeInsn(Opcodes.NEW, stmt.getClassName().replace('.', '/'));
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
                stmt.getClassName().replace('.', '/'),
                "<init>",
                "()V",
                false);
    }
    
    /**
     * 生成加法语句的字节码
     */
    private void generatePlusStatement(MethodVisitor mv, PlusStatement stmt) {
        generateExpression(mv, stmt.getLeft());
        generateExpression(mv, stmt.getRight());
        mv.visitInsn(Opcodes.IADD);
    }
    
    /**
     * 生成减法语句的字节码
     */
    private void generateMinusStatement(MethodVisitor mv, MinusStatement stmt) {
        generateExpression(mv, stmt.getLeft());
        generateExpression(mv, stmt.getRight());
        mv.visitInsn(Opcodes.ISUB);
    }
    
    /**
     * 生成乘法语句的字节码
     */
    private void generateTimesStatement(MethodVisitor mv, TimesStatement stmt) {
        generateExpression(mv, stmt.getLeft());
        generateExpression(mv, stmt.getRight());
        mv.visitInsn(Opcodes.IMUL);
    }
    
    /**
     * 生成小于比较语句的字节码
     */
    private void generateLessThanStatement(MethodVisitor mv, LessThanStatement stmt) {
        generateExpression(mv, stmt.getLeft());
        generateExpression(mv, stmt.getRight());
        mv.visitInsn(Opcodes.ISUB);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.IF_ICMPLT, new org.objectweb.asm.Label());
    }
    
    /**
     * 生成与运算语句的字节码
     */
    private void generateAndStatement(MethodVisitor mv, AndStatement stmt) {
        generateExpression(mv, stmt.getLeft());
        generateExpression(mv, stmt.getRight());
        mv.visitInsn(Opcodes.IAND);
    }
    
    /**
     * 生成非运算语句的字节码
     */
    private void generateNotStatement(MethodVisitor mv, NotStatement stmt) {
        generateExpression(mv, stmt.getExpr());
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitInsn(Opcodes.IXOR);
    }
    
    /**
     * 生成整数字面量语句的字节码
     */
    private void generateIntegerLiteralStatement(MethodVisitor mv, IntegerLiteralStatement stmt) {
        int value = stmt.getValue();
        if (value >= -1 && value <= 5) {
            mv.visitInsn(Opcodes.ICONST_0 + value);
        } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.BIPUSH, value);
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.SIPUSH, value);
        } else {
            mv.visitLdcInsn(value);
        }
    }
    
    /**
     * 生成true语句的字节码
     */
    private void generateTrueStatement(MethodVisitor mv, TrueStatement stmt) {
        mv.visitInsn(Opcodes.ICONST_1);
    }
    
    /**
     * 生成false语句的字节码
     */
    private void generateFalseStatement(MethodVisitor mv, FalseStatement stmt) {
        mv.visitInsn(Opcodes.ICONST_0);
    }
    
    /**
     * 生成this语句的字节码
     */
    private void generateThisStatement(MethodVisitor mv, ThisStatement stmt) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
    }
    
    /**
     * 生成表达式的字节码
     */
    private void generateExpression(MethodVisitor mv, String expr) {
        // 检查是否是变量
        if (variableIndices.containsKey(expr)) {
            int varIndex = getVariableIndex(expr);
            mv.visitVarInsn(Opcodes.ILOAD, varIndex);
            // 如果是打印语句的参数，需要转换为字符串
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "java/lang/String",
                    "valueOf",
                    "(I)Ljava/lang/String;",
                    false);
            return;
        }
        
        // 检查是否是整数常量
        try {
            int value = Integer.parseInt(expr);
            if (value >= -1 && value <= 5) {
                mv.visitInsn(Opcodes.ICONST_0 + value);
            } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
                mv.visitIntInsn(Opcodes.BIPUSH, value);
            } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                mv.visitIntInsn(Opcodes.SIPUSH, value);
            } else {
                mv.visitLdcInsn(value);
            }
            // 如果是打印语句的参数，需要转换为字符串
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "java/lang/String",
                    "valueOf",
                    "(I)Ljava/lang/String;",
                    false);
            return;
        } catch (NumberFormatException e) {
            // 不是整数常量，继续检查其他类型
        }
        
        // 检查是否是布尔常量
        if (expr.equals("true")) {
            mv.visitInsn(Opcodes.ICONST_1);
            return;
        }
        if (expr.equals("false")) {
            mv.visitInsn(Opcodes.ICONST_0);
            return;
        }
        
        // 检查是否是字符串常量
        if (expr.startsWith("\"") && expr.endsWith("\"")) {
            mv.visitLdcInsn(expr.substring(1, expr.length() - 1));
            return;
        }
        
        // 检查是否是this
        if (expr.equals("this")) {
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            return;
        }
        
        // 检查是否是字段访问
        if (expr.contains(".") && !expr.contains("(")) {
            String[] parts = expr.split("\\.");
            if (parts.length == 2) {
                // 加载对象
                generateExpression(mv, parts[0]);
                // 访问字段
                mv.visitFieldInsn(Opcodes.GETFIELD,
                        "java/lang/Object",
                        parts[1],
                        "I"); // 默认使用int类型
                return;
            }
        }
        
        // 检查是否是方法调用
        if (expr.contains(".")) {
            String[] parts = expr.split("\\.");
            if (parts.length == 2) {
                // 加载对象
                generateExpression(mv, parts[0]);
                // 调用方法
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/Object",
                        parts[1],
                        "()V",
                        false);
                return;
            }
        }
        
        // 检查是否是算术运算
        if (expr.contains("+")) {
            String[] operands = expr.split("\\+");
            generateArithmeticOperation(mv, operands[0], operands[1], Opcodes.IADD);
            return;
        }
        if (expr.contains("-")) {
            String[] operands = expr.split("-");
            generateArithmeticOperation(mv, operands[0], operands[1], Opcodes.ISUB);
            return;
        }
        if (expr.contains("*")) {
            String[] operands = expr.split("\\*");
            generateArithmeticOperation(mv, operands[0], operands[1], Opcodes.IMUL);
            return;
        }
        if (expr.contains("/")) {
            String[] operands = expr.split("/");
            generateArithmeticOperation(mv, operands[0], operands[1], Opcodes.IDIV);
            return;
        }
        
        throw new IllegalArgumentException("Unsupported expression: " + expr);
    }
    
    /**
     * 生成算术运算的字节码
     */
    private void generateArithmeticOperation(MethodVisitor mv, String left, String right, int opcode) {
        // 生成左操作数的字节码
        generateExpression(mv, left);
        // 生成右操作数的字节码
        generateExpression(mv, right);
        // 生成运算指令
        mv.visitInsn(opcode);
    }
    
    /**
     * 生成比较运算的字节码
     */
    private void generateComparisonOperation(MethodVisitor mv, String left, String right, int opcode) {
        // 生成左操作数的字节码
        generateExpression(mv, left);
        // 生成右操作数的字节码
        generateExpression(mv, right);
        // 创建标签
        org.objectweb.asm.Label trueLabel = new org.objectweb.asm.Label();
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();
        // 生成比较指令
        mv.visitJumpInsn(opcode, trueLabel);
        // 结果为false
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        // 结果为true
        mv.visitLabel(trueLabel);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(endLabel);
    }
    
    /**
     * 生成逻辑与运算的字节码
     */
    private void generateLogicalAndOperation(MethodVisitor mv, String left, String right) {
        // 创建标签
        org.objectweb.asm.Label falseLabel = new org.objectweb.asm.Label();
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();
        
        // 生成左操作数的字节码
        generateExpression(mv, left);
        // 如果左操作数为false，直接跳转到false标签
        mv.visitJumpInsn(Opcodes.IFEQ, falseLabel);
        
        // 生成右操作数的字节码
        generateExpression(mv, right);
        // 如果右操作数为false，跳转到false标签
        mv.visitJumpInsn(Opcodes.IFEQ, falseLabel);
        
        // 结果为true
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        
        // 结果为false
        mv.visitLabel(falseLabel);
        mv.visitInsn(Opcodes.ICONST_0);
        
        mv.visitLabel(endLabel);
    }
    
    /**
     * 生成逻辑或运算的字节码
     */
    private void generateLogicalOrOperation(MethodVisitor mv, String left, String right) {
        // 创建标签
        org.objectweb.asm.Label trueLabel = new org.objectweb.asm.Label();
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();
        
        // 生成左操作数的字节码
        generateExpression(mv, left);
        // 如果左操作数为true，直接跳转到true标签
        mv.visitJumpInsn(Opcodes.IFNE, trueLabel);
        
        // 生成右操作数的字节码
        generateExpression(mv, right);
        // 如果右操作数为true，跳转到true标签
        mv.visitJumpInsn(Opcodes.IFNE, trueLabel);
        
        // 结果为false
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        
        // 结果为true
        mv.visitLabel(trueLabel);
        mv.visitInsn(Opcodes.ICONST_1);
        
        mv.visitLabel(endLabel);
    }
    
    /**
     * 生成逻辑非运算的字节码
     */
    private void generateLogicalNotOperation(MethodVisitor mv, String expr) {
        // 生成操作数的字节码
        generateExpression(mv, expr);
        // 创建标签
        org.objectweb.asm.Label trueLabel = new org.objectweb.asm.Label();
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();
        
        // 如果操作数为true，跳转到true标签
        mv.visitJumpInsn(Opcodes.IFNE, trueLabel);
        
        // 结果为true
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        
        // 结果为false
        mv.visitLabel(trueLabel);
        mv.visitInsn(Opcodes.ICONST_0);
        
        mv.visitLabel(endLabel);
    }
    
    /**
     * 生成数组创建的字节码
     */
    private void generateNewArray(MethodVisitor mv, String elementType, String sizeExpr) {
        // 生成数组大小的字节码
        generateExpression(mv, sizeExpr);
        
        // 根据元素类型生成不同的数组创建指令
        switch (elementType) {
            case "int":
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT);
                break;
            case "boolean":
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BOOLEAN);
                break;
            case "char":
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_CHAR);
                break;
            case "byte":
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BYTE);
                break;
            case "short":
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_SHORT);
                break;
            case "long":
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_LONG);
                break;
            case "float":
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_FLOAT);
                break;
            case "double":
                mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_DOUBLE);
                break;
            default:
                // 引用类型数组
                mv.visitTypeInsn(Opcodes.ANEWARRAY, elementType.replace('.', '/'));
                break;
        }
    }
    
    /**
     * 生成数组访问的字节码
     */
    private void generateArrayAccess(MethodVisitor mv, String arrayExpr, String indexExpr) {
        // 生成数组引用的字节码
        generateExpression(mv, arrayExpr);
        // 生成索引的字节码
        generateExpression(mv, indexExpr);
        
        // 根据数组类型生成不同的加载指令
        if (arrayExpr.startsWith("new int")) {
            mv.visitInsn(Opcodes.IALOAD);
        } else if (arrayExpr.startsWith("new boolean")) {
            mv.visitInsn(Opcodes.BALOAD);
        } else if (arrayExpr.startsWith("new char")) {
            mv.visitInsn(Opcodes.CALOAD);
        } else if (arrayExpr.startsWith("new byte")) {
            mv.visitInsn(Opcodes.BALOAD);
        } else if (arrayExpr.startsWith("new short")) {
            mv.visitInsn(Opcodes.SALOAD);
        } else if (arrayExpr.startsWith("new long")) {
            mv.visitInsn(Opcodes.LALOAD);
        } else if (arrayExpr.startsWith("new float")) {
            mv.visitInsn(Opcodes.FALOAD);
        } else if (arrayExpr.startsWith("new double")) {
            mv.visitInsn(Opcodes.DALOAD);
        } else {
            mv.visitInsn(Opcodes.AALOAD);
        }
    }
    
    /**
     * 生成数组赋值语句的字节码
     */
    private void generateArrayStore(MethodVisitor mv, String arrayExpr, String indexExpr, String valueExpr) {
        // 生成数组引用的字节码
        generateExpression(mv, arrayExpr);
        // 生成索引的字节码
        generateExpression(mv, indexExpr);
        // 生成值的字节码
        generateExpression(mv, valueExpr);
        
        // 根据数组类型生成不同的存储指令
        if (arrayExpr.startsWith("new int")) {
            mv.visitInsn(Opcodes.IASTORE);
        } else if (arrayExpr.startsWith("new boolean")) {
            mv.visitInsn(Opcodes.BASTORE);
        } else if (arrayExpr.startsWith("new char")) {
            mv.visitInsn(Opcodes.CASTORE);
        } else if (arrayExpr.startsWith("new byte")) {
            mv.visitInsn(Opcodes.BASTORE);
        } else if (arrayExpr.startsWith("new short")) {
            mv.visitInsn(Opcodes.SASTORE);
        } else if (arrayExpr.startsWith("new long")) {
            mv.visitInsn(Opcodes.LASTORE);
        } else if (arrayExpr.startsWith("new float")) {
            mv.visitInsn(Opcodes.FASTORE);
        } else if (arrayExpr.startsWith("new double")) {
            mv.visitInsn(Opcodes.DASTORE);
        } else {
            mv.visitInsn(Opcodes.AASTORE);
        }
    }
    
    /**
     * 生成字段赋值语句的字节码
     */
    private void generateFieldStore(MethodVisitor mv, String target, String value) {
        String[] parts = target.split("\\.");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid field access: " + target);
        }
        
        // 加载对象
        generateExpression(mv, parts[0]);
        // 生成值的字节码
        generateExpression(mv, value);
        // 存储字段
        mv.visitFieldInsn(Opcodes.PUTFIELD,
                "java/lang/Object",
                parts[1],
                "I"); // 默认使用int类型
    }
    
    /**
     * 生成静态字段访问的字节码
     */
    private void generateStaticFieldAccess(MethodVisitor mv, String className, String fieldName, String fieldType) {
        mv.visitFieldInsn(Opcodes.GETSTATIC,
                className.replace('.', '/'),
                fieldName,
                getTypeDescriptor(fieldType));
    }
    
    /**
     * 生成静态字段赋值的字节码
     */
    private void generateStaticFieldStore(MethodVisitor mv, String className, String fieldName, String fieldType, String value) {
        // 生成值的字节码
        generateExpression(mv, value);
        // 存储静态字段
        mv.visitFieldInsn(Opcodes.PUTSTATIC,
                className.replace('.', '/'),
                fieldName,
                getTypeDescriptor(fieldType));
    }
    
    /**
     * 获取类型的描述符
     */
    private String getTypeDescriptor(String type) {
        switch (type) {
            case "int":
                return "I";
            case "boolean":
                return "Z";
            case "char":
                return "C";
            case "byte":
                return "B";
            case "short":
                return "S";
            case "long":
                return "J";
            case "float":
                return "F";
            case "double":
                return "D";
            case "void":
                return "V";
            default:
                return "L" + type.replace('.', '/') + ";";
        }
    }
    
    /**
     * 获取变量的索引
     */
    private int getVariableIndex(String varName) {
        return variableIndices.computeIfAbsent(varName, k -> variableIndices.size());
    }
    
    /**
     * 生成类型转换的字节码
     */
    private void generateTypeCast(MethodVisitor mv, String targetType, String valueExpr) {
        // 生成值的字节码
        generateExpression(mv, valueExpr);
        
        // 根据目标类型生成转换指令
        switch (targetType) {
            case "int":
                // 从其他类型转换为int
                if (valueExpr.startsWith("new ")) {
                    // 引用类型转int
                    mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Number");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/Number",
                            "intValue",
                            "()I",
                            false);
                } else if (valueExpr.equals("true") || valueExpr.equals("false")) {
                    // boolean转int
                    // 不需要转换，因为boolean在JVM中就是int
                } else if (valueExpr.startsWith("\"")) {
                    // String转int
                    mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/String");
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                            "java/lang/Integer",
                            "parseInt",
                            "(Ljava/lang/String;)I",
                            false);
                }
                break;
                
            case "boolean":
                // 从其他类型转换为boolean
                if (valueExpr.startsWith("new ")) {
                    // 引用类型转boolean
                    mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/Boolean",
                            "booleanValue",
                            "()Z",
                            false);
                } else if (valueExpr.startsWith("\"")) {
                    // String转boolean
                    mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/String");
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                            "java/lang/Boolean",
                            "parseBoolean",
                            "(Ljava/lang/String;)Z",
                            false);
                }
                break;
                
            case "String":
                // 从其他类型转换为String
                if (valueExpr.startsWith("new ")) {
                    // 引用类型转String
                    mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Object");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/Object",
                            "toString",
                            "()Ljava/lang/String;",
                            false);
                } else if (valueExpr.equals("true") || valueExpr.equals("false")) {
                    // boolean转String
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                            "java/lang/String",
                            "valueOf",
                            "(Z)Ljava/lang/String;",
                            false);
                } else {
                    // 基本类型转String
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                            "java/lang/String",
                            "valueOf",
                            "(I)Ljava/lang/String;",
                            false);
                }
                break;
                
            default:
                // 引用类型之间的转换
                mv.visitTypeInsn(Opcodes.CHECKCAST, targetType.replace('.', '/'));
                break;
        }
    }
    
    /**
     * 生成基本类型之间的转换指令
     */
    private void generatePrimitiveTypeCast(MethodVisitor mv, String fromType, String toType) {
        if (fromType.equals(toType)) {
            return;
        }
        
        switch (fromType) {
            case "int":
                switch (toType) {
                    case "long":
                        mv.visitInsn(Opcodes.I2L);
                        break;
                    case "float":
                        mv.visitInsn(Opcodes.I2F);
                        break;
                    case "double":
                        mv.visitInsn(Opcodes.I2D);
                        break;
                    case "byte":
                        mv.visitInsn(Opcodes.I2B);
                        break;
                    case "char":
                        mv.visitInsn(Opcodes.I2C);
                        break;
                    case "short":
                        mv.visitInsn(Opcodes.I2S);
                        break;
                }
                break;
                
            case "long":
                switch (toType) {
                    case "int":
                        mv.visitInsn(Opcodes.L2I);
                        break;
                    case "float":
                        mv.visitInsn(Opcodes.L2F);
                        break;
                    case "double":
                        mv.visitInsn(Opcodes.L2D);
                        break;
                }
                break;
                
            case "float":
                switch (toType) {
                    case "int":
                        mv.visitInsn(Opcodes.F2I);
                        break;
                    case "long":
                        mv.visitInsn(Opcodes.F2L);
                        break;
                    case "double":
                        mv.visitInsn(Opcodes.F2D);
                        break;
                }
                break;
                
            case "double":
                switch (toType) {
                    case "int":
                        mv.visitInsn(Opcodes.D2I);
                        break;
                    case "long":
                        mv.visitInsn(Opcodes.D2L);
                        break;
                    case "float":
                        mv.visitInsn(Opcodes.D2F);
                        break;
                }
                break;
        }
    }
    
    /**
     * 生成try-catch块的字节码
     */
    private void generateTryCatchBlock(MethodVisitor mv, String tryBlock, String catchBlock, String exceptionType) {
        // 创建标签
        org.objectweb.asm.Label tryStart = new org.objectweb.asm.Label();
        org.objectweb.asm.Label tryEnd = new org.objectweb.asm.Label();
        org.objectweb.asm.Label catchStart = new org.objectweb.asm.Label();
        org.objectweb.asm.Label catchEnd = new org.objectweb.asm.Label();
        
        // 开始try块
        mv.visitLabel(tryStart);
        // 生成try块中的代码
        generateExpression(mv, tryBlock);
        // 结束try块
        mv.visitLabel(tryEnd);
        // 跳转到catch块结束
        mv.visitJumpInsn(Opcodes.GOTO, catchEnd);
        
        // 开始catch块
        mv.visitLabel(catchStart);
        // 生成catch块中的代码
        generateExpression(mv, catchBlock);
        // 结束catch块
        mv.visitLabel(catchEnd);
        
        // 注册异常处理器
        mv.visitTryCatchBlock(tryStart, tryEnd, catchStart, exceptionType.replace('.', '/'));
    }
    
    /**
     * 生成抛出异常的字节码
     */
    private void generateThrowException(MethodVisitor mv, String exceptionType, String message) {
        // 创建异常对象
        mv.visitTypeInsn(Opcodes.NEW, exceptionType.replace('.', '/'));
        mv.visitInsn(Opcodes.DUP);
        
        // 如果有消息，加载消息
        if (message != null) {
            mv.visitLdcInsn(message);
        }
        
        // 调用构造函数
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
                exceptionType.replace('.', '/'),
                "<init>",
                message != null ? "(Ljava/lang/String;)V" : "()V",
                false);
        
        // 抛出异常
        mv.visitInsn(Opcodes.ATHROW);
    }
    
    /**
     * 生成finally块的字节码
     */
    private void generateFinallyBlock(MethodVisitor mv, String finallyBlock) {
        // 创建标签
        org.objectweb.asm.Label finallyStart = new org.objectweb.asm.Label();
        org.objectweb.asm.Label finallyEnd = new org.objectweb.asm.Label();
        
        // 开始finally块
        mv.visitLabel(finallyStart);
        // 生成finally块中的代码
        generateExpression(mv, finallyBlock);
        // 结束finally块
        mv.visitLabel(finallyEnd);
        
        // 注册异常处理器，捕获所有异常
        mv.visitTryCatchBlock(finallyStart, finallyEnd, finallyStart, "java/lang/Throwable");
    }
    
    /**
     * 生成异常处理的字节码
     */
    private void generateExceptionHandling(MethodVisitor mv, String tryBlock, String catchBlock, String finallyBlock, String exceptionType) {
        // 创建标签
        org.objectweb.asm.Label tryStart = new org.objectweb.asm.Label();
        org.objectweb.asm.Label tryEnd = new org.objectweb.asm.Label();
        org.objectweb.asm.Label catchStart = new org.objectweb.asm.Label();
        org.objectweb.asm.Label catchEnd = new org.objectweb.asm.Label();
        org.objectweb.asm.Label finallyStart = new org.objectweb.asm.Label();
        org.objectweb.asm.Label finallyEnd = new org.objectweb.asm.Label();
        
        // 开始try块
        mv.visitLabel(tryStart);
        // 生成try块中的代码
        generateExpression(mv, tryBlock);
        // 结束try块
        mv.visitLabel(tryEnd);
        // 跳转到finally块
        mv.visitJumpInsn(Opcodes.GOTO, finallyStart);
        
        // 开始catch块
        mv.visitLabel(catchStart);
        // 生成catch块中的代码
        generateExpression(mv, catchBlock);
        // 结束catch块
        mv.visitLabel(catchEnd);
        // 跳转到finally块
        mv.visitJumpInsn(Opcodes.GOTO, finallyStart);
        
        // 开始finally块
        mv.visitLabel(finallyStart);
        // 生成finally块中的代码
        generateExpression(mv, finallyBlock);
        // 结束finally块
        mv.visitLabel(finallyEnd);
        
        // 注册异常处理器
        mv.visitTryCatchBlock(tryStart, tryEnd, catchStart, exceptionType.replace('.', '/'));
        // 注册finally块的异常处理器
        mv.visitTryCatchBlock(tryStart, tryEnd, finallyStart, "java/lang/Throwable");
        mv.visitTryCatchBlock(catchStart, catchEnd, finallyStart, "java/lang/Throwable");
    }
} 