# Performance and Scalability (Java)
# Performance and scalability are critical for building applications that can handle increasing users, data, and workloads efficiently.
# 1. Algorithmic Complexity
# Algorithmic complexity measures how an algorithm's resource consumption grows with input size.
# Time Complexity
Complexity | Example
O(1)       | Array access
O(log n)   | Binary Search
O(n)       | Linear Search
O(n log n) | Merge Sort
O(n²)      | Nested Loops
O(2ⁿ)      | Recursive Fibonacci

# Example
// O(n)
for (int i = 0; i < n; i++) {
    System.out.println(i);
}

// O(n²)
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        System.out.println(i + "," + j);
    }
}

# Space Complexity
# Amount of memory required.
int[] arr = new int[n]; // O(n) space

# 2. Profiling
# Profiling identifies performance bottlenecks.
# Types
# - CPU profiling
# - Memory profiling
# - Thread profiling
# - I/O profiling

# Popular Tools
# - JProfiler
# - VisualVM
# - Java Flight Recorder (JFR)
# - YourKit

# 3. CPU Profiling
# Measures where CPU time is spent.
# Common Problems
# - Infinite loops
# - Heavy computations
# - Excessive synchronization
# Example
for(int i = 0; i < 1_000_000_000; i++) {
    Math.sqrt(i);
}
# CPU profiler shows method consuming most execution time.

# Commands
jcmd <pid> JFR.start
jstack <pid>

# 4. Memory Profiling
# Analyzes heap usage and object allocation.
# Common Issues
# - Memory leaks
# - Excessive object creation
# - Large collections
# Bad Example
List<String> cache = new ArrayList<>();

while (true) {
    cache.add(UUID.randomUUID().toString());
}
# Memory continuously grows.
# Useful Tools
# - VisualVM
# - JProfiler
# - Eclipse MAT

# 5. JVM Tuning
# Optimizing Java Virtual Machine settings.
# Heap Configuration
-Xms2g
-Xmx4g
# Xms = Initial heap size
# Xmx = Maximum heap size

# Example
java -Xms1g -Xmx2g App
# Metaspace
-XX:MaxMetaspaceSize=512m

# 6. Garbage Collector Tuning
# GC reclaims unused objects.
# Common Collectors
# Serial GC
-XX:+UseSerialGC
# - Small applications.
# Parallel GC
-XX:+UseParallelGC
# - Maximum throughput.
# G1 GC
-XX:+UseG1GC
# - Default in modern Java.
# ZGC
-XX:+UseZGC
# - Very low pause times.
# Monitoring GC
-Xlog:gc
# Common Metrics
# - GC frequency
# - Pause time
# - Heap occupancy

# 7. Thread Pool Sizing
# - Choosing optimal thread count.
# CPU-Bound Tasks
# Formula: Threads = Number of CPU Cores
# Example: 8 cores → 8 threads
# I/O-Bound Tasks
# Formula: Threads = Cores × (1 + Wait Time / Compute Time)
# Example:
8 × (1 + 90/10)
= 80 threads
# Thread Pool
ExecutorService pool =
    Executors.newFixedThreadPool(8);

# 8. Connection Pool Sizing
# Database connections are expensive.
# Popular Pools
# - HikariCP
# - Apache DBCP
# - C3P0
# HikariCP Configuration
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5

# Too Many Connections
# Problems:
# - DB overload
# - Context switching
# - Memory waste

# 9. Database Query Optimization
# Poor queries are often the biggest bottleneck.
# Bad Query: SELECT * FROM users;
# Better Query:
SELECT id, name
FROM users
WHERE status = 'ACTIVE';

# Techniques
# - Fetch only required columns
# - Use indexes
# - Avoid N+1 queries
# - Limit result size
# - Use joins wisely

# 10. Indexing
# Indexes speed up data retrieval.
# Without Index
SELECT * FROM users
WHERE email = 'abc@test.com';
# - Full table scan.
# Create Index
CREATE INDEX idx_email
ON users(email);
# Types
# - Primary Index
# - Unique Index
# - Composite Index
# - Clustered Index
# - Non-Clustered Index

