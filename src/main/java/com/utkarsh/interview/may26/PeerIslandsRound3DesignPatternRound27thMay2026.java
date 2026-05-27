package com.utkarsh.interview.may26;

/**
 * PeerIslands - Round 3: Design Pattern Round
 * Date: 27th May 2026
 * Interviewer focus: HLD, Design Patterns, Multithreading, AI experience
 */
public class PeerIslandsRound3DesignPatternRound27thMay2026 {

    /*
    ============================================================
    ROUND SUMMARY
    ============================================================

    1. INTRODUCTION
       - Introduced myself, walked through experience at Accenture and Publicis Sapient

    2. MICROSERVICES DESIGN PATTERNS (asked to name some)
       - Saga Pattern         → distributed transaction management
       - Circuit Breaker      → fault tolerance (Resilience4j in Spring Boot)
       - CQRS                 → separate read/write models for scalability

    3. HLD - E-Commerce Website (scale: ~500K users)
       Clarifying questions asked:
         - What is the expected scale?              → ~500K users
         - Are users domestic only or international? → clarified to scope CDN / latency decisions

       Components drawn:
         - API Gateway → routes traffic, handles auth
         - Services: User, Product, Order, Payment, Notification
         - Message Broker (Kafka) for async inter-service communication
         - CDN for static assets
         - Databases: per-service (polyglot persistence)
         - Cache layer (Redis) for product catalog / sessions

       Follow-up: How does inter-service communication work?
         - Sync  → REST / gRPC (e.g., Order → Payment)
         - Async → Kafka events (e.g., Order placed → Notification service)

       Follow-up: How to handle a failing service?
         - Circuit Breaker (Resilience4j) → stops cascading failures
         - Retry with exponential backoff
         - Fallback responses / graceful degradation
         - Dead Letter Queue (DLQ) for failed Kafka messages

    ============================================================
    4. SINGLETON PATTERN
    ============================================================
    */

    // ---- Lazy Initialization (Not thread-safe) ----
    static class LazySingleton {
        private static LazySingleton instance;

        private LazySingleton() {}

        public static LazySingleton getInstance() {
            if (instance == null) {
                instance = new LazySingleton();
            }
            return instance;
        }
    }

    // ---- Eager Initialization ----
    static class EagerSingleton {
        private static final EagerSingleton instance = new EagerSingleton();

        private EagerSingleton() {}

        public static EagerSingleton getInstance() {
            return instance;
        }
    }

    // ---- Double-Checked Locking (Thread-safe, Lazy) ----
    static class DoubleCheckedSingleton {
        private static volatile DoubleCheckedSingleton instance; // volatile: prevents instruction reordering

        private DoubleCheckedSingleton() {}

        public static DoubleCheckedSingleton getInstance() {
            if (instance == null) {                     // First check: avoid locking every time
                synchronized (DoubleCheckedSingleton.class) {
                    if (instance == null) {             // Second check: inside lock, prevent race condition
                        instance = new DoubleCheckedSingleton();
                    }
                }
            }
            return instance;
        }
    }

    // ---- Enum Singleton (Best practice — reflection/serialization safe) ----
    enum EnumSingleton {
        INSTANCE;

        public void doSomething() {
            System.out.println("Enum singleton — JVM guarantees single instance");
        }
    }

    /*
    HOW TO BREAK SINGLETON & PREVENTION:
    ┌─────────────────────┬────────────────────────────────────────────────────────┐
    │ Attack Vector       │ Prevention                                             │
    ├─────────────────────┼────────────────────────────────────────────────────────┤
    │ Reflection          │ Throw exception in constructor if instance != null     │
    │ Serialization       │ Implement readResolve() → return existing instance     │
    │ Cloning             │ Override clone() → throw CloneNotSupportedException    │
    │ Multiple CL         │ Use Enum singleton (JVM-level guarantee)               │
    └─────────────────────┴────────────────────────────────────────────────────────┘

    Reflection-safe constructor guard:
        private DoubleCheckedSingleton() {
            if (instance != null) {
                throw new IllegalStateException("Use getInstance()");
            }
        }

    ============================================================
    5. MULTITHREADING - Executor Framework
    ============================================================

    Types discussed:
      - newFixedThreadPool(n)          → fixed number of threads, bounded
      - newSingleThreadExecutor()      → single thread, sequential execution
      - newScheduledThreadPool(n)      → delayed / periodic tasks
      - newCachedThreadPool()          → unbounded, reuses idle threads
      - newWorkStealingPool()          → ForkJoin-based, parallel tasks
      - newVirtualThreadPerTaskExecutor() → Java 21+, lightweight virtual threads ✓

    ============================================================
    6. DESIGN PATTERNS USED IN REAL PROJECTS
    ============================================================

      Pattern                | Usage in Project
      -----------------------|--------------------------------------------------
      Factory                | Creating different handler/processor types
      Builder                | @Builder (Lombok) on request/response DTOs
      Singleton              | Spring default bean scope (@Component, @Service)
      Chain of Responsibility| Filter/middleware chains (e.g., validation pipeline)
      Proxy                  | @Transactional → Spring AOP proxy intercepts calls

    ============================================================
    7. AI EXPERIENCE
    ============================================================
      - Publicis Sapient: GenAI project using LangChain, LangGraph, FastAPI
      - RAG pipeline with ChromaDB / FAISS as vector stores
      - Framed as supplementary skill — core focus remains Java backend
    */
}