package com.utkarsh.interview.june26;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Deloitte Interview – Round 2 – 4th June 2026
 */
public class Deloitte_R2_04June2026 {

    // ─────────────────────────────────────────────────────────────
    // Student – used for HashMap mutable-key questions below
    // ─────────────────────────────────────────────────────────────
    static class Student {
        private Integer id;
        private String name;
        private String email;

        public Student(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public Integer getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String n) {
            this.name = n;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String e) {
            this.email = e;
        }

        // hashCode + equals based on `id` – ENABLED for Q2, see comments
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Student s)) return false;
            return Objects.equals(id, s.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Student{id=" + id + ", name='" + name + "'}";
        }
    }

    // ─────────────────────────────────────────────────────────────
    // Q1. HashMap with mutable key – WITHOUT hashCode / equals
    //
    // Scenario: Student uses default Object.hashCode() (identity-based).
    // Mutation (setId) does NOT change the hash → HashMap still finds key.
    //
    // OUTPUT:
    //     Student{id=2, name='Bob'}    ← found: hash(s2) unchanged
    //     Student{id=4, name='Bob'}    ← still found: identity hash = same
    //
    // WHY: Object's default hashCode is derived from object identity
    // (memory address), not field values.  Mutating a field has no effect
    // on the bucket the entry lives in, so get() still finds it.
    // ─────────────────────────────────────────────────────────────
    static void q1_withoutHashcodeEquals() {
        /*
         * To test Q1, comment out the @Override hashCode/equals in Student.
         * With default Object identity:
         *   - s2 is placed in bucket for hash(identity of s2)
         *   - setId(4) mutates the field but NOT the identity
         *   - map.get(s2) recomputes hash(identity of s2) → same bucket → found
         */
        System.out.println("─── Q1: without hashCode/equals ───");
        Map<Student, Student> map = new HashMap<>();
        Student s2 = new Student(2, "Bob", "bob@email.com");
        map.put(s2, s2);

        System.out.println(map.get(s2));   // Student{id=2, name='Bob'}
        s2.setId(4);
        System.out.println(map.get(s2));   // Student{id=4, name='Bob'} ← still found
    }

    // ─────────────────────────────────────────────────────────────
    // Q2. HashMap with mutable key – WITH hashCode / equals (id-based)
    //
    // OUTPUT:
    //     Student{id=2, name='Bob'}    ← found: hash(id=2) → correct bucket
    //     null                         ← NOT found: hash(id=4) → wrong bucket!
    //
    // WHY: hashCode is now computed from id.
    //   - s2 was stored in bucket for hash(2).
    //   - After setId(4), get(s2) computes hash(4) → different bucket → null.
    //   - The entry for s2 is now ORPHANED – it lives in bucket hash(2) but
    //     can never be reached again via s2 as a key.
    //
    // ROOT CAUSE: Using a MUTABLE object as a HashMap key breaks the
    // hashCode contract. Rule: keys should be immutable (String, Integer,
    // UUID, records) or at least have identity-based hashing.
    // ─────────────────────────────────────────────────────────────
    static void q2_withHashcodeEquals() {
        System.out.println("─── Q2: with hashCode/equals (id-based) ───");
        Map<Student, Student> map = new HashMap<>();

        Student s1 = new Student(1, "Alice", "alice@email.com");
        Student s2 = new Student(2, "Bob", "bob@email.com");
        Student s3 = new Student(3, "Charlie", "charlie@email.com");

        map.put(s1, s1);
        map.put(s2, s2);
        map.put(s3, s3);

        System.out.println(map.get(s2));   // Student{id=2, name='Bob'}
        s2.setId(4);
        System.out.println(map.get(s2));   // null  ← hash changed, wrong bucket
    }

    public static void main(String[] args) {
        q1_withoutHashcodeEquals();
        System.out.println();
        q2_withHashcodeEquals();
    }
}


/*
 ═══════════════════════════════════════════════════════════════════
  SQL QUESTIONS
 ═══════════════════════════════════════════════════════════════════

Write an SQL query to find the average salary for each department.

        EmployeeIDﾠNameﾠDepartmentﾠEmployeeTypeﾠEmailﾠTitle
        1ﾠJohn DoeﾠITﾠAssociateﾠjohndoe@company.comﾠSoftware Engineer
        2ﾠJane SmithﾠHRﾠAssociateﾠjanesmith@company.comﾠHR Specialist
        3ﾠSam LeeﾠFinanceﾠAssociateﾠsamlee@company.comﾠAccountant

        PayrollIDﾠEmployeeIDﾠPayDateﾠAmount
        101ﾠ1ﾠ2026-05-31ﾠ4000.00
        102ﾠ2ﾠ2026-05-31ﾠ3800.00
        103ﾠ1ﾠ2026-04-30ﾠ4000.00
        104ﾠ3ﾠ2026-05-31ﾠ4200.00
        Notes:
        EmployeeID is the primary key in the Employee table and a foreign key in the Payroll table.
                PayrollID is the primary key in the Payroll table.
        PayDate stores the date of payment.
        Amount represents the salary paid for that period.

        select e.department, average(p.Amount) as salaries
        from employee e
        join payroll p
        on e.EmployeeID=p.EmployeeID
        group by e.department

        department | salaries
        HR  |  2000
        IT  |  1500

*/


