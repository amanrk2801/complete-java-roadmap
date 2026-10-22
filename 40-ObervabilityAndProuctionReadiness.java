# Observability and Production Readiness (Spring Boot)
# Observability is the ability to understand what is happening inside an application by using logs, metrics, traces, and diagnostics. Production readiness ensures the application can be monitored, maintained, and troubleshot effectively in real-world environments.

# 1. Application Logging
# Logging records application events, errors, and execution details.
# Why Logging?
# - Debug issues
# - Audit activities
# - Monitor application behavior
# - Analyze production incidents
# Spring Boot Logging Framework
# Spring Boot uses:
# - SLF4J (API)
# - Logback (Implementation)
# Example
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);

    public void createUser(String name) {
        log.info("Creating user: {}", name);

        try {
            // business logic
        }
        catch (Exception e) {
            log.error("Error while creating user", e);
        }
    }
}

# 2. Structured Logs
# Instead of plain text logs, use JSON format.
# Traditional Log
User created successfully
# Structured Log
{
  "timestamp":"2026-10-22T10:30:00",
  "level":"INFO",
  "service":"user-service",
  "userId":"123",
  "message":"User created"
}
# Benefits
# - Searchable
# - Machine-readable
# - Easy integration with ELK stack
    -   Elasticsearch
    -   Logstash
    -   Kibana

# Logback JSON Configuration
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
</dependency>
<encoder
class="net.logstash.logback.encoder.LogstashEncoder"/>

# 3. Log Levels
# Help control log verbosity.
Level | Purpose
TRACE | Very detailed information
DEBUG | Debugging information
INFO  | Normal application events
WARN  | Suspicious situations
ERROR | Errors and failures

# Example
log.trace("Method entered");
log.debug("User details loaded");
log.info("Order created");
log.warn("Cache is almost full");
log.error("Payment failed");

# Configuration
logging.level.root=INFO
logging.level.com.example=DEBUG

# 4. Spring Boot Actuator
# Provides production-ready monitoring endpoints.
# Dependency
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
# Configuration
management.endpoints.web.exposure.include=*
# Common Endpoints
Endpoint            |   Description
/actuator/health    |   Application health
/actuator/info      |   Application info
/actuator/metrics   |   Metrics
/actuator/env       |   Environment properties
/actuator/beans     |   Spring beans
/actuator/loggers   |   Logger configuration
/actuator/threaddump|   Thread dump
/actuator/heapdump  |   Heap dump   

# Example
GET /actuator/health

{
  "status":"UP"
}

# 5. Micrometer
# Micrometer is the metrics facade used by Spring Boot.
# Supported Monitoring Systems
# - Prometheus
# - Datadog
# - New Relic
# - CloudWatch
# Dependency
<dependency>
 <groupId>io.micrometer</groupId>
 <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>

# Custom Counter
@Autowired
MeterRegistry registry;

public void placeOrder() {

    Counter counter = registry.counter("orders.total");

    counter.increment();
}

# 6. Prometheus
# Prometheus collects metrics from applications.
# Workflow
Spring Boot
      │
      ▼
Micrometer
      │
      ▼
/actuator/prometheus
      │
      ▼
Prometheus Server

# Configuration
management:
  endpoints:
    web:
      exposure:
        include: prometheus
# Metrics Endpoint
/actuator/prometheus

# Example:
jvm_memory_used_bytes
http_server_requests_seconds_count
system_cpu_usage

# 7. Grafana
# Grafana visualizes Prometheus metrics.
# Dashboard Examples
# - CPU Usage
# - Memory Usage
# - Request Count
# - Error Rate
# - JVM Statistics
# Architecture
Application
    │
Micrometer
    │
Prometheus
    │
Grafana

# Sample Dashboard Widgets
# - Requests/sec
# - Average response time
# - 95th percentile latency
# - Heap memory
# - Active threads

# 8. Distributed Tracing
# Tracks a request across multiple microservices.
# Problem
Client
  │
  ▼
Service A
  │
  ▼
Service B
  │
  ▼
Service C
# - If a request fails, where exactly did it fail?
# - Distributed tracing answers this.
# Concepts
# Trace: Entire request journey.
# Span: Single operation within trace.
Trace
 ├─ Span A
 ├─ Span B
 └─ Span C

# 9. OpenTelemetry
# Industry standard observability framework.
# Provides
# - Metrics
# - Logs
# - Traces
# Dependency
<dependency>
  <groupId>io.opentelemetry</groupId>
  <artifactId>opentelemetry-api</artifactId>
