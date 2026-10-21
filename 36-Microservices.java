# Microservices (Java / Spring Boot)
# 1. Monolith vs Microservices
# Monolith
# A single application containing all modules.
# Advantages
# - Simple development and deployment
# - Easier testing
# - Lower operational complexity
# - Simple debugging
# Disadvantages
# - Large codebase
# - Difficult scaling
# - Technology lock-in
# - Longer deployment cycles

# Microservices
# Application split into independent services.
# Advantages
# - Independent deployment
# - Independent scaling
# - Better fault isolation
# - Different technologies possible
# Disadvantages
# - Distributed system complexity
# - Network latency
# - Harder debugging
# - Data consistency challenges
# Example
Monolith 
+---------------------+ 
| User Module         | 
| Product Module      | 
| Order Module        | 
| Payment Module      | 
+---------------------+

Microservices 
- User Service 
- Product Service 
- Order Service 
- Payment Service

# 2. Service Boundaries
# A service boundary defines what functionality belongs to a microservice.
# Good Boundary Example
User Service 
├─ Registration 
├─ Authentication 
└─ User Profile 

Order Service 
├─ Create Order 
├─ Cancel Order 
└─ Order History

# Rules
# - Single business responsibility
# - Own database
# - Loose coupling
# - High cohesion

# 3. Domain-Driven Design (DDD) Fundamentals
# DDD helps identify microservice boundaries.
# Key Concepts
# Domain
# - Business problem area.
# Example: E-Commerce Domain
# Subdomain
Ordering 
Inventory 
Payment 
Shipping

# Bounded Context
# - A logical boundary where a model is valid.
Ordering Context 
Payment Context
# - Usually one microservice = one bounded context.

# Entities
# - Objects with identity.
public class Customer {
    private Long id;
    private String name;
}

# Value Object
# - No identity.
public record Address(
    String city,
    String state
) {}

# 4. Spring Cloud
# - Collection of tools for microservices.
# Provides
# - Service Discovery
# - API Gateway
# - Config Server
# - Load Balancing
# - Circuit Breakers
# - Distributed Tracing
# Popular modules:
Spring Cloud Eureka 
Spring Cloud Gateway 
Spring Cloud Config 
OpenFeign

# 5. Service Discovery
# - Services register themselves dynamically.
# Problem
# - Order Service needs User Service URL.
# Bad: user.service.url=http://localhost:8081
# Solution
# - Use Eureka.
Order Service 
| 
v 
Eureka Server 
^ 
| 
User Service

# Dependency
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>
      spring-cloud-starter-netflix-eureka-client
    </artifactId>
</dependency>

# 6. API Gateway
# Single entry point for all client requests.
Client 
    | 
API Gateway 
/       \ 
User    Order 
Svc     Svc

