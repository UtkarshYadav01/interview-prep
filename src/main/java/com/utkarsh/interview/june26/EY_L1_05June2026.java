package com.utkarsh.interview.june26;

import java.util.*;
import java.util.stream.*;

/**
 * EY Interview – L1 – 5th June 2026
 */
public class EY_L1_05June2026 {

    // ─────────────────────────────────────────────────────────────
    // Q1. Find numbers divisible by 7
    //     Input : 0–25
    //     Output: 0, 7, 14, 21
    // ─────────────────────────────────────────────────────────────
    static void q1_divisibleBySeven() {
        List<Integer> list = Arrays.asList(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25
        );

        List<Integer> result = list.stream()
                .filter(n -> n % 7 == 0)
                .toList();

        System.out.println("Divisible by 7: " + result); // [0, 7, 14, 21]
    }

    // ─────────────────────────────────────────────────────────────
    // Q2. Find max and min from a list
    //     Input : [10, 20, 5, 40, 15]
    //     Output: min=5, max=40
    //
    //  YOUR APPROACH (sorted + limit) works but is O(n log n).
    //  PREFERRED: min()/max() are O(n) — purpose-built for this.
    // ─────────────────────────────────────────────────────────────
    static void q2_maxAndMin() {
        List<Integer> list = Arrays.asList(10, 20, 5, 40, 15);

        // ✅ Preferred – O(n)
        int min = list.stream().min(Comparator.naturalOrder()).orElseThrow();
        int max = list.stream().max(Comparator.naturalOrder()).orElseThrow();
        System.out.println("min=" + min + ", max=" + max); // min=5, max=40

        // Your approach – works, but O(n log n) — avoid in interviews
        // list.stream().sorted().limit(1).findFirst();                   // min
        // list.stream().sorted(Comparator.reverseOrder()).limit(1)...;   // max
    }

    // ─────────────────────────────────────────────────────────────
    // Q3. Count occurrences of each number
    //     Input : [10, 20, 10, 30, 20, 40, 70, 40, 60]
    //     Output: {10=2, 20=2, 30=1, 40=2, 60=1, 70=1}
    // ─────────────────────────────────────────────────────────────
    static void q3_countOccurrences() {
        List<Integer> list = Arrays.asList(10, 20, 10, 30, 20, 40, 70, 40, 60);

        Map<Integer, Long> freq = list.stream()
                .collect(Collectors.groupingBy(
                        n -> n,
                        Collectors.counting()
                ));

        System.out.println(freq); // {20=2, 40=2, 10=2, 60=1, 70=1, 30=1}
    }

    public static void main(String[] args) {
        System.out.println("=== Q1: Divisible by 7 ===");
        q1_divisibleBySeven();

        System.out.println("\n=== Q2: Max and Min ===");
        q2_maxAndMin();

        System.out.println("\n=== Q3: Count Occurrences ===");
        q3_countOccurrences();
    }
}


/*
 ═══════════════════════════════════════════════════════════════════
  THEORY QUESTIONS
 ═══════════════════════════════════════════════════════════════════

 1. API GATEWAY
    ─────────────
    A single entry-point for all client requests in a microservices system.
    Sits in front of all services and handles cross-cutting concerns.

    Responsibilities:
      • Routing          → forwards request to the correct downstream service
      • Auth / JWT       → validates tokens before request reaches services
      • Rate limiting    → throttles abusive clients
      • SSL termination  → handles HTTPS so services don't have to
      • Request/Response transformation (header rewriting, payload shaping)
      • Logging & tracing

    Spring Cloud: Spring Cloud Gateway (reactive, replaces Zuul)
    Others: AWS API Gateway, Kong, NGINX, Apigee

    Flow:
        Client → API Gateway → [Auth Filter → Rate Limit Filter] → Service A
                                                                  → Service B


 2. SERVICE REGISTRY (Service Discovery)
    ──────────────────────────────────────
    A central directory where microservices register themselves (host + port)
    on startup and deregister on shutdown.

    Two patterns:
      Client-side discovery: client queries registry and load-balances itself.
                             e.g. Eureka + Ribbon (Netflix OSS)
      Server-side discovery: API Gateway queries registry; client is unaware.
                             e.g. AWS ALB + ECS, Consul

    Spring Cloud: Eureka Server (@EnableEurekaServer) + Eureka Client (@EnableDiscoveryClient)

    Heartbeat: services send periodic keep-alives; registry evicts stale entries.

    Flow:
        Service A starts → registers with Eureka (name, host, port)
        Service B wants to call A → asks Eureka "where is Service A?"
                                  → gets host:port → calls directly


 3. LOAD BALANCING
    ──────────────────
    Distributes incoming traffic across multiple instances of a service
    to avoid overloading any single instance.

    Client-side (Spring Cloud LoadBalancer / Ribbon):
        The consumer fetches the instance list from the registry and
        picks one using a strategy (round-robin, random, weighted).

    Server-side (AWS ALB, NGINX):
        A dedicated component in front of services distributes requests;
        clients are unaware of individual instances.

    Algorithms:
      Round Robin    → each instance in turn (default)
      Least Conn     → route to instance with fewest active connections
      Random         → pick randomly
      Weighted RR    → more traffic to beefier instances
      IP Hash        → same client always hits same instance (sticky session)

    Spring Boot config (Spring Cloud LoadBalancer):
        @LoadBalanced RestTemplate / WebClient → auto-resolved via registry


 4. CIRCUIT BREAKER
    ──────────────────
    Prevents cascading failures by stopping calls to a failing downstream
    service and returning a fallback instead.

    States:
      CLOSED   → normal; calls pass through; failures are counted
      OPEN     → failure threshold exceeded; calls SHORT-CIRCUIT immediately
                 (fallback returned, downstream not hit); wait for reset timeout
      HALF-OPEN → after timeout, a few test calls are allowed through;
                  if they succeed → back to CLOSED; if they fail → back to OPEN

    Spring Boot implementation: Resilience4j (@CircuitBreaker)

    Example:
        @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
        public String callPaymentService() {
            return restTemplate.getForObject(paymentUrl, String.class);
        }

        public String paymentFallback(Exception ex) {
            return "Payment service unavailable. Please try later.";
        }

    Config (application.yml):
        resilience4j.circuitbreaker.instances.paymentService:
            slidingWindowSize:         10
            failureRateThreshold:      50     # open if 50% of last 10 calls fail
            waitDurationInOpenState:   5s
            permittedCallsInHalfOpenState: 3

    Related patterns: Retry (@Retry), Rate Limiter (@RateLimiter), Bulkhead (@Bulkhead)


https://www.programiz.com/online-compiler/6F33uKtjH9RoI
*/