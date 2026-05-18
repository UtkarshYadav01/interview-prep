package com.utkarsh.interview.may26;

import java.util.*;
import java.util.stream.*;

/**
 * Bounteous X Accolite Interview – 17th May 2026
 * <p>
 * ORDER OF QUESTIONS:
 * Part 1 – Theory  (asked first)
 * Part 2 – Coding  (asked later)
 */
public class BounteousXAccolite17thMay2026 {

    // ═══════════════════════════════════════════════════════════════
    //  PART 2 – CODING QUESTIONS  (code lives here; theory in comments below)
    // ═══════════════════════════════════════════════════════════════

    // ─────────────────────────────────────────────────────────────
    // CQ1. Implement LRU Cache
    //
    //  APPROACH: LinkedHashMap with accessOrder=true
    //  - accessOrder=true keeps entries in access order (LRU → MRU)
    //  - Override removeEldestEntry() to evict when capacity exceeded
    //  - get() and put() are both O(1)
    //
    //  Alternative (classic whiteboard answer):
    //  Manual Doubly-Linked List + HashMap
    //  - HashMap<key, Node> for O(1) lookup
    //  - DLL maintains access order: head=LRU, tail=MRU
    //  - On get: move node to tail
    //  - On put: add to tail; if over capacity, remove from head
    // ─────────────────────────────────────────────────────────────
    static class LRUCache {
        private final int capacity;
        private final LinkedHashMap<Integer, Integer> cache;

        LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                    return size() > capacity;   // auto-evict LRU entry
                }
            };
        }

        public int get(int key) {
            return cache.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            cache.put(key, value);
        }

        @Override
        public String toString() {
            return cache.toString();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // CQ2. Longest Substring Without Repeating Characters
    //      "abcabcbb" → 3 ("abc")
    //      "pwwkew"   → 3 ("wke")
    //      "bbbbb"    → 1 ("b")
    //
    //  APPROACH: Sliding Window
    //  - left & right pointers define the current window [left, right]
    //  - Map tracks the LAST SEEN INDEX of each character
    //  - When s[right] was seen inside the current window,
    //    advance left past that previous occurrence
    //  - Update maxLen at every step
    //  - Time O(n), Space O(min(n, charset))
    //
    //  WHY your original attempt failed:
    //  - Nested loops → O(n²)
    //  - `ans += s.charAt(j)` doesn't extract a valid substring
    //  - The HashSet collected from the whole string, not per-window
    // ─────────────────────────────────────────────────────────────
    static int cq2_longestSubstringNoRepeat(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLen = 0, left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // Shrink window only if repeat is INSIDE current window
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;
            }
            lastSeen.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // ─────────────────────────────────────────────────────────────
    // CQ3. 2nd Highest Salary – Java Streams
    //      SQL answers in theory section below (T16)
    // ─────────────────────────────────────────────────────────────
    record Employee(String name, double salary) {
    }

    static void cq3_secondHighestSalary() {
        List<Employee> employees = List.of(
                new Employee("Alice", 90_000),
                new Employee("Bob", 120_000),
                new Employee("Carol", 120_000),   // duplicate of highest
                new Employee("Dave", 85_000)
        );

        // distinct() so duplicate 120k counts as ONE rank, not two
        Optional<Double> secondSalary = employees.stream()
                .map(Employee::salary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();

        secondSalary.ifPresent(sal -> {
            List<Employee> result = employees.stream()
                    .filter(e -> e.salary() == sal)
                    .toList();
            System.out.println("2nd highest salary : " + sal);
            System.out.println("Employee(s)        : " + result);
        });
    }

    // ─────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=== CQ1: LRU Cache ===");
        LRUCache lru = new LRUCache(3);
        lru.put(1, 10);
        lru.put(2, 20);
        lru.put(3, 30);
        System.out.println(lru.get(1));   // 10  (key 1 now MRU)
        lru.put(4, 40);                   // evicts key 2 (LRU)
        System.out.println(lru.get(2));   // -1  (evicted)
        System.out.println(lru.get(3));   // 30
        System.out.println(lru.get(4));   // 40

        System.out.println("\n=== CQ2: Longest Substring Without Repeating Chars ===");
        System.out.println(cq2_longestSubstringNoRepeat("abcabcbb")); // 3
        System.out.println(cq2_longestSubstringNoRepeat("pwwkew"));   // 3
        System.out.println(cq2_longestSubstringNoRepeat("bbbbb"));    // 1

        System.out.println("\n=== CQ3: 2nd Highest Salary (Streams) ===");
        cq3_secondHighestSalary();
    }
}


