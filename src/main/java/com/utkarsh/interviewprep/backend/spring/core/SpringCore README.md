# Spring Core Notes

---

## 1 — Project Setup

Create a Maven project and add the Spring Context dependency from [mvnrepository.com](https://mvnrepository.com):

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>7.0.7</version>
    </dependency>
</dependencies>
```

---

## 2 — IoC Container

The **IoC (Inversion of Control) Container** is the core of Spring. Instead of your code creating objects, Spring creates and manages them. Objects managed by the container are called **beans**.

### How it works — Java Reflection API

Spring uses the Java Reflection API to inspect and instantiate classes at runtime:

```java
Student s1 = new Student();

// The Class object holds metadata about any class
Class<Student> c1 = Student.class;
```

The `Class` object gives Spring access to: class name, fields, constructors, methods, and annotations.

---

## 3 — Setting Up the IoC Container

### 3.1 — Mark Classes as Beans

Add `@Component` to any class you want Spring to manage:

```java
// OrderService.java
@Component
public class OrderService {
    private PaymentService paymentService;

    public void placeOrder() {
        paymentService.pay();
        System.out.println("Order Placed");
    }
}
```

```java
// PaymentService.java
@Component
public class PaymentService {
    public void pay() {
        System.out.println("Payment Successful");
    }
}
```

### 3.2 — Create the Config Class

Create `AppConfig.java` — tells Spring which package to scan for `@Component` classes:

```java
@Configuration
@ComponentScan("com.utkarsh")
public class AppConfig {
    // empty — annotations do all the work
}
```

### 3.3 — Start the Container

Two approaches — annotation-based (preferred) or XML-based.

**Annotation-based:**

```java
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService order = context.getBean(OrderService.class);
        order.placeOrder();
    }
}
```

`new AnnotationConfigApplicationContext(AppConfig.class)` — starts the IoC container using annotation-based config with rules defined in `AppConfig`.

---

## 4 — Dependency Injection

### Types of DI

**1. Constructor Injection** ✅ recommended

```java
private final PaymentService paymentService;

@Autowired
public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
}
```

In modern Spring, `@Autowired` is optional when there's only one constructor:

```java
private final PaymentService paymentService;

public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
}
```

**2. Setter Injection**

```java
private PaymentService paymentService;

@Autowired
public void setPaymentService(PaymentService paymentService) {
    this.paymentService = paymentService;
}
```

**3. Field Injection** ❌ avoid

```java
@Autowired
private PaymentService paymentService;
```

### Why Constructor Injection is Recommended

| Reason | Explanation |
|---|---|
| Wired at creation | Dependencies are set when the object is created — no partially-initialised state |
| Supports `final` | Allows immutable fields |
| Easy to test | Dependencies can be passed in directly without Spring in unit tests |

---

## 5 — How Spring Creates Beans Internally

```
Step 1  Start the IoC container
Step 2  Read AppConfig.java
Step 3  Process @ComponentScan
Step 4  Find all @Component classes
Step 5  Create Bean Definitions
Step 6  Create objects (resolve dependencies)
Step 7  Application uses beans
```

**Bean Definition (Step 5):**

```
┌─────────────────────────────────┐
│  Bean name  : paymentService    │
│  Bean class : PaymentService    │
│  Scope      : singleton         │
│  Dependency : none              │
└─────────────────────────────────┘
```

**Object creation order (Step 6):**

```
PaymentService payment = new PaymentService();   // no deps, created first

┌──────────────────────────────────────────────┐
│  PaymentService  ◄────  OrderService         │
└──────────────────────────────────────────────┘

OrderService order = new OrderService(payment);  // dep injected via constructor
```

---

## 6 — Interfaces for Loose Coupling

Define a `PaymentService` interface and multiple implementations:

```java
public interface PaymentService {
    void pay();
}
```

```java
@Component
public class CardPayment implements PaymentService {
    @Override
    public void pay() { System.out.println("Paying via card"); }
}
```

```java
@Component
public class UpiPayment implements PaymentService {
    @Override
    public void pay() { System.out.println("Paying by UPI"); }
}
```

**Problem:** Spring now finds two beans of type `PaymentService` and throws:

```
NoUniqueBeanDefinitionException: expected single matching bean but found 2: cardPayment, upiPayment
```

**Fix 1 — `@Primary`:** marks one bean as the default when multiple candidates exist:

```java
@Component
@Primary
public class UpiPayment implements PaymentService { ... }
```

**Fix 2 — `@Qualifier`:** explicitly selects a specific bean by name:

```java
// On the implementations
@Component
@Qualifier("cardPayment")
public class CardPayment implements PaymentService { ... }

