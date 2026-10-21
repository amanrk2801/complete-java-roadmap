# Caching (Spring Boot / Java)
# Caching is a technique used to store frequently accessed data in a fast storage layer so that future requests can be served faster without repeatedly accessing the database or external systems.

# 1. Cache Fundamentals
# A cache is a temporary storage area that keeps copies of frequently used data.
# Why use caching?
# - Reduces database load
# - Improves response time
# - Increases application throughput
# - Reduces network calls
# Without Cache
Client
  |
  v
Application
  |
  v
Database
# Every request hits the database.
# With Cache
Client
  |
  v
Application
  |
  +--> Cache (Redis)
  |       |
  |       +--> Data Found (Fast)
  |
  +--> Database (If Cache Miss)

# 2. Cache-Aside Pattern
# The application is responsible for managing the cache.
# Flow
1. Check cache
2. If found → return data
3. If not found:
      Read from DB
      Store in cache
      Return response

# Example
public User getUser(Long id) {

    User user = cache.get(id);

    if (user == null) {
        user = userRepository.findById(id).orElse(null);

        if (user != null) {
            cache.put(id, user);
        }
    }

    return user;
}
# Advantages
# - Simple implementation
# - Only frequently used data is cached
# Disadvantages
# - Cache and DB may become inconsistent

# 3. Read-Through and Write-Through
# Read-Through
# - Cache itself loads data from the database.
Application
     |
     v
   Cache
     |
     v
Database
# Application never talks directly to the DB.
# Flow
Cache Hit  -> Return Data
Cache Miss -> Cache loads DB data automatically
# Benefits
# - Simpler application code
# - Centralized cache management

# Write-Through
# - Whenever data is written:
Application
     |
     v
   Cache
     |
     v
Database
# Data is written to both cache and database immediately.
# Advantages
# - Strong consistency
# - Cache always updated
# Disadvantages
# - Slower writes

# 4. Write-Behind (Write-Back)
# - Writes go to cache first.
Application
      |
      v
     Cache
      |
      | (later)
      v
   Database
# Flow
Write → Cache
Return Success

After some delay:
Cache → Database

# Advantages
# - Very fast writes
# - Reduced DB load
# Disadvantages
# - Risk of data loss if cache crashes before DB update

# 5. Eviction Policies
# - Cache memory is limited.
# - When cache becomes full, old entries are removed using eviction policies.

# LRU (Least Recently Used)
# Removes item not used for the longest time.
Access:
A B C D

Use A

Order:
B C D A

Add E

Remove B
# Most commonly used.

# LFU (Least Frequently Used)
# Removes item accessed least often.
A -> 10 accesses
B -> 2 accesses
C -> 1 access

Remove C

# FIFO (First In First Out)
# Remove oldest inserted entry.
A B C D

Add E

Remove A

# Random
# Random entry chosen for removal.

# 6. TTL (Time To Live)
# TTL defines how long a cache entry remains valid.
# Example
User Profile

TTL = 10 minutes

After 10 min:
Cache entry expires
# Redis Example
redisTemplate.opsForValue()
             .set("user:1", user, Duration.ofMinutes(10));
# Benefits:
# - Prevents stale data
# - Automatic cleanup

# 7. Cache Invalidation
# One of the hardest problems in software engineering.
# Problem
DB Value = John

Cache Value = John

DB Updated = Mike

Cache Still = John

# User gets stale data.
# Solutions
# - Time-based
# TTL 5 minutes

# - Event-based
# cache.evict(userId);
# - Whenever data changes, remove cache entry.

# - Version-based
user:v1
user:v2
# - New version gets a new cache key.

# 8. Spring Cache Abstraction
# Spring provides annotations to simplify caching.
# Dependency:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
# Enable caching:
@SpringBootApplication
@EnableCaching
public class Application {
}

# 9. @Cacheable
# Stores method result in cache.
# Example
@Service
public class UserService {