/*
 ═══════════════════════════════════════════════════════════════════
  PART 1 – THEORY QUESTIONS  (asked first in interview)
 ═══════════════════════════════════════════════════════════════════

 ── T1. JAVA 17 KEY FEATURES ────────────────────────────────────────

   1. Sealed Classes (JEP 409) – final in Java 17
      Restrict which classes can extend/implement a type.
        public sealed interface Shape permits Circle, Rectangle, Triangle {}
      Subtypes must be: final | sealed | non-sealed.
      Works beautifully with pattern matching in switch.

   2. Pattern Matching for instanceof (JEP 394) – final in Java 16
        if (obj instanceof String s) {
            System.out.println(s.toUpperCase()); // no explicit cast needed
        }

   3. Records (JEP 395) – final in Java 16
      Immutable data carrier; auto-generates constructor, getters,
      equals(), hashCode(), toString().
        record Point(int x, int y) {}

   4. Text Blocks (JEP 378) – final in Java 15
      Multi-line string literals with triple-quote delimiters.
        String json = """
                      { "name": "Utkarsh" }
                      """;

   5. Switch Expressions (JEP 361) – final in Java 14
      Switch as an expression (returns a value); arrow syntax; no fall-through.
        int result = switch (day) {
            case MONDAY, FRIDAY -> 6;
            case SUNDAY         -> 0;
            default             -> 4;
        };

   6. Helpful NullPointerExceptions (JEP 358) – Java 14
      JVM tells you exactly WHICH variable was null (not just line number).

   7. Strong Encapsulation of JDK Internals (JEP 403)
      Internal APIs (sun.*, com.sun.*) are strongly encapsulated;
      --add-opens no longer works without explicit JVM flags.

   8. RandomGenerator API (JEP 356)
      New hierarchy: RandomGenerator, SplittableGenerator, etc.
      Modern algorithms (Xoshiro256**, LXM) via RandomGeneratorFactory.


 ── T2. WHY IS STRING IMMUTABLE IN JAVA? ────────────────────────────

   a) Security
      DB URLs, file paths, class names are Strings. Mutability would let
      malicious code alter them after validation (TOCTOU attacks).

   b) Thread Safety
      Immutable objects are inherently thread-safe; no synchronisation needed
      when sharing a String across threads.

   c) String Pool / Interning
      JVM keeps a pool of String literals. Multiple references safely share
      the same object only because no one can mutate it.
        String a = "hello";
        String b = "hello"; // same pool object; a == b → true

   d) hashCode Caching
      String caches its hashCode after first call. Safe only because the
      content can never change.

   Implementation:
     private final byte[] value;  // Java 9+ compact strings; was char[]
     No setter. value is private and never exposed directly.
     Class is declared final → cannot be subclassed to add mutability.


 ── T3. HashMap vs ConcurrentHashMap vs Collections.synchronizedMap ──

   HashMap
   - Not thread-safe. Concurrent writes → data corruption / infinite loop.
   - Allows one null key, multiple null values.
   - Best performance in single-threaded context.

   Collections.synchronizedMap(new HashMap<>())
   - Wraps every method in synchronized(mutex) – coarse lock.
   - ONE thread can read OR write at a time → bottleneck.
   - Iteration is NOT thread-safe; must manually synchronize:
       synchronized(map) { for (var e : map.entrySet()) { ... } }
   - Allows null keys/values (delegates to the wrapped map).

   ConcurrentHashMap
   - Thread-safe with fine-grained bucket-level locking (Java 8+).
   - Reads are lock-free (volatile).
   - Writes lock only the affected bucket (CAS + synchronized on bin head).
   - Does NOT allow null keys or values → throws NullPointerException.
   - Atomic compound ops: computeIfAbsent(), merge(), putIfAbsent().
   - Best choice for concurrent read-heavy or moderate-write workloads.

   ┌──────────────────────┬──────────┬──────────────────┬────────────────────┐
   │ Feature              │ HashMap  │ synchronizedMap  │ ConcurrentHashMap  │
   ├──────────────────────┼──────────┼──────────────────┼────────────────────┤
   │ Thread-safe          │ No       │ Yes (coarse)     │ Yes (fine-grained) │
   │ Null key/value       │ Yes      │ Yes              │ No                 │
   │ Read perf (MT)       │ Unsafe   │ Low (blocks)     │ High (lock-free)   │
   │ Write perf (MT)      │ Unsafe   │ Low (blocks all) │ High (bucket lock) │
   │ Iteration thread-safe│ No       │ No (manual sync) │ Yes (weakly consis)│
   └──────────────────────┴──────────┴──────────────────┴────────────────────┘


 ── T4. SPRING SECURITY – HOW OAUTH2 WORKS ──────────────────────────

   OAuth2 = authorization framework, not authentication.
   Spring Boot setup:
     Dependency: spring-boot-starter-oauth2-client
     application.yml:
       spring.security.oauth2.client.registration.google:
         client-id: YOUR_ID
         client-secret: YOUR_SECRET
         scope: openid, profile, email

   Authorization Code Grant Flow:
     1. User clicks "Login with Google".
     2. Redirect to Google's /authorize endpoint.
     3. User logs in + consents.
     4. Google redirects back to your callback URL with ?code=AUTH_CODE.
     5. Spring exchanges code for access_token + id_token (server-to-server).
     6. Spring creates OAuth2AuthenticationToken; stored in SecurityContext.

   Custom config:
     @Bean SecurityFilterChain chain(HttpSecurity http) throws Exception {
         http.oauth2Login(o -> o
             .defaultSuccessUrl("/dashboard")
             .userInfoEndpoint(u -> u.userService(myUserService))
         );
         return http.build();
     }


 ── T5. HOW JWT WORKS ────────────────────────────────────────────────

   JWT = JSON Web Token. Three Base64URL-encoded parts: header.payload.signature

   Header:    { "alg": "HS256", "typ": "JWT" }
   Payload:   { "sub": "userId", "roles": ["ADMIN"], "exp": 1716000000 }
   Signature: HMACSHA256(base64url(header) + "." + base64url(payload), secret)

   Spring Boot flow:
   1. On login: generate token
        String token = Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + 86_400_000))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();

   2. Client sends: Authorization: Bearer <token> with every request.

   3. JwtAuthenticationFilter (extends OncePerRequestFilter):
      - Extract token from header.
      - Validate signature + expiry via Jwts.parserBuilder().
      - Set Authentication in SecurityContextHolder.

   Key points:
   - JWT is SIGNED (integrity), not encrypted (payload is readable if decoded).
   - For sensitive data use JWE (JSON Web Encryption).
   - Store in httpOnly cookie (not localStorage) to prevent XSS.
   - Short expiry (15 min) + refresh token pattern for security.
   - Stateless → no server-side session → scales horizontally easily.


 ── T6. SAGA PATTERN ─────────────────────────────────────────────────

   Problem: A business transaction spans multiple microservices.
   Traditional ACID transactions don't work across service boundaries.

   Saga = sequence of local transactions, each publishing an event to
   trigger the next step. On failure → compensating transactions roll back.

   A) Choreography (event-driven, decentralised)
      Each service listens for events and reacts.
      OrderService → emits "OrderCreated"
      PaymentService → listens → charges card → emits "PaymentCompleted"
      InventoryService → listens → reserves stock → emits "StockReserved"
      Pro: loose coupling.  Con: hard to trace overall saga state.

   B) Orchestration (centralised)
      A Saga Orchestrator sends commands to each service and awaits replies.
      OrderSaga → calls PaymentService → on success calls InventoryService.
      Pro: clear flow, easy to debug.  Con: orchestrator is a single point.

   Compensating transactions (rollback):
     Payment charged  → if inventory fails → refund payment
     Stock reserved   → if shipping fails  → release reservation


 ── T7. HOW TO PREVENT CASCADING FAILURES ────────────────────────────

   Cascading failure: Service A fails → caller B piles up blocked threads
   → B exhausts resources → B fails → propagates upstream.

   Patterns:

   1. Circuit Breaker (most important – Resilience4j)
      CLOSED (normal) → OPEN (fast-fail) → HALF-OPEN (probe)
      When failure rate exceeds threshold → OPEN → reject immediately.
      After cooldown → HALF-OPEN → one probe request.
        @CircuitBreaker(name = "payment", fallbackMethod = "paymentFallback")

   2. Timeout
      Never wait forever. Set connect + read timeouts on all HTTP clients.

   3. Bulkhead
      Isolate thread pools per downstream service so one slow dependency
      cannot exhaust the shared pool.

   4. Retry with Exponential Backoff + Jitter
      Retry transient failures, not indefinitely.
      Add random jitter to avoid thundering herd on recovery.
        @Retry(name = "inventory", fallbackMethod = "fallback")

   5. Rate Limiting (see T9)
      Protect your own service from being overwhelmed by upstream.

   6. Fallback / Graceful Degradation
      Return cached data, a default response, or a friendly error
      instead of propagating the failure.


 ── T8. CQRS – COMMAND QUERY RESPONSIBILITY SEGREGATION ──────────────

   Core idea: Separate the write model (Commands) from the read model (Queries).

   Command side (writes):
   - Handles create/update/delete.
   - Optimised for consistency and business rule enforcement.
   - Writes to a Command DB (e.g., normalised relational DB).
   - Emits domain events on state change.

   Query side (reads):
   - Handles reads only.
   - Optimised for query performance; can be denormalised / pre-projected.
   - Updated by consuming events from the Command side.
   - Can use a different store (Redis, Elasticsearch, read replica).

   Example flow:
     POST /orders → OrderCommandService → saves to Postgres → emits OrderCreated
     OrderCreated event → OrderProjectionHandler → updates Redis view
     GET  /orders/{id} → OrderQueryService → reads from Redis (fast)

   Benefits:
   - Read and write sides scale independently.
   - Queries are fast (no joins; pre-projected views).
   - Natural fit with Event Sourcing.

   Trade-offs:
   - Eventual consistency between command and query sides.
   - More complexity; two models to maintain.


 ── T9. RATE THROTTLING (Rate Limiting) ──────────────────────────────

   Controlling how many requests a client can make in a given time window.

   Algorithms:

   1. Token Bucket (most common in practice)
      Bucket holds N tokens; refilled at a fixed rate.
      Each request consumes 1 token; if empty → reject.
      Allows short bursts up to bucket capacity.

   2. Fixed Window Counter
      Count requests per fixed window (e.g. 100 req/min).
      Vulnerable to boundary burst (100 at :59 + 100 at 1:01 = 200 in 2 sec).

   3. Sliding Window Log
      Record timestamp of each request; count those in last N seconds.
      Accurate but memory-intensive.

   4. Leaky Bucket
      Requests enter a queue; processed at fixed rate.
      Smooths bursts; no burst allowance.

   Spring / Java options:
   - Resilience4j @RateLimiter
   - Spring Cloud Gateway built-in Redis-backed rate limiter
   - Bucket4j library (token bucket, production-grade)

   Distributed: use Redis atomic INCR + EXPIRE to share counters across instances.


 ── T10. RACE CONDITION ───────────────────────────────────────────────

   Occurs when two or more threads access shared mutable data concurrently
   and the final result depends on the unpredictable scheduling order.

   Classic bank example:
     Thread A reads balance = 1000
     Thread B reads balance = 1000
     Thread A writes 1000 – 500 = 500
     Thread B writes 1000 – 300 = 700  ← overwrites A!
     Final = 700 instead of correct 200.

   Fixes:
   - synchronized block / method
   - ReentrantLock (explicit, supports tryLock, fairness)
   - AtomicInteger.compareAndSet() (lock-free CAS)
   - DB level: SELECT FOR UPDATE (pessimistic) or @Version (optimistic)


 ── T11. DEADLOCK ─────────────────────────────────────────────────────

   Two or more threads permanently blocked, each waiting for a lock
   held by the other.

   Coffman Conditions (ALL four must hold for deadlock):
   1. Mutual Exclusion – resource held exclusively.
   2. Hold and Wait – thread holds one lock while waiting for another.
   3. No Preemption – locks can't be forcibly taken.
   4. Circular Wait – T1 waits for T2; T2 waits for T1.

   Classic example:
     Thread A: synchronized(lockA) { synchronized(lockB) { ... } }
     Thread B: synchronized(lockB) { synchronized(lockA) { ... } }

   Prevention:
   - Always acquire locks in the same GLOBAL ORDER across all threads.
   - Use tryLock(timeout) – release held lock if can't acquire next one.
   - Reduce lock granularity; prefer higher-level utilities (ConcurrentHashMap).
   - Detect with jstack / thread dump → look for "waiting to lock" cycles.


 ── T12. INTERNAL IMPLEMENTATION OF CompletableFuture ────────────────

   CompletableFuture<T> implements Future<T> + CompletionStage<T>.

   Internally (OpenJDK source):
   - volatile Object result field:
       null = incomplete
       AltResult wrapper = exceptional completion or null value
       actual value = normal completion
   - Linked stack of UniCompletion / BiCompletion nodes (dependent actions).
   - Each thenApply/thenAccept creates a node chained onto the stack.
   - On completion: walks the stack and triggers each dependent node.

   Thread model:
   - supplyAsync(fn)            → ForkJoinPool.commonPool()
   - supplyAsync(fn, executor)  → your executor
   - thenApply(fn)              → same thread that completed future (or caller if already done)
   - thenApplyAsync(fn)         → ForkJoinPool or given executor (new thread)

   Key combinators:
     CompletableFuture.allOf(cf1, cf2, cf3).join(); // wait for all to complete
     CompletableFuture.anyOf(cf1, cf2, cf3).join(); // wait for first to complete

     CompletableFuture<String> result = CompletableFuture
         .supplyAsync(() -> fetchFromDB())
         .thenApply(data -> transform(data))
         .exceptionally(ex -> "fallback")
         .whenComplete((res, ex) -> log(res, ex));


 ── T13. EXECUTOR FRAMEWORK ───────────────────────────────────────────

   Problem: new Thread per task → expensive (OS cost, memory).
   Solution: pool of reusable threads managed by ExecutorService.

   Core interfaces:
   - Executor              – execute(Runnable)
   - ExecutorService       – submit(), shutdown(), invokeAll(), invokeAny()
   - ScheduledExecutorService – scheduleAtFixedRate(), schedule()

   Factory methods (Executors class):
   ┌──────────────────────────────────┬───────────────────────────────────────────┐
   │ newFixedThreadPool(n)            │ Fixed n threads; extra tasks queue         │
   │ newCachedThreadPool()            │ Grows as needed; idle threads expire (60s) │
   │ newSingleThreadExecutor()        │ 1 thread; sequential processing             │
   │ newScheduledThreadPool(n)        │ Delayed / periodic tasks                    │
   │ newWorkStealingPool()            │ ForkJoinPool; ideal for CPU-bound tasks     │
   └──────────────────────────────────┴───────────────────────────────────────────┘

   ThreadPoolExecutor internals:
   - corePoolSize: min threads kept alive.
   - maximumPoolSize: upper limit under load.
   - keepAliveTime: idle extra threads expire after this.
   - BlockingQueue: holds pending tasks (LinkedBlockingQueue default = unbounded!).
   - RejectedExecutionHandler: AbortPolicy (throws), CallerRunsPolicy (runs on caller).

   Always shut down gracefully:
     executor.shutdown();
     executor.awaitTermination(30, TimeUnit.SECONDS);


 ── T14. OPTIMISTIC vs PESSIMISTIC LOCKING ────────────────────────────

   PESSIMISTIC LOCKING
   - Assumes conflicts WILL happen → lock the row before reading.
   - SQL: SELECT * FROM orders WHERE id=1 FOR UPDATE;
   - JPA: @Lock(LockModeType.PESSIMISTIC_WRITE)
   - Other transactions BLOCK until lock is released.
   - Risk of deadlock; reduces concurrency.
   - Best for high-contention (seat booking, inventory deduction).

   OPTIMISTIC LOCKING
   - Assumes conflicts are RARE → no lock taken; detect conflict at commit.
   - Uses a version column:
       @Version
       private int version;
   - UPDATE: WHERE id=? AND version=?
     → 0 rows updated = conflict → throw OptimisticLockException → retry.
   - No blocking; high concurrency; no deadlock risk.
   - Best for low-contention (user profile updates, read-heavy workloads).

   ┌──────────────────┬───────────────────────┬────────────────────────┐
   │                  │ Optimistic            │ Pessimistic            │
   ├──────────────────┼───────────────────────┼────────────────────────┤
   │ Lock timing      │ At commit (version)   │ At read (row lock)     │
   │ Concurrency      │ High                  │ Low                    │
   │ Deadlock risk    │ None                  │ Yes                    │
   │ Conflict cost    │ Retry                 │ Wait / timeout         │
   │ Best for         │ Low contention        │ High contention        │
   └──────────────────┴───────────────────────┴────────────────────────┘


 ── T15. KAFKA – WHAT HAPPENS WHEN PRODUCER SENDS A MESSAGE? ─────────

   1. Producer calls send(ProducerRecord<K,V>).
   2. Serialiser converts key + value to bytes.
   3. Partitioner picks target partition:
      - Explicit partition set → use it.
      - Key present → hash(key) % numPartitions (same key → same partition, ordering guaranteed).
      - No key → round-robin or sticky partitioner (batches records for same partition).
   4. Record added to in-memory RecordAccumulator (batch buffer per partition).
   5. Sender thread picks batches and sends to the Leader broker.
   6. Leader appends to its local commit log (append-only segment file).
   7. Followers fetch and replicate the record.
   8. Broker acks based on acks config:
      - acks=0  → fire and forget (no ack, fastest, data loss possible).
      - acks=1  → ack after leader write (default, leader failure = loss).
      - acks=all→ ack after ALL ISR replicas confirm (safest, highest latency).
   9. Producer callback / Future completes.

   Key producer configs:
   - linger.ms       : wait up to N ms to batch more records.
   - batch.size      : max bytes per batch.
   - retries         : auto-retry transient failures.
   - enable.idempotence=true: exactly-once (dedup by producer-id + sequence number).


 ── T16. WHAT HAPPENS WHEN KAFKA BROKER IS DOWN? ─────────────────────

   Kafka is fault-tolerant through replication (replication.factor ≥ 3 recommended).

   Scenario A – A FOLLOWER broker goes down:
   - No impact on producers/consumers.
   - Partition leader keeps serving reads/writes.
   - Downed broker removed from ISR.
   - On restart: catches up missed messages, rejoins ISR.

   Scenario B – The LEADER broker goes down:
   - ZooKeeper (or KRaft in Kafka 3+) detects failure via missed heartbeat.
   - Controller broker triggers leader election.
   - New leader elected from ISR (in-sync replica → no data loss).
   - Producers retry (automatic) and get updated metadata pointing to new leader.
   - Consumer group coordinator reassigns partitions if broker hosted a coordinator.
   - Downtime: typically milliseconds to a few seconds.

   Scenario C – ALL ISR brokers for a partition go down:
   - unclean.leader.election.enable=false (default/safe):
     Partition goes OFFLINE until an ISR member returns. No data loss.
   - unclean.leader.election.enable=true:
     An out-of-sync replica elected; service resumes but messages may be LOST.

   Producer behaviour when broker is down:
   - Gets NOT_LEADER_FOR_PARTITION or connection error.
   - With retries > 0 + retry.backoff.ms → retries automatically.
   - Messages buffer in RecordAccumulator up to max.block.ms → then exception.
   - enable.idempotence=true + acks=all → exactly-once even across retries.

   Consumer behaviour when broker is down:
   - Fetches updated metadata → discovers new partition leader.
   - Reconnects and resumes from last committed offset in __consumer_offsets topic.
   - No message loss (messages replicated; offsets persisted).


 ═══════════════════════════════════════════════════════════════════
  PART 2 – CODING QUESTIONS  (code above, SQL answers below)
 ═══════════════════════════════════════════════════════════════════

 CQ1. LRU Cache → LRUCache class above.

 CQ2. Longest Substring → cq2_longestSubstringNoRepeat() above.

 CQ3. 2nd Highest Salary WITHOUT LIMIT – SQL answers

   A) Correlated subquery (fully portable, no LIMIT):
      SELECT id, name, salary
      FROM employee e1
      WHERE 1 = (
          SELECT COUNT(DISTINCT salary)
          FROM employee e2
          WHERE e2.salary > e1.salary
      );
      → "Give me rows where exactly 1 distinct salary is higher than mine."

   B) MAX excluding the highest (simplest):
      SELECT MAX(salary) AS second_highest
      FROM employee
      WHERE salary < (SELECT MAX(salary) FROM employee);

   C) DENSE_RANK (most impressive, handles ties perfectly):
      SELECT name, salary
      FROM (
          SELECT name, salary,
                 DENSE_RANK() OVER (ORDER BY salary DESC) AS rnk
          FROM employee
      ) ranked
      WHERE rnk = 2;
      → DENSE_RANK gives tied rows the same rank (120k,120k → both rank 1).
      → rnk = 2 correctly picks the 2nd distinct salary level.

      Find 2nd highest salary without LIMIT.

        select id, name, salary from employee em
        inner join
        order by salary
        (select id, name, salary from employee e where e.salary= max(salary))as innerq

        select id, name, salary from employee
        order by salary
        limit(2,1)

     //https://www.programiz.com/online-compiler/27DeFmtAxOEgm
*/