# Responsibilities
# - Authentication
# - Routing
# - Rate limiting
# - Logging
# - Load balancing
# Spring Cloud Gateway
routes:
  - id: user-service
    uri: lb://USER-SERVICE
    predicates:
      - Path=/users/**

# 7. Centralized Configuration
# Manage configurations in one place.
# Spring Cloud Config
Git Repository 
| 
Config Server 
| 
Microservices
# Example
server.port=8081
database.url=mysql-url
# Benefits:
# - Central management
# - Dynamic refresh
# - Environment-specific configs

# 8. Inter-Service Communication
# Two approaches:
# Synchronous
Order -> User
# REST APIs.
# Asynchronous
Order -> Kafka -> User
# Event-driven.

# 9. REST Clients
# Used to call another service.
# Options
# - RestTemplate (legacy)
# - WebClient
# - OpenFeign

# 10. OpenFeign
# Declarative REST client.
# Example
@FeignClient("USER-SERVICE")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable Long id);
}

# Usage: UserDto user = userClient.getUser(1L);
# Advantages:
# - Less boilerplate
# - Service discovery integration
# - Easy maintenance

# 11. WebClient
# - Modern non-blocking HTTP client.
WebClient client = WebClient.builder()
        .baseUrl("http://user-service")
        .build();
Mono<UserDto> user = client.get()
        .uri("/users/1")
        .retrieve()
        .bodyToMono(UserDto.class);
# Advantages
# - Reactive
# - Non-blocking
# - High throughput

# 12. Load Balancing
# Distributes requests among instances.
# Example
Order Service 
| 
v 
Load Balancer 
/       \ 
User1   User2

# Spring Cloud LoadBalancer automatically works with Eureka.

# 13. Circuit Breakers
# Prevent cascading failures.
# Without circuit breaker:
Order -> User (Down) 
            ^ 
            | 
            Fail
# With circuit breaker:
Closed 
| 
Open 
| 
Half Open
# States
# 1. Closed
# 2. Open
# 3. Half-Open

# 14. Retries and Timeouts
# Timeout
# - Don't wait forever.
WebClient.builder()
    .clientConnector(...)
# Example: Timeout = 2 seconds
# Retry
# - Retry temporary failures.
retry(3)
# Best Practice: Retry + Timeout + Circuit Breaker

# 15. Resilience4j
# Fault tolerance library.
# Features
# - CircuitBreaker
# - Retry
# - Bulkhead
# - RateLimiter
# - TimeLimiter
# Example
@CircuitBreaker(name = "userService")
public UserDto getUser(Long id) {
    return client.getUser(id);
}
# Fallback:
public UserDto fallback(
        Long id,
        Exception ex) {

    return new UserDto();
}

# 16. Distributed Tracing
# Track a request across services.
Client 
| 
Gateway 
| 
Order Service 
| 
User Service

# Tools: Zipkin, Jaeger, OpenTelemetry

# 17. Correlation IDs
# Unique ID added to each request.
# Example:
X-Correlation-ID: A123-B456-C789
# Logs:
Order Service [A123] 
User Service [A123] 
Payment [A123]
# Helpful for debugging.

# 18. Centralized Logging
# Collect logs from all services.
Microservices 
| 
Fluent Bit 
| 
Elasticsearch 
| 
Kibana

# Benefits:
# - Search all logs
# - Faster debugging
# - Log analytics

# 19. Metrics and Monitoring
# Metrics
# - CPU
# - Memory
# - Response Time
# - Error Rate
# Spring Boot Actuator
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>
      spring-boot-starter-actuator
    </artifactId>
</dependency>
# Stack
Micrometer 
| 
Prometheus 
| 
Grafana

# 20. Event-Driven Architecture
# Services communicate using events.
Order Created 
| 
Kafka 
| 
Inventory 
Payment 
Shipping

# Advantages
# - Loose coupling
# - Scalability
# - High availability

# 21. Saga Pattern
# Manages distributed transactions.
# Choreography
Order Created 
| 
Payment Event 
| 
Shipping Event

# Orchestration
Saga Orchestrator 
| 
Payment 
Inventory 
Shipping

# If a step fails: Compensating Transaction
# Example: Refund Payment

# 22. Outbox Pattern
# Ensures database update and event publishing remain consistent.
# Problem:
DB Updated 
Kafka Event Failed
# Solution:
Transaction 
| 
DB Update 
Outbox Table

# Background process publishes events.
Outbox -> Kafka

# 23. Idempotent Consumers
# Same event processed multiple times should produce the same result.
# Example:
OrderCreated Event 
OrderCreated Event 
OrderCreated Event
# Process only once.
if(processed(eventId))
    return;
# Store processed IDs.

# 24. Eventual Consistency
# Data may not be immediately consistent.
# Example:
Order Created 
Inventory Updated Later
# Short period:
Different Services 
Different Data
# Eventually: 
Consistent State

# 25. Distributed Transactions
# Transaction spanning multiple services.
# Example:
Order Service 
Payment Service 
Inventory Service
# Challenges
# - Network failures
# - Partial commits
# - Rollbacks
# Modern Approach
# - Avoid 2PC when possible.
# Prefer:
Saga Pattern + Event-Driven Architecture

# 26. Contract Testing
# Ensures producer and consumer APIs remain compatible.
# Example
# Producer:
{
  "id": 1,
  "name": "Aman"
}
# Consumer expects:
{
  "id": 1,
  "name": "Aman"
}
# Spring Cloud Contract
# Automatically verifies contracts.
# Benefits:
# - Detect breaking changes early
# - Safer deployments
# - Better service integration

# Typical Spring Boot Microservices Architecture
                    Client
                       |
                       v
               API Gateway
                       |
         +-------------+-------------+
         |             |             |
         v             v             v
   User Service   Order Service  Payment Service
         |             |             |
         +-------------+-------------+
                       |
                 Eureka Server
                       |
                 Config Server
                       |
                     Kafka
                       |
     +-----------------+----------------+
     |                                  |
 Distributed Tracing          Monitoring & Logging
(OpenTelemetry/Zipkin)     (Prometheus/Grafana/ELK)

#  Interview Topics (Most Important)
Monolith vs Microservices
DDD & Bounded Context
Eureka Service Discovery
Spring Cloud Gateway
OpenFeign vs WebClient
Circuit Breaker & Resilience4j
Saga Pattern
Outbox Pattern
Distributed Tracing
Eventual Consistency
Contract Testing
Kafka-based Event-Driven Architecture

# These are the core microservices concepts expected in Java Spring Boot interviews from mid-level to senior developer roles.