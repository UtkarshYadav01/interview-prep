package com.utkarsh.interview.may26;

public class Deloitte28thMay2026 {

    /*
     * ══════════════════════════════════════════════════════════════
     * SYNCHRONIZED HASHMAP vs CONCURRENTHASHMAP
     * ══════════════════════════════════════════════════════════════
     * SynchronizedHashMap (Collections.synchronizedMap)
     *   - Wraps entire map with a single mutex lock
     *   - One thread at a time for ALL operations (get + put)
     *   - Risk of deadlock if iterator not manually synchronized
     *
     * ConcurrentHashMap
     *   - Segment-level locking (Java 7) → bucket-level CAS (Java 8+)
     *   - Multiple threads can read/write DIFFERENT buckets simultaneously
     *   - No lock on reads at all (volatile reads)
     *   - Null keys/values NOT allowed
     *   - Prefer this for high-concurrency scenarios
     *
     * TL;DR: ConcurrentHashMap = fine-grained lock → better throughput
     * ══════════════════════════════════════════════════════════════
     *
     *
     * MICROSERVICE DESIGN PATTERNS
     * ══════════════════════════════════════════════════════════════
     * SAGA
     *   - Manages distributed transactions across services
     *   - Choreography: each service publishes events, others react
     *   - Orchestration: central saga orchestrator sends commands
     *   - Compensating transactions for rollback (no 2PC)
     *
     * Circuit Breaker (Resilience4j)
     *   - CLOSED → requests pass through normally
     *   - OPEN   → fast-fail, no calls to failing service
     *   - HALF-OPEN → trial requests to check recovery
     *
     * CQRS (Command Query Responsibility Segregation)
     *   - Separate models for writes (Command) and reads (Query)
     *   - Often paired with Event Sourcing
     *   - Read side can be denormalized/optimized independently
     *
     *
     * WHAT IS CORS
     * ══════════════════════════════════════════════════════════════
     * Cross-Origin Resource Sharing — browser security mechanism
     * Blocks JS from calling a different origin (domain/port/protocol)
     *   unless the server explicitly allows it via headers:
     *   Access-Control-Allow-Origin: https://frontend.com
     *   Access-Control-Allow-Methods: GET, POST
     * In Spring Boot: @CrossOrigin or global WebMvcConfigurer
     * Preflight = browser sends OPTIONS request first for non-simple requests
     *
     *
     * CALLABLE vs RUNNABLE
     * ══════════════════════════════════════════════════════════════
     * Runnable
     *   - void run()  → no return value, cannot throw checked exception
     *   - Used with Thread / ExecutorService.execute()
     *
     * Callable<V>
     *   - V call() throws Exception → returns value, can throw checked
     *   - Used with ExecutorService.submit() → returns Future<V>
     *
     * Rule: need a result or checked exception → Callable
     *
     *
     * COMPLETABLEFUTURE
     * ══════════════════════════════════════════════════════════════
     * Non-blocking async composition (Java 8+), extends Future<T>
     *
     * Key methods:
     *   supplyAsync(() -> fetchUser())          // async task, returns value
     *   thenApply(user -> transform(user))      // sync transform on result
     *   thenCompose(user -> fetchOrders(user))  // flat-map (async chain)
     *   thenCombine(cf2, (a, b) -> merge(a,b)) // combine two futures
     *   exceptionally(ex -> fallback)           // error handling
     *   allOf(cf1, cf2, cf3)                    // wait for all
     *   anyOf(cf1, cf2, cf3)                    // first to complete wins
     *
     * vs Future: Future.get() blocks; CompletableFuture chains callbacks
     *
     *
     * SAGA IN ECOM MICROSERVICES (Order Example)
     * ══════════════════════════════════════════════════════════════
     * Services: OrderService → InventoryService → PaymentService → ShipmentService
     *
     * Happy path (Choreography via Kafka):
     *   OrderCreated → reserve inventory → InventoryReserved
     *   → charge payment → PaymentProcessed → ship order
     *
     * Failure path (compensating transactions):
     *   PaymentFailed → release inventory (compensate) → cancel order
     *
     * Key insight: no distributed lock/2PC; each step is idempotent
     *
     *
     * SOLID PRINCIPLES
     * ══════════════════════════════════════════════════════════════
     * S - Single Responsibility : one class, one reason to change
     * O - Open/Closed           : open for extension, closed for modification
     * L - Liskov Substitution   : subclass must be usable wherever parent is
     * I - Interface Segregation : many small interfaces > one fat interface
     * D - Dependency Inversion  : depend on abstractions, not concretions
     *
     *
     * VECTOR DB / RAG / AZURE
     * ══════════════════════════════════════════════════════════════
     * Vector DB (e.g. ChromaDB, FAISS, Pinecone, Azure AI Search)
     *   - Stores embeddings (high-dimensional float vectors)
     *   - Nearest-neighbour search (cosine similarity / dot product)
     *
     * RAG (Retrieval Augmented Generation)
     *   - User query → embed → search vector DB → retrieve top-k chunks
     *   - Inject chunks into LLM prompt as context → grounded answer
     *   - Reduces hallucination; keeps knowledge up-to-date without retraining
     *
     * Azure (AZ-900 / DP-900 / PL-900 certified)
     *   - AZ-900: core cloud concepts, Azure services overview
     *   - DP-900: data fundamentals (SQL, NoSQL, analytics)
     *   - PL-900: Power Platform (Power Apps, Power Automate)
     *   - Used Azure OpenAI + Azure AI Search in GenAI project at PS
     *
     *
     * DOCKERFILE vs DOCKER COMPOSE FILE
     * ══════════════════════════════════════════════════════════════
     * Dockerfile
     *   - Blueprint to BUILD a single image
     *   - FROM → RUN → COPY → EXPOSE → CMD
     *   - Output: Docker image
     *
     * docker-compose.yml
     *   - Defines and runs MULTI-CONTAINER applications
     *   - References images (built or pulled)
     *   - Configures networks, volumes, env vars, port mappings
     *   - Output: running container cluster (docker compose up)
     *
     * Analogy: Dockerfile = recipe for one dish
     *          docker-compose = full restaurant menu + kitchen setup
     *
     *
     * GITHUB ACTIONS & KUBERNETES SECRETS
     * ══════════════════════════════════════════════════════════════
     * GitHub Actions
     *   - CI/CD via .github/workflows/*.yml
     *   - Triggers: push, pull_request, schedule
     *   - Secrets stored in repo Settings → Secrets → accessed as ${{ secrets.MY_KEY }}
     *   - Never hard-code credentials; use secrets for DB_PASSWORD, API_KEY, etc.
     *
     * Kubernetes Secrets (what to keep there)
     *   - DB credentials, JWT signing keys, API keys, TLS certificates
     *   - Created as: kubectl create secret generic db-secret --from-literal=password=xyz
     *   - Injected as env vars or mounted volumes in Pod spec
     *   - Best practice: use external secret managers (AWS Secrets Manager,
     *     Azure Key Vault, HashiCorp Vault) + External Secrets Operator
     *   - Never commit secrets to Git; never bake into Docker image
     *
     *
     * PLANTUML
     * ══════════════════════════════════════════════════════════════
     * Text-based diagram scripting tool
     * Supports: sequence, class, component, activity, ER, C4 diagrams
     *
     * Example (sequence):
     *   @startuml
     *   Client -> OrderService: POST /orders
     *   OrderService -> Kafka: publish OrderCreated
     *   Kafka -> InventoryService: consume OrderCreated
     *   @enduml
     *
     * Integrates with: IntelliJ plugin, Confluence, GitHub README rendering
     * ══════════════════════════════════════════════════════════════
     */
}