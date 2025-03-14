package org.tiger.ast;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Environment parent;
    private Map<String, Object> values;
    
    public Environment(Environment parent) {
        this.parent = parent;
        this.values = new HashMap<>();
    }
    
    public void put(String name, Object value) {
        values.put(name, value);
    }
    
    public Object get(String name) {
        if (values.containsKey(name)) {
            return values.get(name);
        }
        if (parent != null) {
            return parent.get(name);
        }
        throw new RuntimeException("变量未定义: " + name);
    }
    
    public boolean contains(String name) {
        return values.containsKey(name) || (parent != null && parent.contains(name));
    }
} 