package com.utkarsh.interview.june26;

import java.util.*;
import java.util.stream.*;

/**
 * WTDS Interview – Round 1 – 5th June 2026
 */
public class WTDS_R1_05June2026 {

    // ─────────────────────────────────────────────────────────────
    // Q1. Group Anagrams
    //     Input : ["eat","tea","tan","ate","nat","bat"]
    //     Output: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
    //
    //  YOUR ATTEMPT – errors:
    //    ❌ toCharArray() returns char[], not a Stream → can't call .sort() on it
    //    ❌ Arrays.sort() is void → can't chain
    //    ❌ Collectors.groupingBy() takes a classifier + optional downstream,
    //       not (classifier, e->e)
    //
    //  FIX: sort with Arrays.sort() imperatively inside the lambda,
    //       then wrap back to String as the grouping key.
    // ─────────────────────────────────────────────────────────────
    static void q1_groupAnagrams() {
        List<String> list = Arrays.asList("eat", "tea", "tan", "ate", "nat", "bat");

        Map<String, List<String>> grouped = list.stream()
                .collect(Collectors.groupingBy(s -> {
                    char[] chars = s.toCharArray(); // char[] – not a Stream
                    Arrays.sort(chars);             // sorts in-place, void return
                    return new String(chars);       // "eat" → "aet", "tea" → "aet"
                }));

        System.out.println(grouped.values());
        // [[eat, tea, ate], [tan, nat], [bat]]
    }

    // ─────────────────────────────────────────────────────────────
    // Q2. Singleton – Double-Checked Locking (thread-safe)
    //
    //  YOUR ATTEMPT – bugs:
    //    ❌ private final volatile Singleton instance  → must be static (class-level)
    //                                                 → must NOT be final (assigned lazily)
    //    ❌ syncronized  → typo: synchronized
    //    ❌ instance checked but never initialized as static field
    //
    //  KEY POINTS:
    //    • static     → one instance shared across the class, not per-object
    //    • volatile   → prevents instruction reordering; ensures the
    //                   half-constructed object isn't visible to other threads
    //    • double-check (outer null check) → avoids synchronized block overhead
    //      on every call once instance is initialized
    // ─────────────────────────────────────────────────────────────
    static class Singleton {
        private static volatile Singleton instance; // static + volatile

        private Singleton() {} // prevent external instantiation