// At the injection point
public OrderService(@Qualifier("cardPayment") PaymentService paymentService) {
    this.paymentService = paymentService;
}
```

> Bean name defaults to the class name in camelCase. You can override it by passing a name into `@Qualifier("customName")` on the class.

---

## 7 — When `@Component` Fails

`@Component` requires Spring to instantiate the class automatically. It fails in two cases:

### Case 1 — Constructor needs primitive/String values

```java
@Component
public class User {
    private String name;
    private int age;

    public User(String name, int age) { // Spring can't autowire String or int
        this.name = name;
        this.age = age;
    }
}
```

Spring throws:

```
NoSuchBeanDefinitionException: No qualifying bean of type 'java.lang.String' available
```

### Case 2 — Class is from an external JAR

You can't add `@Component` to compiled `.class` files in a third-party library.

Example: `CartService` in a separate `SpringCoreDemo2` project installed via `mvn install`:

```xml
<dependency>
    <groupId>com.utkarsh2</groupId>
    <artifactId>SpringCoreDemo2</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

```java
// In the external JAR — can't touch this source
public class CartService {
    public void addToCart() { System.out.println("Added To Cart"); }
}
```

Spring cannot scan and manage this class because it has no `@Component`.

---

## 8 — `@Bean` Annotation

`@Bean` solves both `@Component` failure cases. You manually create the object and Spring manages it.

Add `@Bean` methods to your `AppConfig` class:

```java
@Configuration
@ComponentScan("com.utkarsh")
public class AppConfig {

    // Case 1 fix — supply the values yourself
    @Bean
    public User createUser() {
        return new User("Utkarsh", 26);
    }

    // Case 2 fix — instantiate the external class yourself
    @Bean
    public CartService getCartService() {
        return new CartService();
    }
}
```

Spring reads these methods during startup: when it finds `@Bean`, it calls the method, receives the object, and stores it in the IoC container.

```java
// Main.java
User user = context.getBean(User.class);
System.out.println(user.getName()); // Utkarsh

CartService cartService = context.getBean(CartService.class);
cartService.addToCart();            // Added To Cart
```

### `@Component` vs `@Bean`

| | `@Component` | `@Bean` |
|---|---|---|
| Applied to | Class | Method in a `@Configuration` class |
| Use when | You own the source code | External JAR or need to pass constructor args |
| Spring creates the object | Yes | No — you create it, Spring manages it |
| Priority if both exist | Lower | **Higher** — `@Bean` wins |

---

## 9 — DI in `@Bean` Configurations

### Constructor Injection via `@Bean`

```java
@Bean
public PaymentService createCardPayment() {
    return new CardPayment();
}

@Bean
public OrderService createOrderService(PaymentService paymentService) {
    return new OrderService(paymentService);  // Spring injects the PaymentService bean
}
// @Autowired not needed — constructor injection is implicit in @Bean methods
```

### Setter Injection via `@Bean`

**Without `@Autowired` — wire manually:**

```java
@Bean
public OrderService createOrderService() {
    PaymentService payment = createCardPayment();
    OrderService order = new OrderService();
    order.setPaymentService(payment);
    return order;
}
```

**With `@Autowired` — Spring wires automatically:**

```java
// On the setter in OrderService
@Autowired
public void setPaymentService(PaymentService paymentService) {
    this.paymentService = paymentService;
}

// In AppConfig — Spring injects the bean automatically
@Bean
public OrderService createOrderService(PaymentService paymentService) {
    return new OrderService();
}
```

---

## 10 — `@Primary` & `@Qualifier` in `@Bean`

When multiple `@Bean` methods return the same type, resolve the ambiguity with `@Primary` or `@Qualifier`:

```java
@Bean
@Qualifier("cp")                          // custom bean name
public PaymentService createCardPayment() {
    return new CardPayment();
}

@Bean
@Primary                                  // default when no qualifier specified
public PaymentService createUpiPayment() {
    return new UpiPayment();
}

@Bean
public OrderService createOrderService(@Qualifier("cp") PaymentService paymentService) {
    return new OrderService(paymentService);
}
```

> If both `@Component` and `@Bean` exist for the same type, `@Bean` takes priority.

---

## Key Takeaways

| Concept | Rule of thumb |
|---|---|
| `@Component` | Use on your own classes with no-arg or autowirable constructors |
| `@Bean` | Use for external classes or when you need to pass values to the constructor |
| `@Primary` | Default bean when multiple of the same type exist |
| `@Qualifier` | Explicitly select a bean by name at the injection point |
| Constructor injection | Always preferred — immutable, testable, no partial init |
| `@ComponentScan` | Tells Spring which package to search for `@Component` beans |
| `ApplicationContext` | The IoC container — start it once in `main`, then retrieve beans from it |