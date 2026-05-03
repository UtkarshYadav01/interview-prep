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

| # | What | File / Notes |
|---|------|------|
| 1 | Project setup & pushed to GitHub | Initial commit with `pom.xml` and `.gitignore` |
| 2 | Full folder structure created | All modules scaffolded under `com.utkarsh.interviewprep` |
| 3 | README added | Project overview, structure, and progress log |
| 4 | Java 8 Streams practice | [`StreamPracticeApril2026.java`](src/main/java/com/utkarsh/interviewprep/java/java8/streamAPI/practice/StreamPracticeApril2026.java) |
| 5 | JUnit 5 added to `pom.xml` | `junit-jupiter 5.12.2` added as test dependency |
| 6 | Stream tests written & passing ✅ | [`StreamPracticeApril2026Test.java`](src/test/java/com/utkarsh/interviewprep/java/java8/streamAPI/practice/StreamPracticeApril2026Test.java) — all tests pass |

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

This project is for personal learning and reference purposes.



28-04-2026 
[Arrays1](src/main/java/com/utkarsh/interviewprep/dsa/arrays/Arrays1.java)
```java
//Q1. Find the average of array elements

//Q2. Multiply each element of array by 10

//Q3. Search for an element in an Array {Linear Search}

//Q4. Find the maximum element in an array

//Q5. Return Sum of +ve and -ve numbers

//Q6. Count the number of Zeroes and Ones

//Q7. Find first Unsorted Element in Array

//HW
// Q8.Swap Alternate Elements in an Array
// Q9.Print Array Intersection element
// Q10.Print Alternate Extreme elements of an Array
```