# 11. Caching Strategies
# Caching reduces expensive computations.
# Types
# In-Memory
Map<Long, User> cache = new HashMap<>();
# Distributed Cache
# - Redis
# - Hazelcast
# - Memcached

# Cache Patterns
# Cache Aside
if(cache.containsKey(id))
    return cache.get(id);
# Else read database and update cache.

# Write Through
Write cache + DB together

# Write Back
Write cache first 
Persist later

# 12. Pagination at Scale
# - Avoid loading huge datasets.
# Offset Pagination
SELECT *
FROM users
LIMIT 20 OFFSET 1000;
# Problem: Large offsets become slow.
# Keyset Pagination
SELECT *
FROM users
WHERE id > 1000
ORDER BY id
LIMIT 20;
# Preferred for large datasets.

# 13. Load Testing
# Determines system behavior under load.
# Goals
# - Find bottlenecks
# - Measure throughput
# - Measure latency
# - Determine breaking point
# Tools
# - JMeter
# - Gatling
# - k6
# Example: k6 run script.js
# Metrics: Requests/sec, Error rate, Response time

# 14. JMH Benchmarks
# JMH = Java Microbenchmark Harness.
# Used for accurate Java benchmarking.
# Dependency
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-core</artifactId>
    <version>1.37</version>
</dependency>
# Example
@Benchmark
public void testMethod() {
    Math.sqrt(1000);
}
# Run:
mvn clean install
java -jar target/benchmarks.jar

# 15. Latency vs Throughput
# Latency
# - Time taken for one request.
Request → Response = 50 ms
# - Lower latency is better.
# Throughput
# - Number of requests handled.
10,000 requests/sec
# - Higher throughput is better.
# Example
System | Latency | Throughput
A      |  20ms   | 1000 req/s
B      |  40ms   | 5000 req/s
# - System B handles more traffic but responds slower.

# 16. Backpressure
# Protects systems from overload.
# Problem
# - Producer faster than consumer.
Producer -> 10,000 msgs/sec
Consumer -> 2,000 msgs/sec
# - Queue grows endlessly.
# Solutions
# Rate Limiting
RateLimiter limiter =
    RateLimiter.create(100);
# Bounded Queue
new ArrayBlockingQueue<>(1000);
# Reactive Streams
# Backpressure is built into:
# - Project Reactor
# - RxJava

# 17. Horizontal vs Vertical Scaling
# Vertical Scaling
# - Increase machine resources.
4 CPU → 32 CPU
# Advantages
# - Simple
# Disadvantages
# - Hardware limits

# Horizontal Scaling
# - Add more servers.
1 Server → 10 Servers
# Advantages
# - Better scalability
# - Better fault tolerance
# Disadvantages
# - Distributed system complexity

# 18. Capacity Planning
# - Predict future resource needs.
# Metrics to Monitor
# - CPU usage
# - Memory usage
# - Database load
# - Network traffic
# - Disk I/O
# Formula
Future Load =
Current Load × Growth Rate
# Example
Current users = 100,000
Growth = 20%

Future users
= 120,000

# Capacity Planning Steps
# 1. Measure current load.
# 2. Define growth expectations.
# 3. Load test future traffic.
# 4. Identify bottlenecks.
# 5. Scale infrastructure.

# Interview Questions
# Q1. Difference between latency and throughput?
# Latency = Time for one request.
# Throughput = Number of requests processed per unit time.

# Q2. Why is keyset pagination faster?
# Because it uses indexed values and avoids scanning skipped rows.

# Q3. What is backpressure?
# A mechanism that prevents producers from overwhelming consumers.

# Q4. When should G1GC be used?
# Large heap applications requiring balanced throughput and pause times.

# Q5. What is the purpose of JMH?
# To create reliable Java microbenchmarks while avoiding JVM optimization biases.

# Performance Optimization Checklist
# ✅ Choose efficient algorithms (Big-O)
# ✅ Profile before optimizing
# ✅ Tune JVM and GC
# ✅ Use appropriate thread pools
# ✅ Optimize SQL queries
# ✅ Create proper indexes
# ✅ Add caching
# ✅ Use pagination
# ✅ Load test regularly
# ✅ Monitor latency and throughput
# ✅ Apply backpressure
# ✅ Plan capacity before traffic spikes
# ✅ Scale horizontally when needed