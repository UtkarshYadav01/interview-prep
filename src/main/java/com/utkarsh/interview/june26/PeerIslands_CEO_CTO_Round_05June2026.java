package com.utkarsh.interview.june26;

/**
 * PeerIslands – CEO/CTO Round – 5th June 2026
 *
 * Format  : Conversational / Leadership + Technical depth
 * Outcome : Asked 3 smart questions back → showed genuine interest
 */
public class PeerIslands_CEO_CTO_Round_05June2026 {

    /*
     ═══════════════════════════════════════════════════════════════════
      BEHAVIORAL / INTRO
     ═══════════════════════════════════════════════════════════════════

     Q. Introduce yourself.
        → 4.5 yrs Java backend; Accenture (Spring MVC/Batch) → Publicis Sapient
          (GenAI: LangChain, LangGraph, RAG, Azure OpenAI).
          Azure certified (AZ-900, DP-900, PL-900).

     Q. Explain your project and your approach.
        Talked about:
        → DevOps Guru integration: AI-assisted anomaly detection pipeline.
        → SQL Multi-Agent Flow: multiple LLM agents collaborating to
          decompose, execute, and validate SQL queries via LangGraph.

     Q. Why LangGraph over LangChain alone?
        → LangChain = linear chain of steps (good for simple pipelines).
          LangGraph = stateful, cyclic agent graphs.
          Needed it for:
            • Multi-agent orchestration (agents hand off to each other)
            • Conditional branching (route based on agent output)
            • Loops with human-in-the-loop checkpoints
            • Built-in state persistence across steps

     Q. How do you learn new technology?
        → Find one trusted resource (docs / video) → build a small isolated
          project → note gaps → fill with official docs → teach it back
          (write README / notes). Enrolled in Spring Boot 0→100 cohort
          (Coding Shuttle) for structured depth.

     Q. Latest tech you are aware of?
        Mentioned:
        → A2A Protocol (Agent-to-Agent): Google's open protocol for
          inter-agent communication in multi-agent AI systems.
          Agents advertise capabilities via "Agent Cards";
          other agents discover and delegate tasks to them.
        → Java 26 + Official AI SDK support:
          Project Babylon (code reflection), continued Valhalla progress,
          and official first-party AI/LLM SDK planned for Java platform.


     ═══════════════════════════════════════════════════════════════════
      TECHNICAL QUESTIONS
     ═══════════════════════════════════════════════════════════════════

     Q1. AUTO-CONFIGURATION IN SPRING BOOT
     ──────────────────────────────────────
     Spring Boot automatically configures beans based on:
       • What JARs are on the classpath
       • What beans are already defined by the developer
       • Properties in application.yml / application.properties

     How it works internally:
       1. @SpringBootApplication includes @EnableAutoConfiguration.
       2. Spring Boot reads META-INF/spring/org.springframework.boot
          .autoconfigure.AutoConfiguration.imports (Spring Boot 3.x)
          — a list of all AutoConfiguration classes.
       3. Each AutoConfiguration class is annotated with @Conditional*
          annotations that gate whether it activates:

            @ConditionalOnClass        – activates if a class is on classpath
            @ConditionalOnMissingBean  – activates only if you haven't defined
                                         your own bean of that type
            @ConditionalOnProperty     – activates if a property is set
            @ConditionalOnWebApplication – activates for web apps only

     Example:
       DataSourceAutoConfiguration fires if:
         • spring-jdbc is on the classpath (@ConditionalOnClass)
         • No DataSource bean is already defined (@ConditionalOnMissingBean)
         → Spring auto-creates a DataSource from spring.datasource.* props.

     Override: define your own @Bean → @ConditionalOnMissingBean skips auto-config.
     Exclude:  @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

     Debug:    Run with --debug flag → "CONDITIONS EVALUATION REPORT" in logs
               shows what fired and why.


     Q2. RAG – RETRIEVAL-AUGMENTED GENERATION
     ──────────────────────────────────────────
     Problem: LLMs have a knowledge cutoff and no access to private/domain data.
     Solution: Retrieve relevant context at query time and inject it into the prompt.

     Pipeline:
       INDEXING (offline):
         Documents → Chunking → Embedding Model → Vector Store (ChromaDB / FAISS / Azure AI Search)

       RETRIEVAL (online, per query):
         User Query → Embed query → Vector similarity search → Top-K chunks retrieved

       GENERATION:
         [System prompt + Retrieved chunks + User query] → LLM → Answer

     Key design decisions:
       • Chunk size & overlap  – too small = loses context; too large = noise
       • Embedding model       – must match what was used at index time
       • Top-K                 – how many chunks to pass; affects context window usage
       • Metadata filtering    – filter by date/source before vector search

     Used in project: LangChain/LangGraph + ChromaDB + FAISS + Azure AI Search
                      + Azure OpenAI (embeddings + completion)


     Q3. QUERY RERANKING
     ─────────────────────
     Problem: Vector similarity (cosine / dot product) retrieves semantically
     similar chunks but doesn't always rank them by RELEVANCE to the query.
     Top result by embedding similarity ≠ most useful for answering the question.

     Solution: After retrieval, apply a RERANKER MODEL to re-score and reorder
     the retrieved chunks by their actual relevance to the query.

     Two-stage pipeline:
       Stage 1 – Retrieval (fast, approximate):
         Embed query → ANN search → Top-50 candidates (cheap vector search)

       Stage 2 – Reranking (slower, precise):
         Reranker model scores each (query, chunk) pair → re-sorts → Top-5 sent to LLM

     Types of rerankers:
       Cross-encoder  → takes (query + chunk) together as input; more accurate
                        but slower (can't pre-compute). e.g. Cohere Rerank, BGE Reranker
       Bi-encoder     → separate embeddings compared; faster but less accurate
                        (standard vector search is essentially this)

     Why it matters:
       Without reranking, the LLM might receive chunks that are topically related
       but don't actually answer the question → hallucination or irrelevant answers.
       Reranking improves answer quality significantly with minimal added latency
       (only top-K from stage 1 are reranked, not the whole corpus).

     Used in project: Cohere / Azure AI Search semantic reranker.


     ═══════════════════════════════════════════════════════════════════
      QUESTIONS I ASKED
     ═══════════════════════════════════════════════════════════════════

     1. "This is a polyglot role — what does the day-to-day work look like
        and what is the primary tech stack the team uses?"
        → Shows interest in actual work, not just the title.

     2. "I saw PeerIslands has PeerAI — is there a separate product team
        for it? And would there be opportunity to contribute to it alongside
        client work?"
        → Shows initiative + GenAI background relevance.

     3. "I understand there's a merger happening — how does that affect
        the engineering teams?"
        → CEO replied: it's an expanding merger; teams are growing, not shrinking.
        → Smart question: shows business awareness; answer de-risked the opportunity.

    */
}