</dependency>
# Create Span
Span span =
    tracer.spanBuilder("createOrder")
          .startSpan();

try {
    // business logic
}
finally {
    span.end();
}
# Benefits
# Vendor neutral
# Works with:
# - Jaeger
# - Zipkin
# - Tempo
# - Datadog

# 10. Metrics and Alerting
# Metrics indicate system health.
# Important Metrics
# JVM
Heap Usage
GC Activity
Thread Count
CPU Usage
# HTTP
Request Count
Response Time
Error Rate
# Business Metrics
Orders Created
Payments Completed
Users Registered
# Prometheus Alert Example
groups:
- name: alerts

  rules:
  - alert: HighCPUUsage

    expr: system_cpu_usage > 0.8

    for: 5m

    labels:
      severity: critical

# 11. Health Checks
# Used by monitoring systems and Kubernetes.
# Types
# Dependency Health
# - Database
# - Redis
# - Kafka
# - External APIs

# Example
GET /actuator/health
# Response:
{
  "status":"UP"
}

# 12. Readiness and Liveness
# Very important in Kubernetes.
# Liveness Probe
# Checks: Is application alive?
# If NO: Restart Pod
# Example: /actuator/health/liveness

# Readiness Probe
# Checks: Is application ready for traffic?
# Example: /actuator/health/readiness
# If not ready: Do not send traffic

# 13. Thread and Heap Diagnostics
# Thread Dump
# Shows all running threads.
# Endpoint: /actuator/threaddump
# Useful for: Deadlocks, Stuck threads, Thread starvation

# Heap Dump
# Shows objects in memory.
# Endpoint: /actuator/heapdump

# Used with:
# - Eclipse MAT
# - VisualVM
# Useful for:
# - Memory leaks
# - Excessive object creation

# 14. Performance Monitoring
# Continuous monitoring of application performance.
# Key Areas
# JVM Monitoring
Memory
GC
Threads
CPU

# Database Monitoring
Slow Queries
Connection Pool Usage
Lock Contention

# API Monitoring
Latency
Throughput
Error Rates
Availability

# Useful Tools
# - Grafana
# - Prometheus
# - VisualVM
# - JProfiler
# - Java Mission Control (JMC)

# 15. Production Incident Troubleshooting
# - A systematic approach to diagnosing issues.
# Step 1: Check Health
curl http://localhost:8080/actuator/health

# Step 2: Review Logs
grep ERROR application.log

# Look for:
# - Exceptions
# - Failed requests
# - Dependency failures

# Step 3: Verify Metrics
# Check:
# - CPU usage
# - Memory usage
# - Error rate
# - Response time

# Step 4: Analyze Thread Dump
# Look for:
# - BLOCKED threads
# - Deadlocks
# - Long-running requests

# Step 5: Analyze Heap Dump
# Look for:
# - Memory leaks
# - Large collections
# - Unreleased objects

# Step 6: Use Distributed Tracing
# Trace request:
Gateway
   ↓
Order Service
   ↓
Payment Service
   ↓
Notification Service
# Identify the failing span quickly.

# Complete Production Monitoring Architecture
                    ┌─────────────────┐
                    │ Spring Boot App │
                    └────────┬────────┘
                             │
            ┌────────────────┼────────────────┐
            │                │                │
            ▼                ▼                ▼

         Logs            Metrics          Traces
            │                │                │
            ▼                ▼                ▼

     ELK Stack         Prometheus     OpenTelemetry
            │                │                │
            ▼                ▼                ▼

        Kibana          Grafana        Jaeger/Zipkin

            └────────────┬─────────────┘
                         ▼

                 Observability

# Interview Questions
# Q1. What are the three pillars of observability?
# Logs, Metrics, and Traces.

# Q2. Difference between Prometheus and Grafana?
# Prometheus collects metrics, Grafana visualizes metrics.

# Q3. What is a Span?
# A single operation inside a distributed trace.

# Q4. Difference between Readiness and Liveness?
# - Liveness: Is application alive?
# - Readiness: Is application ready to serve traffic?

# Q5. Why use Micrometer?
# Provides a vendor-neutral metrics API and integrates Spring Boot with monitoring systems like Prometheus.

# Q6. How do you investigate a production issue?
# Health Check → Logs → Metrics → Thread Dump → Heap Dump → Distributed Trace.

