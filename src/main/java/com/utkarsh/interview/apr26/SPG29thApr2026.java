package com.utkarsh.interview.apr26;

import java.util.*;
import java.util.stream.*;

/**
 * SPG Interview – 29th April 2026
 * Contains solutions + theory notes for all questions asked.
 */
public class SPG29thApr2026 {

    // ─────────────────────────────────────────────────────────────
    // MODEL
    // ─────────────────────────────────────────────────────────────
    record Employee(String name, double salary) {}

    // ─────────────────────────────────────────────────────────────
    // Q1. Find the employee with the 2nd largest (distinct) salary
    // ─────────────────────────────────────────────────────────────
    static void q1_secondLargestSalary() {
        List<Employee> employees = List.of(
                new Employee("Alice",  90_000),
                new Employee("Bob",   120_000),
                new Employee("Carol", 120_000),   // duplicate of highest
                new Employee("Dave",   85_000)
        );

        // distinct() on salaries so duplicates don't count as separate ranks
        Optional<Double> secondSalary = employees.stream()
                .map(Employee::salary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)                          // skip the highest
                .findFirst();

        // If you also want the employee object(s) for that salary:
        secondSalary.ifPresent(sal -> {
            List<Employee> result = employees.stream()
                    .filter(e -> e.salary() == sal)
                    .toList();
            System.out.println("2nd highest salary: " + sal);
            System.out.println("Employee(s): " + result);
        });
    }

    // ─────────────────────────────────────────────────────────────
    // Q2. Group words by their starting letter
    //     Input : ["apple","avocado","banana","blueberry","cherry","citrus"]
    //     Output: {a=[apple, avocado], b=[banana, blueberry], c=[cherry, citrus]}
    // ─────────────────────────────────────────────────────────────
    static void q2_groupByStartingLetter() {
        List<String> words = List.of("apple", "avocado", "banana", "blueberry", "cherry", "citrus");

        Map<Character, List<String>> grouped = words.stream()
                .collect(Collectors.groupingBy(w -> w.charAt(0)));

        System.out.println(grouped);
        // {a=[apple, avocado], b=[banana, blueberry], c=[cherry, citrus]}
    }

    // ─────────────────────────────────────────────────────────────
    // Q3. What is the output of this stream pipeline?
    //
    //  OUTPUT (short-circuit – stops after FIRST even number found):
    //      filter: 1      ← checked, odd → rejected
    //      filter: 2      ← checked, even → passes
    //      map: 2         ← mapped to 20
    //      result: 20     ← findFirst() returns immediately, 3/4/5 never processed
    //
    //  KEY CONCEPT: findFirst() is a short-circuit terminal op.
    //  Streams are LAZY – elements are pulled one at a time, not all at once.
    // ─────────────────────────────────────────────────────────────
    static void q3_streamLazyOutputExplained() {
        List<Integer> nums = List.of(1, 2, 3, 4, 5);

        Optional<Integer> result = nums.stream()
                .filter(n -> { System.out.println("filter: " + n); return n % 2 == 0; })
                .map(n    -> { System.out.println("map: "    + n); return n * 10; })
                .findFirst();

        System.out.println("result: " + result.orElse(-1));
    }

    // ─────────────────────────────────────────────────────────────
    // Q4. Longest substring without repeating characters (Sliding Window)
    //     "abcabcbb" → 3 ("abc")
    //     "pwwkew"   → 3 ("wke")
    //     "bbbbb"    → 1 ("b")
    // ─────────────────────────────────────────────────────────────
    static int q4_longestSubstringNoRepeat(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // If char was seen inside the current window, shrink window from left
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;
            }
            lastSeen.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // ─────────────────────────────────────────────────────────────
    // Q5. Group Anagrams
    //     ["eat","tea","tan","ate","nat","bat"]
    //     → [["eat","tea","ate"], ["tan","nat"], ["bat"]]
    // ─────────────────────────────────────────────────────────────
    static List<List<String>> q5_groupAnagrams(List<String> words) {
        // Anagrams share the same sorted-character key
        return new ArrayList<>(
                words.stream()
                        .collect(Collectors.groupingBy(w -> {
                            char[] chars = w.toCharArray();
                            Arrays.sort(chars);
                            return new String(chars);
                        }))
                        .values()
        );
    }

