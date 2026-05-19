package com.utkarsh.interview.may26;

import java.util.*;
import java.util.stream.*;

/**
 * VLink Interview – 19th May 2026
 * round-2
 * Contains solutions + theory notes for all questions asked.
 */
public class VLink19thMay2026 {

    // ─────────────────────────────────────────────────────────────
    // Q1. Partition a list into even and odd numbers
    //     Input : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    //     Output: {false=[1,3,5,7,9], true=[2,4,6,8,10]}
    //
    //  APPROACH: Collectors.partitioningBy()
    //  - Special case of groupingBy with exactly two buckets: true / false.
    //  - Always returns both keys even if one bucket is empty.
    // ─────────────────────────────────────────────────────────────
    static void q1_partitionEvenOdd() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Map<Boolean, List<Integer>> partitioned = list.stream()
                .collect(Collectors.partitioningBy(e -> e % 2 == 0));

        System.out.println("Even : " + partitioned.get(true));   // [2,4,6,8,10]
        System.out.println("Odd  : " + partitioned.get(false));  // [1,3,5,7,9]
    }

    // ─────────────────────────────────────────────────────────────
    // Q2. Pair Difference equals K
    //     Input : [5, 20, 3, 2, 50, 80], k = 78
    //     Output: true  (80 - 2 = 78)
    //
    //  THREE APPROACHES:
    //
    //  A) Brute Force – O(n²) time, O(1) space
    //     Try every pair (i, j); check |arr[i] - arr[j]| == k.
    //     Your original q2() was almost correct – just use Math.abs().
    //
    //  B) HashSet – O(n) time, O(n) space  ← BEST for interviews
    //     For each element x, check if (x+k) or (x-k) already seen in set.
    //
    //  C) Two Pointer – O(n log n) time, O(1) space
    //     Sort the array first, then use two pointers.
    //     NOTE: your q2b() had bugs – it swapped elements (corrupting the
    //     array), returned false inside the loop too early, and the pointer
    //     logic was incorrect. Correct version below.
    // ─────────────────────────────────────────────────────────────

    // Approach A – Brute Force O(n²)
    static boolean q2_bruteForce(int[] arr, int k) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (Math.abs(arr[i] - arr[j]) == k) {
                    return true;
                }
            }
        }
        return false;
    }

    // Approach B – HashSet O(n) ← recommended answer
    static boolean q2_hashSet(int[] arr, int k) {
        Set<Integer> seen = new HashSet<>();
        for (int x : arr) {
            // Check if a complement already exists that forms the diff
            if (seen.contains(x + k) || seen.contains(x - k)) {
                return true;
            }
            seen.add(x);
        }
        return false;
    }

    // Approach C – Two Pointer O(n log n)  [sort first, then pointer walk]
    static boolean q2_twoPointer(int[] arr, int k) {
        Arrays.sort(arr);       // [2, 3, 5, 20, 50, 80]
        int left = 0, right = 1;

        while (right < arr.length) {
            int diff = arr[right] - arr[left];  // always >= 0 after sort

            if (diff == k) {
                return true;
            } else if (diff < k) {
                right++;        // diff too small → expand right
            } else {
                left++;         // diff too large → shrink from left
                if (left == right) right++; // pointers must stay distinct
            }
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=== Q1: Even / Odd Partition ===");
        q1_partitionEvenOdd();

        System.out.println("\n=== Q2: Pair Difference == K ===");
        int[] arr = {5, 20, 3, 2, 50, 80};
        int k = 78;

        System.out.println("Brute Force  : " + q2_bruteForce(arr.clone(), k)); // true
        System.out.println("HashSet      : " + q2_hashSet(arr.clone(), k));    // true
        System.out.println("Two Pointer  : " + q2_twoPointer(arr.clone(), k)); // true

        // Edge cases
        System.out.println("\n--- Edge cases ---");
        System.out.println(q2_hashSet(new int[]{1}, 0));        // false (need 2 elements)
        System.out.println(q2_hashSet(new int[]{1, 1}, 0));     // true  (1-1=0)
        System.out.println(q2_hashSet(new int[]{5, 20, 3}, 100)); // false
    }
}


