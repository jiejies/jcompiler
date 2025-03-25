package org.tiger.semantic;

import java.util.*;

/**
 * 作用域类，用于管理符号的可见性
 */
public class Scope {
    private final Map<String, Object> symbols = new HashMap<>();
    private final Scope parent;
    
    public Scope(Scope parent) {
        this.parent = parent;
    }
    
    /**
     * 在当前作用域中定义符号
     */
    public void define(String name, Object symbol) {
        symbols.put(name, symbol);
    }
    
    /**
     * 在当前作用域及父作用域中查找符号
     */
    public Object resolve(String name) {
        Object symbol = symbols.get(name);
        if (symbol != null) {
            return symbol;
        }
        return parent != null ? parent.resolve(name) : null;
    }
    
    /**
     * 检查符号是否在当前作用域中已定义
     */
    public boolean isDefined(String name) {
        return symbols.containsKey(name);
    }
} 