        public static Singleton getInstance() {
            if (instance == null) {                     // 1st check (no lock)
                synchronized (Singleton.class) {
                    if (instance == null) {             // 2nd check (with lock)
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Q1: Group Anagrams ===");
        q1_groupAnagrams();

        System.out.println("\n=== Q2: Singleton ===");
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println("Same instance: " + (s1 == s2)); // true
    }
}


/*
 ═══════════════════════════════════════════════════════════════════
  THEORY QUESTIONS
 ═══════════════════════════════════════════════════════════════════

 1. DEFAULT BEAN SCOPE IN SPRING BOOT
    ────────────────────────────────────
    singleton – one shared instance per ApplicationContext (the default).
    Spring creates it on startup (eager) and reuses it for every injection.

    Note: Spring singleton ≠ GoF Singleton.
    GoF Singleton = one instance per JVM/ClassLoader.
    Spring Singleton = one instance per ApplicationContext
    (you can have multiple contexts → multiple instances).


 2. OTHER BEAN SCOPES
    ───────────────────
    prototype   → new instance every time the bean is requested / injected.
                  Spring does NOT manage its lifecycle after creation.

    request     → one instance per HTTP request. Destroyed when request ends.
                  Needs web-aware ApplicationContext.

    session     → one instance per HTTP session. Lives until session expires.

    application → one instance per ServletContext (shared across all sessions).

    websocket   → one instance per WebSocket session.

    Custom scopes can be registered via Scope interface + registerScope().


 3. KAFKA QUESTIONS
    ─────────────────

    a) What is Kafka?
       Distributed event-streaming platform. Producers publish messages to
       Topics; Consumers read from Topics. Messages are persisted on disk
       (not deleted after consumption) for a configurable retention period.

    b) Topic, Partition, Offset
       Topic     → logical channel for messages (e.g. "order-events")
       Partition → a topic is split into N ordered, immutable log segments.
                   Enables parallelism — consumers read partitions in parallel.
       Offset    → sequential ID of a message within a partition.
                   Consumers track their position via offset (auto or manual commit).

    c) Consumer Groups
       A group of consumers that collectively read a topic.
       Each partition is assigned to exactly ONE consumer in the group.
       → More partitions = more parallel consumers = higher throughput.
       Different groups each get ALL messages independently (pub-sub model).

    d) Can multiple consumers read from the same partition?
       NO  – within the same Consumer Group (ordering guarantee).
       YES – across different Consumer Groups (each has its own offset pointer).

    e) Producer acks
       acks=0  → fire and forget; fastest; possible message loss.
       acks=1  → leader acknowledges; default; one broker must confirm.
       acks=all (or -1) → all in-sync replicas confirm; safest; slowest.

    f) How to increase Kafka throughput?
       Producer: ↑ batch.size, add linger.ms, enable compression (snappy/lz4)
       Consumer: ↑ partition count (more consumer threads), ↑ fetch.min.bytes
       Broker:   add partitions, add brokers

    g) What is a Consumer Lag?
       Difference between the latest offset produced and the last offset consumed.
       High lag = consumers are falling behind. Monitor via JMX or Kafka UI.

    h) Kafka vs RabbitMQ
       Kafka      → pull-based, log retention, replay, high throughput, event streaming.
       RabbitMQ   → push-based, message deleted after ack, task queue model.


 4. PUT vs PATCH
    ──────────────
    PUT   → REPLACE the entire resource.
            Client must send the FULL representation.
            If a field is omitted, it is set to null / default.
            Idempotent: calling PUT multiple times with the same body = same result.

    PATCH → PARTIAL UPDATE of a resource.
            Client sends only the CHANGED fields.
            Other fields remain unchanged.
            May or may not be idempotent (depends on implementation).

    Example – update only email of a user:

        PUT /users/1
        { "name": "Alice", "email": "new@email.com", "age": 30 }  ← ALL fields required

        PATCH /users/1
        { "email": "new@email.com" }                               ← only changed field

    Rule of thumb:
        Use PUT when replacing a full resource (e.g. config files, documents).
        Use PATCH when updating one or two fields of a large resource.


 5. WHAT IS THE SINGLETON DESIGN PATTERN AND WHY DO WE USE IT IN JAVA?
    ────────────────────────────────────────────────────────────────────
    Intent:
    Ensure a class has ONLY ONE instance throughout the application's
    lifetime, and provide a global access point to it.

    Why we use it:
      • Shared resource control – e.g. DB connection pool, thread pool,
        logging service. Creating multiple instances would waste resources
        or cause inconsistent state.
      • Global configuration – one place to read/write app-wide settings.
      • Caching – a single shared cache object avoids redundant data loading.

    Real-world Java examples:
      • java.lang.Runtime.getRuntime()        → JVM runtime info
      • java.util.logging.Logger              → logging framework
      • Spring Beans (default scope)          → every @Service/@Repository
        is effectively a singleton within the ApplicationContext

    Implementation variants:
      a) Eager initialization (simplest, thread-safe):
            private static final Singleton INSTANCE = new Singleton();
            // JVM class loading guarantees thread safety here

      b) Double-Checked Locking (lazy + thread-safe): ← shown in Q2 above
            Use when instance creation is expensive and you want to delay it.

      c) Bill Pugh / Initialization-on-demand (best practice):
            private static class Holder {
                private static final Singleton INSTANCE = new Singleton();
            }
            public static Singleton getInstance() { return Holder.INSTANCE; }
            // Holder class is loaded only when getInstance() is first called
            // JVM guarantees thread safety with no synchronized overhead

      d) Enum Singleton (Josh Bloch recommendation):
            public enum Singleton { INSTANCE; }
            // Serialization-safe + reflection-proof + thread-safe by JVM

    Singleton vs Spring Bean scope=singleton:
      GoF Singleton  → one instance per ClassLoader (JVM-level guarantee).
      Spring singleton→ one instance per ApplicationContext.
                        If two contexts exist, two instances can exist.

    When NOT to use:
      • Avoid for stateful objects shared across threads without sync.
      • Makes unit testing harder (global state is hard to mock/reset).
        Prefer dependency injection (Spring) over hand-rolled singletons.


 6. IS MULTIPLE INHERITANCE POSSIBLE IN JAVA?
    ─────────────────────────────────────────────
    With CLASSES   → NO.
    Java deliberately disallows extending more than one class to avoid
    the "Diamond Problem" — ambiguity when two parent classes have the
    same method and the compiler doesn't know which to inherit.

    With INTERFACES → YES.
    A class can implement any number of interfaces.
    An interface can also extend multiple interfaces.

        class MyClass implements InterfaceA, InterfaceB { ... }  // ✅ allowed
        class MyClass extends ClassA, ClassB            { ... }  // ❌ compile error


 7. CLASS IMPLEMENTS TWO INTERFACES WITH THE SAME METHOD SIGNATURE
    ──────────────────────────────────────────────────────────────────

    Case A – Same signature, same return type (abstract methods)
    ─────────────────────────────────────────────────────────────
        interface A { void show(); }
        interface B { void show(); }

        class MyClass implements A, B {
            @Override
            public void show() {          // ONE override satisfies BOTH
                System.out.println("show");
            }
        }
        // ✅ No conflict – one implementation covers both contracts.


    Case B – Same signature, same return type (default methods) → CONFLICT
    ────────────────────────────────────────────────────────────────────────
        interface A { default void show() { System.out.println("A"); } }
        interface B { default void show() { System.out.println("B"); } }

        class MyClass implements A, B {
            // ❌ COMPILE ERROR if you don't override:
            //    "MyClass inherits unrelated defaults for show() from A and B"

            @Override
            public void show() {          // must explicitly resolve
                A.super.show();           // choose A's version, OR
                // B.super.show();        // choose B's version, OR
                // write your own logic
            }
        }
        // Rule: when two interfaces provide conflicting default methods,
        // the implementing class MUST override and resolve the conflict.


 8. SAME METHOD NAME BUT DIFFERENT RETURN TYPE
    ─────────────────────────────────────────────
        interface A { int    getValue(); }
        interface B { String getValue(); }

        class MyClass implements A, B { ... }  // ❌ COMPILE ERROR

    WHY IT FAILS:
    Java does NOT support return-type-only overloading.
    The JVM identifies a method by name + parameter types (erased signature).
    Two methods with the same name and same parameters but different return
    types are INDISTINGUISHABLE to the compiler — it cannot generate a single
    method that satisfies both contracts simultaneously.

    There is NO workaround for this in a single class.
    You would need separate classes or a redesign of the interfaces.

    CONTRAST with valid return-type covariance (overriding in a subclass):
        class Parent { Animal get() { ... } }
        class Child extends Parent {
            @Override
            Dog get() { ... }   // ✅ allowed – Dog IS-A Animal (covariant return)
        }
    This works because the return type is narrowed, not changed to an
    unrelated type — the contract is still satisfied.

*/