/*
 ═══════════════════════════════════════════════════════════════════
  THEORY QUESTIONS
 ═══════════════════════════════════════════════════════════════════

 1. EXPLAIN YOUR PROJECT
    ─────────────────────
    Prepare a 2-min STAR-format answer covering:
    • What the system does (problem statement)
    • Your role and tech stack
    • A specific challenge you solved (e.g. latency, scaling, bug)
    • Outcome / impact (numbers help: "reduced response time by 40%")


 2. SPRING SECURITY – HOW DOES AUTHORIZATION WORK?
    ──────────────────────────────────────────────────
    Authentication:  "Who are you?"  → validate credentials, issue JWT.
    Authorization:   "Are you allowed?" → check roles/permissions on each request.

    Flow (JWT-based):
      Request → JwtAuthFilter (extracts + validates token)
              → SecurityContextHolder.setAuthentication(authToken)
              → Spring checks @PreAuthorize / hasRole() on the endpoint
              → Allow or 403 Forbidden.

    Key beans: SecurityFilterChain, UserDetailsService, JwtAuthenticationFilter.


 3. IF I CHANGE USER TYPE IN THE PAYLOAD, WILL I BE AUTHORIZED?
    ───────────────────────────────────────────────────────────────
    NO – if implemented correctly.
    The JWT is signed with a secret key (HS256) or private key (RS256).
    Tampering with any claim (e.g. role: USER → ADMIN) invalidates the
    signature.  The server re-validates the signature on every request;
    a tampered token will fail signature verification and be rejected (401).

    If someone COULD modify the token and still get authorized → broken
    auth implementation (e.g. signature not verified, alg:none accepted).


 4. REDIS CACHING IN A SPRING BOOT APP
    ──────────────────────────────────────
    Dependencies: spring-boot-starter-data-redis + spring-boot-starter-cache

    application.yml:
        spring.cache.type: redis
        spring.data.redis.host: localhost
        spring.data.redis.port: 6379

    Enable:  @EnableCaching on @SpringBootApplication class.

    Usage:
        @Cacheable("employees")        // read-through cache
        @CachePut("employees")         // always updates cache + DB
        @CacheEvict("employees")       // removes from cache (e.g. on delete)

    Key generation: default = method args; custom = @Cacheable(key="#id")


 5. CACHE EVICTION POLICIES
    ─────────────────────────
    LRU  – Least Recently Used     → evict the entry not accessed for longest
    LFU  – Least Frequently Used   → evict the entry accessed fewest times
    TTL  – Time-To-Live             → evict after a fixed duration (Redis default)
    FIFO – First In, First Out      → evict oldest inserted entry

    Redis default: noeviction (returns error when full).
    Common prod setting: allkeys-lru or volatile-lru.
    Configure in Spring: RedisCacheConfiguration.entryTtl(Duration.ofMinutes(10))


 6. WHAT IS A POD?
    ─────────────────
    The smallest deployable unit in Kubernetes.
    A Pod wraps one or more containers that share:
      - Network namespace (same IP + ports)
      - Storage volumes
      - Lifecycle

    Typically 1 container per Pod (sidecar pattern is the exception).
    Pods are ephemeral; controllers (Deployment, ReplicaSet) manage restarts.


 7. CONFIGURE POSTGRESQL IN SPRING BOOT
    ──────────────────────────────────────
    Dependency: spring-boot-starter-data-jpa + postgresql driver

    application.yml:
        spring:
          datasource:
            url:      jdbc:postgresql://localhost:5432/mydb
            username: postgres
            password: secret
            driver-class-name: org.postgresql.Driver
          jpa:
            hibernate.ddl-auto: update
            show-sql: true
            database-platform: org.hibernate.dialect.PostgreSQLDialect


 8. CompletableFuture
    ─────────────────────
    Non-blocking async computation introduced in Java 8.

    Key methods:
      supplyAsync(supplier)         → runs task on ForkJoinPool, returns future
      thenApply(fn)                 → transform result (like Stream.map)
      thenAccept(consumer)          → consume result, returns void
      thenCombine(other, fn)        → combine two futures
      exceptionally(fn)             → handle exceptions
      join() / get()                → block until result ready

    Example:
        CompletableFuture.supplyAsync(() -> fetchUser(id))
            .thenApply(user -> enrichUser(user))
            .thenAccept(System.out::println)
            .exceptionally(ex -> { log(ex); return null; });


 9. HOW TO INCREASE KAFKA THROUGHPUT
    ─────────────────────────────────
    PRODUCER side:
      • batch.size          → larger batches (default 16KB → try 64–128KB)
      • linger.ms           → wait a few ms to fill batch before sending
      • compression.type    → snappy / lz4 / zstd (reduces network I/O)
      • acks=1 or acks=0    → sacrifice durability for speed (vs acks=all)

    CONSUMER side:
      • Increase partition count → more parallelism (more consumers in group)
      • fetch.min.bytes / fetch.max.wait.ms → fetch larger chunks less often
      • max.poll.records     → process more records per poll()

    BROKER side:
      • Add brokers / partitions
      • Tune num.io.threads, num.network.threads


 10. CAN MULTIPLE CONSUMERS READ FROM THE SAME PARTITION?
     ───────────────────────────────────────────────────────
     NO – within the same Consumer Group.
     Each partition is assigned to exactly ONE consumer in a group.
     This guarantees ordering within a partition.

     YES – across DIFFERENT Consumer Groups.
     Each group maintains its own independent offset, so multiple groups
     can read the same partition simultaneously (e.g. one group for
     analytics, another for notifications).


 11. STEP vs JOB IN SPRING BATCH
     ──────────────────────────────
     Job  : the overall batch process; top-level container.
             Has a name, JobParameters, and is made up of one or more Steps.
             Launched by JobLauncher; tracked in JobRepository.

     Step : a single phase inside a Job (read → process → write).
             Can be Tasklet-based (single method) or Chunk-based (read/process/write).
             Steps run sequentially by default; can be conditional or parallel.

     Analogy:
         Job  = "Monthly Payroll Run"
         Step = "Read Employees" → "Calculate Tax" → "Write Payslips"

 12. HOW TO TEST A PRIVATE METHOD IN JUNIT / SPRING BOOT
     ──────────────────────────────────────────────────────

     First, the design principle:
     Private methods are implementation details — ideally you test them
     INDIRECTLY through the public methods that call them.
     If a private method feels hard to test, it's usually a signal it
     should be extracted into its own class.

     That said, three practical approaches exist:

     ─────────────────────────────────────────────────────
     OPTION A – Reflection (plain Java, no extra library)
     ─────────────────────────────────────────────────────
     Use java.lang.reflect.Method to access and invoke a private method.

         class MathUtils {
             private int square(int n) { return n * n; }
         }

         @Test
         void testSquareViaReflection() throws Exception {
             MathUtils utils = new MathUtils();

             Method method = MathUtils.class.getDeclaredMethod("square", int.class);
             method.setAccessible(true);                        // bypass private

             int result = (int) method.invoke(utils, 5);
             assertEquals(25, result);
         }

     Pros  : no extra dependency.
     Cons  : verbose; breaks if method is renamed (no compile-time safety);
             setAccessible(true) may be restricted by Java module system (Java 9+).

     ─────────────────────────────────────────────────────
     OPTION B – ReflectionTestUtils (Spring Test utility)
     ─────────────────────────────────────────────────────
     Spring provides a cleaner wrapper around reflection.
     Available in spring-test (already on classpath in Spring Boot test).

         @Test
         void testSquareViaReflectionTestUtils() {
             MathUtils utils = new MathUtils();

             int result = (int) ReflectionTestUtils.invokeMethod(utils, "square", 5);

             assertEquals(25, result);
         }

     Pros  : cleaner API; handles the setAccessible boilerplate.
     Cons  : still reflection under the hood; same rename risk.
     Use when: already in a Spring Boot project and want a one-liner.

     ─────────────────────────────────────────────────────
     OPTION C – Change visibility to package-private (preferred refactor)
     ─────────────────────────────────────────────────────
     If the method genuinely needs thorough testing, change from
     `private` to package-private (no modifier) and add a comment:

         // package-private for testing
    int square(int n) { return n * n; }

    Then test it directly — no reflection needed.
    The test class lives in the same package (standard Maven layout).

            ─────────────────────────────────────────────────────
    OPTION D – Extract to a separate class (best design)
            ─────────────────────────────────────────────────────
    If a private method is complex enough to need isolated tests,
    it belongs in its own public class/service.
    Extract it → inject it → test it directly + mock it in the parent.

         ─────────────────────────────────────────────────────
    SUMMARY TABLE
         ─────────────────────────────────────────────────────
    Approach                  | Dependency        | Verdict
         ──────────────────────────|───────────────────|─────────────────
    Reflect via getDeclared…  | none              | works, verbose
    ReflectionTestUtils       | spring-test       | cleaner, Spring projects
    Package-private           | none              | clean, test-friendly
    Extract to new class      | none              | best design ✅


*/

class Student {
    private Integer id;
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }


    // constructors, getters, setters
}