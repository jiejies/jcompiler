package org.tiger.cfg;

import java.util.List;
import java.util.Map;

/**
 * 方法的控制流图
 */
public class MethodCFG {
    private final String methodName;
    private final String returnType;
    private final List<String> parameterTypes;
    private final Map<String, BasicBlock> basicBlocks;
    private final BasicBlock entryBlock;
    
    public MethodCFG(String methodName, String returnType, List<String> parameterTypes,
                    Map<String, BasicBlock> basicBlocks, BasicBlock entryBlock) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.basicBlocks = basicBlocks;
        this.entryBlock = entryBlock;
    }
    
    public String getMethodName() {
        return methodName;
    }
    
    public String getReturnType() {
        return returnType;
    }
    
    public List<String> getParameterTypes() {
        return parameterTypes;
    }
    
    public Map<String, BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }
    
    public BasicBlock getEntryBlock() {
        return entryBlock;
    }
    
    /**
     * 获取方法的描述符
     */
    public String getDescriptor() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String paramType : parameterTypes) {
            sb.append(getTypeDescriptor(paramType));
        }
        sb.append(")");
        sb.append(getTypeDescriptor(returnType));
        return sb.toString();
    }
    
    /**
     * 获取类型的描述符
     */
    private String getTypeDescriptor(String type) {
        // 检查是否是数组类型
        if (type.startsWith("[") && type.endsWith(";")) {
            return type;  // 已经是正确的描述符格式
        }
        
        switch (type) {
            case "int":
                return "I";
            case "boolean":
                return "Z";
            case "String":
                return "Ljava/lang/String;";
            case "void":
                return "V";
            default:
                return "L" + type.replace('.', '/') + ";";
        }
    }
}