/*
 ═══════════════════════════════════════════════════════════════════
  THEORY / SQL ANSWERS
 ═══════════════════════════════════════════════════════════════════

 ── SQL Q1. Select Even Numbers from a table ─────────────────────────

   Your attempt used invalid syntax (grouping by, where with aggregate).
   Simple fix – just use WHERE with the modulo operator:

   Correct query:
     SELECT n
     FROM nums
     WHERE n % 2 = 0;

   No GROUP BY needed here; GROUP BY is for aggregating rows.
   In SQL, modulo is written as % (MySQL/Postgres) or MOD(n, 2) (Oracle).


 ── SQL Q2. Count Male and Female Employees ──────────────────────────

   Your single-query attempt was almost right – small syntax fixes needed:
   - It's GROUP BY (not "grouping by").
   - String literals use single quotes in SQL, not double quotes.
   - Column alias with a space needs backticks (MySQL) or quotes (standard SQL).
   - The WHERE clause is optional here since GROUP BY already separates genders;
     but it's fine to keep it for clarity.

   ┌─────────────────────────────────────────────────────────────────┐
   │  Option A – Single query (recommended)                          │
   │                                                                 │
   │  SELECT gender, COUNT(*) AS emp_count                           │
   │  FROM employee                                                  │
   │  GROUP BY gender;                                               │
   │                                                                 │
   │  Result:                                                        │
   │  gender  | emp_count                                            │
   │  ─────── | ─────────                                            │
   │  male    | 2                                                    │
   │  female  | 5                                                    │
   └─────────────────────────────────────────────────────────────────┘

   ┌─────────────────────────────────────────────────────────────────┐
   │  Option B – Pivot style (one row, two columns)                  │
   │                                                                 │
   │  SELECT                                                         │
   │      COUNT(CASE WHEN gender = 'male'   THEN 1 END) AS male,    │
   │      COUNT(CASE WHEN gender = 'female' THEN 1 END) AS female   │
   │  FROM employee;                                                 │
   │                                                                 │
   │  Result:                                                        │
   │  male | female                                                  │
   │  ──── | ──────                                                  │
   │  2    | 5                                                       │
   └─────────────────────────────────────────────────────────────────┘


 ── WHAT WAS WRONG WITH YOUR q2b() (Two Pointer) ────────────────────

   Bug 1 – Swapping elements inside a search function corrupts the input.
            A Two Pointer search on a sorted array does NOT swap elements;
            it only MOVES the pointer indices.

   Bug 2 – Returning false inside the loop after the first non-matching pair.
            The function should keep scanning; only return false after the
            whole array is exhausted.

   Bug 3 – The sorted two-pointer technique requires the array to be sorted
            first (Arrays.sort). Your q2b() never sorted the array, so
            pointer movement had no meaningful direction.

   Bug 4 – After sort, the difference arr[right] - arr[left] is always ≥ 0,
            so checking arr[high] - arr[low] != k and arr[low] - arr[high] != k
            simultaneously is redundant and wrong.

   Correct two-pointer logic (see q2_twoPointer above):
     Sort → left=0, right=1
     diff = arr[right] - arr[left]
     if diff == k → found
     if diff < k  → right++   (need larger difference)
     if diff > k  → left++    (need smaller difference)
     if left == right after incrementing → right++ (keep pointers distinct)


 ═══════════════════════════════════════════════════════════════════
  ADDITIONAL THEORY QUESTIONS
 ═══════════════════════════════════════════════════════════════════

 ── T1. JAVA 17 vs JAVA 21 – KEY DIFFERENCES ────────────────────────

   Both are LTS (Long-Term Support) releases. Java 21 builds on Java 17.

   Features ADDED in Java 21 (not present in Java 17):

   1. Virtual Threads – Project Loom (JEP 444) ← MOST IMPORTANT
      Lightweight threads managed by the JVM, not the OS.
      (See T3 below for full explanation.)

   2. Pattern Matching for switch (JEP 441) – FINAL in Java 21
      (Was a preview in Java 17 via JEP 406.)
        Object obj = ...;
        String result = switch (obj) {
            case Integer i -> "int: " + i;
            case String  s -> "str: " + s;
            case null      -> "null";
            default        -> "other";
        };

   3. Record Patterns (JEP 440) – FINAL in Java 21
      Deconstruct record values directly in pattern matching.
        if (obj instanceof Point(int x, int y)) {
            System.out.println(x + ", " + y);
        }

   4. Sequenced Collections (JEP 431)
      New interfaces: SequencedCollection, SequencedMap, SequencedSet.
      Uniform API to access first/last elements across List, Deque, LinkedHashMap:
        list.getFirst();  list.getLast();
        list.reversed();

   5. String Templates (JEP 430) – PREVIEW in Java 21
      Template literals with embedded expressions (like JS template strings):
        String name = "Utkarsh";
        String msg = STR."Hello \{name}!";  // "Hello Utkarsh!"

   6. Unnamed Classes and Instance Main Methods (JEP 445) – PREVIEW
      Write a simple program without boilerplate class declaration:
        void main() { System.out.println("Hello!"); }

   7. Scoped Values (JEP 446) – PREVIEW
      Safe alternative to ThreadLocal for passing immutable data
      down a call stack, especially with virtual threads.

   Summary table:
   ┌──────────────────────────────┬───────────┬───────────┐
   │ Feature                      │ Java 17   │ Java 21   │
   ├──────────────────────────────┼───────────┼───────────┤
   │ Records                      │ Final     │ Final     │
   │ Sealed Classes               │ Final     │ Final     │
   │ Pattern match instanceof     │ Final     │ Final     │
   │ Text Blocks                  │ Final     │ Final     │
   │ Switch Expressions           │ Final     │ Final     │
   │ Pattern match switch         │ Preview   │ FINAL     │
   │ Record Patterns              │ –         │ FINAL     │
   │ Virtual Threads (Loom)       │ –         │ FINAL     │
   │ Sequenced Collections        │ –         │ FINAL     │
   │ String Templates             │ –         │ Preview   │
   │ Scoped Values                │ –         │ Preview   │
   └──────────────────────────────┴───────────┴───────────┘


 ── T2. DISADVANTAGES OF var (LOCAL VARIABLE TYPE INFERENCE) ─────────

   var was introduced in Java 10 for local variables.
   The compiler infers the type; it is NOT dynamic typing (still strongly typed).

   Disadvantages:

   1. Reduced Readability
      The type is not visible at the declaration site.
        var result = getEmployeeData();   // What type is this? You must look at the method.
        List<Employee> result = ...;       // Immediately clear.

   2. IDE Dependency
      Readers without IDE hover support (e.g. code reviews in GitHub, plain text)
      can't easily tell the type.

   3. Cannot Be Used with null Literals
        var x = null;   // Compile error – type cannot be inferred from null.

   4. Cannot Be Used for Fields, Method Parameters, or Return Types
        private var name = "Utkarsh";   // Compile error – only local variables.

   5. Misleading with Diamond Operator
        var list = new ArrayList<>();    // Inferred as ArrayList<Object>, not ArrayList<String>!
        var list = new ArrayList<String>(); // Must be explicit to get the right type param.

   6. Chained / Complex Expressions Become Cryptic
        var x = map.entrySet().stream().filter(...).collect(...);
        // The type of x is hard to guess without tracing the full chain.

   7. Harder to Refactor
      Changing the return type of a method silently changes all var usages;
      no compile-time nudge to review each usage.

   When to use var (best practices):
   - Short-scope local variables where the type is obvious from the right-hand side.
       var list = new ArrayList<String>();   // fine, type is obvious
       var entry = map.entrySet().iterator().next(); // avoid, type is opaque


 ── T3. VIRTUAL THREADS (Project Loom – Java 21) ─────────────────────

   PROBLEM WITH PLATFORM (OS) THREADS:
   - Each Java thread = one OS thread = 1–2 MB stack memory.
   - OS thread is expensive to create, context-switch, and scale.
   - A server handling 10,000 concurrent requests needs 10,000 OS threads → memory exhausted.
   - Traditional fix: non-blocking/reactive code (WebFlux) → complex, hard to read/debug.

   WHAT ARE VIRTUAL THREADS?
   - Lightweight threads managed entirely by the JVM (not the OS).
   - Thousands or even millions can exist simultaneously with low memory cost.
   - When a virtual thread BLOCKS (e.g., waiting for I/O, DB, HTTP call),
     the JVM unmounts it from the carrier (OS) thread and parks it.
   - The carrier thread is freed to run another virtual thread.
   - When the I/O completes, the virtual thread is mounted again (possibly on a
     different carrier thread) and resumes.
   - Your code stays SIMPLE (plain blocking style) but SCALES like reactive code.

   How to create:
     // Single virtual thread
     Thread vt = Thread.ofVirtual().start(() -> System.out.println("virtual!"));

     // Virtual thread per task executor
     ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
     executor.submit(() -> handleRequest());

   Spring Boot 3.2+ integration:
     # application.yml
     spring:
       threads:
         virtual:
           enabled: true
     # That's it – Tomcat and @Async now use virtual threads automatically.

   Virtual Thread vs Platform Thread:
   ┌──────────────────────┬──────────────────────┬──────────────────────┐
   │                      │ Platform Thread       │ Virtual Thread       │
   ├──────────────────────┼──────────────────────┼──────────────────────┤
   │ Managed by           │ OS                   │ JVM                  │
   │ Memory per thread    │ ~1 MB stack          │ ~few KB (dynamic)    │
   │ Max practical count  │ Thousands            │ Millions             │
   │ Blocking I/O         │ Blocks OS thread     │ Unmounts; frees carrier│
   │ Code style           │ Blocking (simple)    │ Blocking (simple)    │
   │ Context switch cost  │ High (OS)            │ Low (JVM)            │
   │ Pinning risk         │ N/A                  │ Yes (synchronized block│
   │                      │                      │ pins carrier thread) │
   └──────────────────────┴──────────────────────┴──────────────────────┘

   PINNING (key gotcha):
   If a virtual thread enters a synchronized block or calls native code,
   it gets PINNED to its carrier thread (can't unmount on block).
   Fix: replace synchronized with ReentrantLock for critical sections
   inside virtual-thread-heavy code.


 ── T4. HOW SERVICES COMMUNICATE WITH EACH OTHER ─────────────────────

   Two primary styles:

   A) SYNCHRONOUS (request/response) – caller waits for a reply
      ┌──────────────────────────────────────────────────────────┐
      │ REST over HTTP                                            │
      │ - Most common; uses HTTP verbs (GET, POST, PUT, DELETE). │
      │ - Java: RestTemplate (legacy) or WebClient (reactive).   │
      │ - Spring: @FeignClient for declarative REST calls.       │
      │   @FeignClient(name = "order-service")                   │
      │   interface OrderClient {                                 │
      │       @GetMapping("/orders/{id}")                        │
      │       Order getOrder(@PathVariable Long id);             │
      │   }                                                       │
      ├──────────────────────────────────────────────────────────┤
      │ gRPC                                                      │
      │ - Binary protocol (Protocol Buffers); faster than REST.  │
      │ - Strongly typed contracts via .proto files.             │
      │ - Ideal for internal service-to-service calls.           │
      └──────────────────────────────────────────────────────────┘

   B) ASYNCHRONOUS (fire and forget / event-driven) – caller doesn't wait
      ┌──────────────────────────────────────────────────────────┐
      │ Message Brokers                                           │
      │ - Kafka: high-throughput event streaming.                │
      │   Producer publishes to a topic; consumers subscribe.    │
      │ - RabbitMQ: traditional message queue (AMQP).            │
      │ - SQS/SNS (AWS managed): for cloud-native apps.          │
      │                                                           │
      │ When to use async:                                        │
      │ - Operations that don't need an immediate response.      │
      │ - Decoupling services (order placed → notify inventory,  │
      │   email, analytics independently).                        │
      │ - Handling spikes / buffering load.                      │
      └──────────────────────────────────────────────────────────┘

   Service Discovery (how services find each other's address):
   - Eureka (Netflix/Spring Cloud): services register on startup;
     client asks Eureka for the address of another service.
   - Kubernetes DNS: each service gets a stable DNS name inside the cluster.
   - API Gateway (Kong, Spring Cloud Gateway): single entry point;
     routes requests to the correct service.


 ── T5. HOW TO MAKE YOUR APPLICATION SCALABLE ────────────────────────

   Scalability = ability to handle growing load by adding resources.

   Two types:
   - Vertical scaling (scale up): bigger machine (more CPU/RAM). Has a ceiling.
   - Horizontal scaling (scale out): more instances. Preferred for microservices.

   Key strategies:

   1. Stateless Services
      Store no session state in the server process.
      Use JWT (stateless token) instead of HTTP sessions.
      Store shared state in Redis / DB so any instance can serve any request.

   2. Database Scaling
      - Read replicas: offload read queries to replicas; write only to primary.
      - Connection pooling: HikariCP (Spring Boot default) reuses DB connections.
      - Caching: Redis / Caffeine cache for frequently read, rarely changed data.
      - Sharding: partition data across multiple DB instances by key range or hash.

   3. Horizontal Pod Autoscaler (Kubernetes)
      Automatically adds/removes pods based on CPU, memory, or custom metrics.
        kubectl autoscale deployment order-service --min=2 --max=10 --cpu-percent=70

   4. Asynchronous Processing
      Offload slow/non-critical work to Kafka / queues so the HTTP thread
      responds immediately.

   5. Caching Layers
      - In-process: Caffeine (@Cacheable in Spring).
      - Distributed: Redis for shared cache across instances.
      - CDN: for static assets.

   6. Load Balancing (see T6)

   7. Circuit Breaker + Bulkhead (Resilience4j)
      Prevent one slow dependency from dragging down the whole app.

   8. Async / Non-blocking I/O
      Spring WebFlux + Reactor for I/O-bound services; or Virtual Threads (Java 21).


 ── T6. HOW LOAD BALANCING IS DONE ───────────────────────────────────

   Load balancing = distribute incoming requests across multiple service
   instances so no single instance is overwhelmed.

   LEVELS OF LOAD BALANCING:

   A) DNS Load Balancing
      DNS returns multiple IP addresses; client picks one.
      Simple but no health checks; client caches DNS → uneven distribution.

   B) Hardware / External Load Balancer (L4 / L7)
      - AWS ALB (Application Load Balancer) – L7, routes by URL path, headers.
      - AWS NLB (Network Load Balancer) – L4, ultra-low latency, TCP/UDP.
      - Nginx / HAProxy – popular open-source options.
      - Sits in front of all instances; clients talk only to the LB.

   C) API Gateway
      - Spring Cloud Gateway / Kong / AWS API Gateway.
      - Routes to the correct microservice + handles auth, rate limiting, logging.

   D) Client-Side Load Balancing (Spring Cloud)
      - The calling service itself picks which instance to call.
      - Spring Cloud LoadBalancer (replaces deprecated Ribbon):
          @LoadBalanced
          @Bean
          RestTemplate restTemplate() { return new RestTemplate(); }
        When you call http://order-service/orders, Spring resolves
        "order-service" to an actual instance via Eureka + round-robin.

   E) Kubernetes Service + kube-proxy
      A Kubernetes Service (ClusterIP) distributes traffic across all
      matching pods using iptables / IPVS rules at the node level.
      Ingress Controller (Nginx Ingress, Traefik) handles external traffic.

   LOAD BALANCING ALGORITHMS:
   ┌────────────────────────┬────────────────────────────────────────────┐
   │ Round Robin            │ Requests rotate across instances evenly.   │
   │ Weighted Round Robin   │ Instances with more capacity get more share│
   │ Least Connections      │ Route to instance with fewest active conns │
   │ IP Hash                │ Same client IP always → same instance       │
   │                        │ (useful for sticky sessions)               │
   │ Random                 │ Pick a random instance.                    │
   └────────────────────────┴────────────────────────────────────────────┘

   Health Checks:
   - LB periodically hits /actuator/health (Spring Boot Actuator).
   - Unhealthy instances are removed from rotation automatically.
   - Spring Boot: spring-boot-starter-actuator exposes /health endpoint.

   https://www.programiz.com/online-compiler/7cfzyKqKWgBjq
*/