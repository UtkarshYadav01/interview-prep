# 📚 Java Backend Interview Prep

> Structured workspace for Java, DSA, backend, databases, DevOps, and interview-specific preparation topics.

---

## 🗂️ Project Structure

```
src/main/java/com/utkarsh/interviewprep/
│
├── java/
│   ├── oops/                    # Encapsulation, Inheritance, Polymorphism, Abstraction
│   ├── collections/             # List, Set, Map, Concurrent Collections
│   ├── java8/                   # Streams, Lambda, Optional, Functional Interfaces
│   ├── java11/                  # String methods, File API, HTTP Client
│   ├── java17/                  # Sealed Classes, Records, Pattern Matching, Switch Expressions
│   ├── java21/                  # Virtual Threads, Structured Concurrency, Sequenced Collections
│   ├── multithreading/          # Thread Lifecycle, Synchronization, Executor Framework, Fork/Join
│   ├── jvm/                     # Memory Model, Garbage Collection, Class Loading
│   └── designpatterns/          # Creational, Structural, Behavioral
│
├── dsa/
│   ├── arrays/                  # Basics, Sorting, Searching, Problems
│   ├── strings/                 # Reverse, Palindrome, Anagram, Pattern Matching
│   ├── linkedlist/              # Singly, Doubly, Problems
│   ├── stackqueue/              # Stack, Queue, Monotonic
│   ├── trees/                   # BST, Binary Tree, Traversals
│   ├── graphs/                  # BFS, DFS, Shortest Path, Topological Sort
│   ├── recursion/               # Basics, Backtracking
│   └── dynamicprogramming/      # Knapsack, Subsequence, Grid DP
│
├── backend/
│   ├── spring/                  # IoC/DI, Bean Lifecycle, REST, Security, Data JPA
│   ├── microservices/           # Patterns, Communication, Design
│   ├── messaging/               # Kafka, RabbitMQ
│   ├── caching/                 # Redis, Cache Patterns, Invalidation
│   ├── systemdesign/            # URL Shortener, Rate Limiter, Chat, Payment, Ride Sharing
│   ├── apidesign/               # REST Principles, Pagination, Versioning, Idempotency
│   ├── resilience/              # Retry, Timeout, Circuit Breaker, Bulkhead, Fallback
│   └── observability/           # Logging, Metrics, Tracing, Monitoring
│
├── databases/
│   ├── sql/                     # Joins, Indexing, Normalization, Transactions
│   ├── mongodb/                 # Schema Design, Aggregation, Indexing
│   └── redis/                   # Caching, Pub/Sub, Data Structures
│
├── devops/                      # Docker, Kubernetes, CI/CD, Cloud Basics
│
└── interviewsets/               # Amazon, Google, System Design, HR & Behavioral
```

---

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-25-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)
![JUnit5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-black?logo=apachekafka)
![Redis](https://img.shields.io/badge/Redis-DC382D?logo=redis&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)

---

## 🚀 Getting Started

```bash
# Clone the repo
git clone https://github.com/UtkarshYadav01/interview-prep.git

cd interview-prep

# Build with Maven
mvn clean install

# Run tests
mvn test
```

> Java 25+ and Maven 3.8+ recommended.

---

## 📈 Progress Log

Track of what's been built so far, in order.

| # | Date | What | File / Notes |
|---|---|---|---|
| 1 | 20-04-2026 | Project setup & pushed to GitHub | Initial commit with `pom.xml` and `.gitignore` |
| 2 | 21-04-2026 | Full folder structure created | All modules scaffolded under `com.utkarsh.interviewprep` |
| 3 | 21-04-2026 | README added | Project overview, structure, and progress log |
| 4 | 21-04-2026 | Java 8 Streams practice | [`StreamPracticeApril2026.java`](src/main/java/com/utkarsh/interviewprep/java/java8/streamAPI/practice/StreamPracticeApril2026.java) |
| 5 | 13-05-2026 | JUnit 5 added to `pom.xml` | `junit-jupiter 5.12.2` added as test dependency |
| 6 | 13-05-2026 | Stream tests written & passing ✅ | [`StreamPracticeApril2026Test.java`](src/test/java/com/utkarsh/interviewprep/java/java8/streamAPI/practice/StreamPracticeApril2026Test.java) — all tests pass |
| 7 | 02-05-2026 | Array basics practice added | [`Arrays1.java`](src/main/java/com/utkarsh/interviewprep/dsa/arrays/Arrays1.java) — average, linear search, max element, zero/one count, intersections, alternate extremes |
| 8 | 30-04-2026 | SPG interview practice set added | [`SPG29thApr2026.java`](src/main/java/com/utkarsh/interview/apr26/SPG29thApr2026.java) — streams, sliding window, anagrams, HashMap internals, Spring bean scopes |
| 9 | 05-05-2026 | Arrays intermediate problems added | [`Arrays2.java`](src/main/java/com/utkarsh/interviewprep/dsa/arrays/Arrays2.java) — reverse array, mode, frequency analysis, shifting, union |
| 10 | 06-05-2026 | VLink recursion interview practice added | [`VLink06thMay2026.java`](src/main/java/com/utkarsh/interview/may26/VLink06thMay2026.java) — recursive factorial and `n--` vs `--n` behavior |
| 11 | 05-05-2026 | Advanced array problems added | [`Arrays3.java`](src/main/java/com/utkarsh/interviewprep/dsa/arrays/Arrays3.java) — segregate 0s/1s, unique element, missing number, XOR approach |
| 12 | 14-05-2026 | VLink SQL interview practice added | [`VLink14thMay2026.java`](src/main/java/com/utkarsh/interview/may26/VLink14thMay2026.java) — SQL joins, LEFT JOIN explanation, employee-department query |
| 13 | 14-05-2026 | AWS cloud basics notes added | [`Aws notes.md`](src/main/java/com/utkarsh/interviewprep/devops/cloudbasics/Aws%20notes.md) — cloud and AWS interview preparation notes |
| 14 | 16-05-2026 | String problems practice added | [`StringProblems.java`](src/main/java/com/utkarsh/interviewprep/dsa/strings/StringProblems.java) — sum of all substrings of a number, beauty sum placeholder problem |
| 15 | 16-05-2026 | String problems JUnit tests added & passing ✅ | [`StringProblemsTest.java`](src/test/java/com/utkarsh/interviewprep/dsa/strings/StringProblemsTest.java) — substring sum and beauty sum test cases |
---

## 🎯 Who Is This For?

- Java backend developers with **4–7 years of experience** preparing for senior-level interviews
- Engineers who prefer **learning by writing code** over reading theory
- Anyone who wants a **structured revision reference** they can come back to

---

## 📌 Notes

- Each module contains focused, runnable Java examples
- Tests are written alongside implementation for self-verification
- Designed as a **personal reference** — not a course or tutorial
- Continuously updated as new topics are covered

---

## 📄 License