    @Cacheable("users")
    public User getUser(Long id) {
        System.out.println("Fetching from DB");
        return userRepository.findById(id).orElseThrow();
    }
}
# First Call
getUser(1);
# Output: Fetching from DB

# Second Call
getUser(1);
# Output: (No DB call)
# Data returned from cache.

# Custom Key
@Cacheable(value = "users", key = "#id")
public User getUser(Long id)

# 10. @CachePut
# Always executes method and updates cache.
@CachePut(value = "users", key = "#user.id")
public User updateUser(User user) {
    return userRepository.save(user);
}
# Difference
@Cacheable -> May skip method execution
@CachePut  -> Always executes method
# Use during updates.

# 11. @CacheEvict
# Removes entries from cache.
# Remove One Entry
@CacheEvict(value = "users", key = "#id")
public void deleteUser(Long id) {
    userRepository.deleteById(id);
}
# Clear Entire Cache
@CacheEvict(value = "users", allEntries = true)
public void clearUsersCache() {
}

# 12. Redis
# Redis is an in-memory key-value data store widely used for caching.
# Features
# - Extremely fast
# - In-memory
# - Supports TTL
# - Distributed
# - Persistent storage option
# Redis Example
Key: user:1

Value:
{
  "id":1,
  "name":"John"
}

# Spring Redis Dependency
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

# 13. Distributed Caching
# Used when multiple application instances exist.
# Problem
App-1 Cache -> User A
App-2 Cache -> Empty
# Caches become inconsistent.

# Solution
# Shared Cache
       Redis
      /  |  \
     /   |   \
 App1 App2 App3
# All applications use the same cache.

# Benefits
# - Consistency
# - Scalability
# - Shared state

# 14. Cache Consistency
# Ensures cache and database contain the same data.
# Common Strategies
# Write-Through
Cache + DB updated together
# Cache Invalidate
Update DB
Delete Cache
# Refresh Cache
Update DB
Update Cache
# Trade-off:
Strong Consistency  <----->  Performance

# 15. Cache Stampede
# Occurs when many requests hit an expired cache entry simultaneously.
# Scenario
Cache Expires

1000 Requests Arrive

All Hit DB

DB Overloaded

# Solutions
# 1. Mutex Lock
# Only one request rebuilds cache.
Request 1 -> DB
Request 2 -> Wait
Request 3 -> Wait

# 2. Randomized TTL
# Instead of: All keys expire at 10 PM
# Use:
Key1 -> 9:58 PM
Key2 -> 10:01 PM
Key3 -> 10:03 PM

# 3. Cache Warming
# Preload cache before expiration.

# 4. Background Refresh
# Refresh cache before TTL ends.

# Interview Questions
# Q1. Difference between @Cacheable and @CachePut?
# @Cacheable -> Uses cache if available
@CachePut  -> Always executes method and updates cache

# Q2. Difference between Cache-Aside and Read-Through?
# Cache-Aside:
Application manages cache.

Read-Through:
Cache manages data loading.

# Q3. Why Redis for caching?
- In-memory
- Extremely fast
- Distributed
- Supports TTL
- Scalable

# Q4. What causes cache stampede?
# Many requests hitting DB simultaneously after cache expiry.

# Q5. Difference between Write-Through and Write-Behind?
# Write-Through:
Cache + DB updated immediately.

Write-Behind:
Cache updated immediately,
DB updated later asynchronously.

# Quick Revision
Cache-Aside     -> App loads cache
Read-Through    -> Cache loads DB
Write-Through   -> Cache + DB write together
Write-Behind    -> Cache first, DB later

@Cacheable      -> Read and cache
@CachePut       -> Update cache
@CacheEvict     -> Remove cache

TTL             -> Expiration time
LRU             -> Evict least recently used
Redis           -> Distributed in-memory cache
Stampede        -> Many requests after expiry

