package org.tiger.cfg;

import java.util.ArrayList;
import java.util.List;

public class ClassNode {
    private final String className;
    private final List<Field> fields;
    private final List<Method> methods;
    private final List<ClassNode> children;
    private ClassNode parent;

    public ClassNode(String className) {
        this.className = className;
        this.fields = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public void addChild(ClassNode child) {
        children.add(child);
        child.setParent(this);
    }

    public void setParent(ClassNode parent) {
        this.parent = parent;
    }

    public String getClassName() {
        return className;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public List<ClassNode> getChildren() {
        return children;
    }

    public ClassNode getParent() {
        return parent;
    }

    public static class Field {
        private final String name;
        private final String type;

        public Field(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }

    public static class Method {
        private final String name;
        private final String returnType;
        private final List<Parameter> parameters;
        private final CFG cfg;

        public Method(String name, String returnType, List<Parameter> parameters, CFG cfg) {
            this.name = name;
            this.returnType = returnType;
            this.parameters = parameters;
            this.cfg = cfg;
        }

        public String getName() {
            return name;
        }

        public String getReturnType() {
            return returnType;
        }

        public List<Parameter> getParameters() {
            return parameters;
        }

        public CFG getCFG() {
            return cfg;
        }

        public static class Parameter {
            private final String name;
            private final String type;

            public Parameter(String name, String type) {
                this.name = name;
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public String getType() {
                return type;
            }
        }
    }
} 