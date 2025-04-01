package org.tiger.runtime;

public class TigerRuntime {
    private static TigerRuntime instance = new TigerRuntime();
    
    private TigerRuntime() {}
    
    public static TigerRuntime getInstance() {
        return instance;
    }
    
    // 静态方法，可以直接调用
    public static void printInt(int value) {
        System.out.println(value);
    }
    
    public static void printString(String value) {
        System.out.println(value);
    }
    
    public static void printBoolean(boolean value) {
        System.out.println(value);
    }
    
    // 实例方法，通过 getInstance() 调用
    public void printIntInstance(int value) {
        System.out.println(value);
    }
    
    public void printStringInstance(String value) {
        System.out.println(value);
    }
    
    public void printBooleanInstance(boolean value) {
        System.out.println(value);
    }
}