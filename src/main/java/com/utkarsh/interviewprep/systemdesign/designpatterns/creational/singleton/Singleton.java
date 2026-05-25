package com.utkarsh.interviewprep.systemdesign.designpatterns.creational.singleton;

/**
 * Singleton using Double-Checked Locking (DCL)
 * <p>
 * Why this approach?
 * - Lazy initialization (object created only when needed)
 * - Thread-safe
 * - Performance optimized (synchronized block only on first creation)
 */
class Singleton {

    /**
     * volatile ensures visibility and prevents instruction reordering issues.
     * Without volatile, another thread may see a partially constructed object.
     */
    private static volatile Singleton instance;

    /**
     * Private constructor prevents external instantiation
     */
    private Singleton() {
    }

    /**
     * Global access point to get the single instance
     */
    public static Singleton getInstance() {

        // First check (no locking) → improves performance
        if (instance == null) {

            synchronized (Singleton.class) {

                // Second check (with lock) → ensures only one instance is created
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}

/**
 * Enum Singleton (Best and simplest approach in Java)
 * <p>
 * Why this is preferred:
 * - Thread-safe by default
 * - Serialization safe
 * - Reflection safe
 * - No synchronization needed
 */
enum Singleton2 {

    INSTANCE;

    // Example method
    public void doSomething() {
        System.out.println("Enum Singleton in action");
    }
}

//https://www.programiz.com/online-compiler/4xlFBzpK6rnvW
