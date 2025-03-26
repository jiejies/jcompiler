package org.tiger.cfg;

import java.util.*;

public class ClassEliminator {
    private final Map<String, ClassNode> classMap;
    private final List<ClassNode> inheritanceTree;

    public ClassEliminator() {
        this.classMap = new HashMap<>();
        this.inheritanceTree = new ArrayList<>();
    }

    public void addClass(String className, String parentClassName) {
        ClassNode classNode = new ClassNode(className);
        classMap.put(className, classNode);
        
        if (parentClassName != null) {
            ClassNode parentNode = classMap.get(parentClassName);
            if (parentNode != null) {
                classNode.setParent(parentNode);
                parentNode.addChild(classNode);
            }
        } else {
            inheritanceTree.add(classNode);
        }
    }

    public void buildInheritanceTree() {
        // 按照层次顺序遍历继承树
        for (ClassNode root : new ArrayList<>(inheritanceTree)) {
            buildInheritanceTree0(root);
        }
    }

    private void buildInheritanceTree0(ClassNode node) {
        // 递归处理子类
        for (ClassNode child : new ArrayList<>(node.getChildren())) {
            buildInheritanceTree0(child);
        }
    }

    public void eliminateClasses() {
        // 按照层次顺序遍历继承树，从叶子节点开始处理
        for (ClassNode root : new ArrayList<>(inheritanceTree)) {
            eliminateClasses0(root);
        }
    }

    private void eliminateClasses0(ClassNode node) {
        // 递归处理子类
        for (ClassNode child : new ArrayList<>(node.getChildren())) {
            eliminateClasses0(child);
        }

        // 处理当前类
        prefixOneClass(node);
    }

    private void prefixOneClass(ClassNode node) {
        String className = node.getClassName();
        ClassNode parent = node.getParent();

        // 1. 处理字段
        List<ClassNode.Field> fields = new ArrayList<>();
        
        // 添加所有祖先类的字段
        ClassNode current = parent;
        while (current != null) {
            for (ClassNode.Field ancestorField : current.getFields()) {
                String prefixedName = current.getClassName() + "_" + ancestorField.getName();
                fields.add(new ClassNode.Field(prefixedName, ancestorField.getType()));
            }
            current = current.getParent();
        }
        
        // 添加当前类的字段
        fields.addAll(node.getFields());
        
        // 更新字段列表
        node.getFields().clear();
        node.getFields().addAll(fields);

        // 2. 处理方法
        List<ClassNode.Method> methods = new ArrayList<>();
        
        // 添加所有祖先类的方法
        current = parent;
        while (current != null) {
            for (ClassNode.Method ancestorMethod : current.getMethods()) {
                List<ClassNode.Method.Parameter> newParams = new ArrayList<>();
                newParams.add(new ClassNode.Method.Parameter("this", className));
                newParams.addAll(ancestorMethod.getParameters());

                ClassNode.Method newMethod = new ClassNode.Method(
                    ancestorMethod.getName(),
                    ancestorMethod.getReturnType(),
                    newParams,
                    ancestorMethod.getCFG()
                );
                methods.add(newMethod);
            }
            current = current.getParent();
        }
        
        // 添加当前类的方法
        for (ClassNode.Method method : node.getMethods()) {
            List<ClassNode.Method.Parameter> newParams = new ArrayList<>();
            newParams.add(new ClassNode.Method.Parameter("this", className));
            newParams.addAll(method.getParameters());

            ClassNode.Method newMethod = new ClassNode.Method(
                method.getName(),
                method.getReturnType(),
                newParams,
                method.getCFG()
            );
            methods.add(newMethod);
        }
        
        // 更新方法列表
        node.getMethods().clear();
        node.getMethods().addAll(methods);
    }

    public Map<String, ClassNode> getClassMap() {
        return classMap;
    }

    public List<ClassNode> getInheritanceTree() {
        return inheritanceTree;
    }
} 