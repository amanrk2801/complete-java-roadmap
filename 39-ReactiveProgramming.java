# Reactive Programming (Java)
# Reactive Programming is a programming paradigm focused on asynchronous data streams and non-blocking execution. Instead of repeatedly asking for data, components react when data becomes available.

# 1. Reactive Programming Fundamentals
# Traditional Programming
String data = getData(); // waits until data arrives
System.out.println(data);
# The thread is blocked until the result is returned.

# Reactive Programming
publisher.subscribe(data -> System.out.println(data));
# The subscriber is notified automatically when data is available.
# Characteristics
# - Asynchronous
# - Event-driven
# - Non-blocking
# - Scalable
# - Resilient
# Real-world Example
# - User clicks button
# - Event is generated
# - Application reacts to event
# - UI updates automatically

# 2. Reactive Streams
# Reactive Streams is a specification for asynchronous stream processing with non-blocking backpressure.
# Main Interfaces
Publisher<T>
Subscriber<T>
Subscription
Processor<T,R>
# Workflow
Publisher
    ↓
Subscription
    ↓
Subscriber

# 3. Publisher and Subscriber
# Publisher
# - Produces data.
Publisher<String> publisher;
# Example:
Flux.just("Java", "Spring", "Reactor");
# Subscriber
# - Consumes data.
Subscriber<String> subscriber;
# Example:
Flux.just("Java", "Spring", "Reactor")
    .subscribe(System.out::println);
# Output:
Java
Spring
Reactor

# Subscriber Lifecycle
onSubscribe()
onNext()
onError()
onComplete()
# Example:
Flux.just("A", "B", "C")
    .subscribe(
        System.out::println,
        Throwable::printStackTrace,
        () -> System.out.println("Completed")
    );

# 4. Backpressure
# Backpressure prevents consumers from being overwhelmed by producers.
# Problem
Producer: 10000 items/second
Consumer: 100 items/second
# Memory issues may occur.
# Solution
# - Consumer requests data at its own speed.
subscription.request(10);
# Example
Flux.range(1, 100)
    .limitRate(10)
    .subscribe(System.out::println);
# Only a limited number of items are requested at a time.

# 5. Project Reactor
# Project Reactor is Spring's reactive library.
# Core Types
Mono<T>
Flux<T>
# Maven Dependency
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-core</artifactId>
</dependency>
# Example
Flux.just(1, 2, 3)
    .subscribe(System.out::println);

# 6. Mono
# Represents 0 or 1 value.
# Create Mono
Mono<String> mono = Mono.just("Java");
# Subscribe
mono.subscribe(System.out::println);
# Output
Java

# Empty Mono
Mono.empty();
# From Supplier
Mono.fromSupplier(() -> "Generated");

# Example
Mono<String> user = Mono.just("Aman");

user.subscribe(System.out::println);

# 7. Flux
# Represents 0 to N values.
# Create Flux
Flux<String> flux =
    Flux.just("Java", "Spring", "Reactor");
# Subscribe
flux.subscribe(System.out::println);
# Output
Java
Spring
Reactor

# Range Example
Flux.range(1, 5)
    .subscribe(System.out::println);
# Output
1
2
3
4
5

# Mono vs Flux
Feature     | Mono          | Flux
Values      | 0 or 1        | 0 to N
Return Type | Single result | Multiple results
Example     | User details  | List of users

Mono<User>
Flux<User>

# 8. Operators
# Operators transform stream data.
# map()
Flux.just("java", "spring")
    .map(String::toUpperCase)
    .subscribe(System.out::println);
# Output
JAVA
SPRING

# filter()
Flux.range(1, 10)
    .filter(n -> n % 2 == 0)
    .subscribe(System.out::println);
# Output
2
4
6
8
10

# flatMap()
Flux.just("Java", "Spring")
    .flatMap(item ->
        Mono.just(item.toUpperCase())
    )
    .subscribe(System.out::println);

# take()
Flux.range(1, 100)
    .take(5)
    .subscribe(System.out::println);
# Output
1
2
3
4
5

# reduce()
Flux.range(1, 5)
    .reduce(Integer::sum)
    .subscribe(System.out::println);
# Output
15

# 9. Error Handling
# Reactive streams should handle failures gracefully.
# onErrorReturn()
Flux.just(1, 2, 0, 4)
    .map(n -> 10 / n)
    .onErrorReturn(-1)
    .subscribe(System.out::println);
# Output
10
5
-1

# onErrorResume()
Mono.error(new RuntimeException())
    .onErrorResume(e ->
        Mono.just("Fallback")
    )
    .subscribe(System.out::println);
# Output
Fallback

# doOnError()
Flux.just(1, 0)
    .map(i -> 10 / i)
    .doOnError(System.out::println)
    .subscribe();

# 10. WebFlux
# Spring WebFlux is Spring's reactive web framework.
# Dependency
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

# Reactive Controller
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public Mono<User> getUser(
            @PathVariable Long id) {
        return userService.findById(id);
    }
}

# Returning Multiple Records
@GetMapping
public Flux<User> getUsers() {
    return userService.findAll();
}

# 11. Reactive Database Access
# Traditional JPA is blocking.
@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {
}

# Reactive applications use R2DBC.
# Dependency
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>

# Repository
public interface UserRepository
        extends ReactiveCrudRepository<User, Long> {
}

# Query
public Flux<User> findAllUsers() {
    return repository.findAll();
}

# 12. Reactive vs Traditional MVC
Feature             | Spring MVC            | Spring WebFlux
Programming Model   | Synchronous           | Reactive
Threading           | One thread per request| Event Loop
Blocking            | Yes                   | No
Scalability         | Moderate              | High
Streaming Support   | Limited               | Excellent
Return Type         | Object                | Mono/Flux

# MVC
@GetMapping
public List<User> getUsers() {
    return service.getUsers();
}

# Reactive
@GetMapping
public Flux<User> getUsers() {
    return service.getUsers();
}

# 13. When Reactive Programming is Appropriate
# Use Reactive When
# ✅ High concurrency applications
# ✅ Real-time systems
# ✅ Chat applications
# ✅ Streaming systems
# ✅ WebSockets
# ✅ Event-driven architecture
# ✅ Microservices with many I/O calls
# ✅ API Gateway
# Example:
Netflix
Uber
Amazon
Trading Systems
IoT Platforms

# Avoid Reactive When
# ❌ Small CRUD applications
# ❌ CPU-intensive calculations
# ❌ Simple monolithic applications
# ❌ Teams unfamiliar with reactive concepts
# Example:
Simple Employee CRUD
Small School Management System
Basic Inventory System
# Spring MVC is often easier and sufficient for these.

# Interview Questions
# 1. What is Reactive Programming?
# - A non-blocking, asynchronous programming model where applications react to data/events as they occur.

# 2. Difference between Mono and Flux?
Mono<T>  // 0 or 1 item
Flux<T>  // 0 to many items

# 3. What is Backpressure?
# A mechanism that allows consumers to control the rate at which they receive data.

# 4. Why WebFlux?
# To build highly scalable, non-blocking applications.

# 5. What is R2DBC?
# Reactive Relational Database Connectivity used for non-blocking database access.

# 6. What is flatMap()?
# Transforms each item into another Publisher and merges the results.
Flux.just("Java")
    .flatMap(v -> Mono.just(v.toUpperCase()));

# 7. What are the core interfaces of Reactive Streams?
Publisher
Subscriber
Subscription
Processor

# These are the most important Java/Spring Reactive Programming concepts commonly asked in Spring Boot and Java backend interviews.