    // ─────────────────────────────────────────────────────────────
    // Q6. try-return-finally output
    //
    //  OUTPUT:
    //      finally: x = 99
    //      returned: 10           ← NOT 99 !
    //
    //  WHY: The return value (10) is saved on the stack before the
    //  finally block runs. finally CAN override it only if it also
    //  has an explicit `return` statement. Here it just mutates the
    //  local variable x, which has no effect on the already-saved value.
    // ─────────────────────────────────────────────────────────────
    static int q6_tryFinallyReturn() {
        int x = 10;
        try {
            return x;       // 10 is pushed onto the return-value slot
        } finally {
            x = 99;         // local x changes, but the slot already holds 10
            System.out.println("finally: x = " + x);   // prints 99
        }
        // prints "returned: 10" in caller
    }

    // ─────────────────────────────────────────────────────────────
    // Q7. Valid Parentheses (Stack approach)
    //     "([]){}" → true
    //     "([)]"   → false
    //     "(("     → false
    // ─────────────────────────────────────────────────────────────
    static boolean q7_isValidParentheses(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');

        for (char c : s.toCharArray()) {
            if (pairs.containsValue(c)) {       // opening bracket
                stack.push(c);
            } else if (pairs.containsKey(c)) {  // closing bracket
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();  // unmatched openers left = false
    }

    // ─────────────────────────────────────────────────────────────
    // MAIN – runs all coding questions
    // ─────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=== Q1: 2nd Largest Salary ===");
        q1_secondLargestSalary();

        System.out.println("\n=== Q2: Group by Starting Letter ===");
        q2_groupByStartingLetter();

        System.out.println("\n=== Q3: Stream Lazy Evaluation Output ===");
        q3_streamLazyOutputExplained();

        System.out.println("\n=== Q4: Longest Substring Without Repeating Characters ===");
        System.out.println(q4_longestSubstringNoRepeat("abcabcbb")); // 3
        System.out.println(q4_longestSubstringNoRepeat("pwwkew"));   // 3
        System.out.println(q4_longestSubstringNoRepeat("bbbbb"));    // 1

        System.out.println("\n=== Q5: Group Anagrams ===");
        List<String> input = Arrays.asList("eat", "tea", "tan", "ate", "nat", "bat");
        System.out.println(q5_groupAnagrams(input));

        System.out.println("\n=== Q6: try-finally return value ===");
        System.out.println("returned: " + q6_tryFinallyReturn());

        System.out.println("\n=== Q7: Valid Parentheses ===");
        System.out.println(q7_isValidParentheses("([]){}")); // true
        System.out.println(q7_isValidParentheses("([)]"));   // false
        System.out.println(q7_isValidParentheses("(("));     // false
    }
}


/*
 ═══════════════════════════════════════════════════════════════════
  THEORY ANSWERS
 ═══════════════════════════════════════════════════════════════════

 1. INTERMEDIATE vs TERMINAL STREAM OPERATIONS
    ─────────────────────────────────────────────
    Intermediate : return a new Stream; LAZY – nothing executes until
                   a terminal op is called.
                   Examples: filter(), map(), sorted(), distinct(), peek()

    Terminal     : trigger the pipeline and produce a result or side-effect.
                   Examples: collect(), findFirst(), count(), forEach(), reduce()

    Short-circuit terminal ops stop early (don't consume all elements):
    findFirst(), findAny(), anyMatch(), allMatch(), noneMatch(), limit()


 2. STREAM SHORT-CIRCUIT
    ─────────────────────
    A short-circuit operation allows the pipeline to terminate before
    processing every element.
    - findFirst() / findAny() stop as soon as one match is found.
    - anyMatch()  stops on the first true result.
    - allMatch()  stops on the first false result.
    - limit(n)    stops after n elements.
    This is what made Q3 print only filter:1, filter:2, map:2 instead
    of all five elements.


 3. WHY IS STRING IMMUTABLE IN JAVA?
    ──────────────────────────────────
    a) Security – class names, DB URLs, network connections use Strings;
       mutation could be exploited.
    b) Thread safety – immutable objects are inherently safe across threads
       with no synchronization needed.
    c) String Pool / caching – the JVM can intern/cache String literals.
       If strings were mutable, sharing would be dangerous.
    d) hashCode caching – String caches its hash; safe only because
       content never changes.
    Implementation: char[] value is private + final; no setters exposed.


 4. HOW TO CREATE AN IMMUTABLE CLASS
    ────────────────────────────────────
    Rules:
    1. Declare class as `final` (prevents subclassing).
    2. Make all fields `private final`.
    3. No setters.
    4. Initialize all fields via constructor.
    5. For mutable field types (List, Date, arrays): return DEFENSIVE COPIES
       from getters, and deep-copy in constructor.

    Example:
        public final class Money {
            private final int amount;
            private final List<String> tags;

            public Money(int amount, List<String> tags) {
                this.amount = amount;
                this.tags = List.copyOf(tags); // defensive copy
            }
            public int getAmount() { return amount; }
            public List<String> getTags() { return tags; } // unmodifiable
        }


 5. CopyOnWriteArrayList
    ───────────────────────
    - Thread-safe variant of ArrayList (java.util.concurrent).
    - On every WRITE (add/set/remove), the underlying array is COPIED,
      the modification is applied to the copy, and the reference is updated.
    - READs are lock-free because they always see a consistent snapshot.
    - Cost: writes are expensive (O(n) copy); best for read-heavy,
      rarely-written collections.
    - Iterators are FAIL-SAFE (see below).


 6. FAIL-FAST vs FAIL-SAFE ITERATORS
    ─────────────────────────────────
    Fail-fast  : Throws ConcurrentModificationException if the collection
                 is structurally modified DURING iteration (checks modCount).
                 Examples: ArrayList, HashMap, HashSet iterators.
                 ⚠ Not guaranteed in multi-threaded use; best-effort.

    Fail-safe  : Works on a COPY of the collection; modifications to the
                 original don't affect the running iteration; no exception.
                 Examples: CopyOnWriteArrayList, ConcurrentHashMap iterators.
                 Trade-off: may not reflect latest state of the collection.


 7. HOW TO MODIFY A COLLECTION WITHOUT ConcurrentModificationException
    ────────────────────────────────────────────────────────────────────
    Option A – Use Iterator.remove():
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().equals("x")) it.remove(); // safe
        }

    Option B – removeIf() (Java 8+):
        list.removeIf(s -> s.equals("x"));

    Option C – Use a concurrent collection:
        CopyOnWriteArrayList or ConcurrentHashMap.

    Option D – Collect results to a new list, then swap:
        List<String> toRemove = list.stream().filter(...).toList();
        list.removeAll(toRemove);


 8. INTERNAL WORKING OF HASHMAP
    ─────────────────────────────
    - Backed by an array of Node<K,V>[] (buckets), default capacity 16.
    - On put(key, value):
        1. Compute hash: (key == null) ? 0 : key.hashCode() ^ (h >>> 16)
        2. index = hash & (capacity - 1)
        3. If bucket empty → insert node.
        4. If collision (same index, different key) → chain as LinkedList.
        5. If chain length > 8 AND capacity >= 64 → convert to TreeMap (O(log n)).
    - Load factor default = 0.75; when size > capacity * 0.75 → RESIZE (double).
    - In Java 8+, treeification avoids worst-case O(n) lookup on hash collisions.
    - get(key): same hash+index computation → traverse chain/tree to find key by equals().


 9. SPRING BOOT BEAN SCOPES
    ──────────────────────────
    @Scope("singleton")  – default; one instance per ApplicationContext.
    @Scope("prototype")  – new instance every time it is injected/requested.
    @Scope("request")    – one per HTTP request  (web apps).
    @Scope("session")    – one per HTTP session  (web apps).
    @Scope("application")– one per ServletContext (web apps).
    @Scope("websocket")  – one per WebSocket session.


 10. RESOLVING AMBIGUOUS BEANS (multiple beans of same type)
     ──────────────────────────────────────────────────────────
     a) @Primary   – marks preferred bean; used when no further hint given.
     b) @Qualifier("beanName") – at injection point, explicitly names the bean.
     c) @Bean method name – Spring falls back to method name as bean name.
     d) applicationContext.xml (Spring Core) – byType (default) or byName
        wiring via autowire="byName" attribute; bean id/name acts as selector.
     e) Programmatic: ApplicationContext.getBean("beanName", Type.class)
     f) @ConditionalOnMissingBean – auto-config pattern to register a bean
        only when no other bean of that type is present.

        https://www.programiz.com/online-compiler/0AFVBtpSnsNla

        https://www.programiz.com/online-compiler/1wjPQydlaTLjU

*/