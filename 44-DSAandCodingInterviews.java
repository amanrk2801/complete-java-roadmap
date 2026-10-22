# Data Structures
1. Arrays and strings
2. Linked lists
3. Stacks and queues
4. Hash maps and hash sets
5. Trees and binary search trees
6. Heaps and priority queues
7. Graphs
8. Tries
9. Union-Find / Disjoint Set Union

# Algorithms
Big-O analysis
Sorting
Binary search
Two pointers
Sliding window
Prefix sums
Fast and slow pointers
Recursion and backtracking
Tree traversal
Graph BFS and DFS
Topological sorting
Greedy algorithms
Dynamic programming
Shortest path algorithms
Monotonic stacks and queues
Intervals
Bit manipulation

# Recommended Learning Order
1. Big-O
2. Arrays & Strings
3. HashMap/HashSet
4. Linked List
5. Stack
6. Queue
7. Binary Search
8. Two Pointers
9. Sliding Window
10. Prefix Sum
11. Fast & Slow Pointers
12. Recursion
13. Backtracking
14. Trees
15. Heaps
16. Graphs
17. BFS
18. DFS
19. Topological Sort
20. Greedy
21. Intervals
22. Trie
23. Union Find
24. Shortest Paths
25. Dynamic Programming
26. Bit Manipulation
27. Monotonic Stack/Queue

# Interview Preparation Target
Solve 20-30 problems on Arrays
Solve 15-20 problems on Linked List
Solve 20-30 problems on Trees
Solve 20-30 problems on Graphs
Solve 30-40 problems on Dynamic Programming

# Java Interview-Specific Topics
# 1. String Pool
# What is it?
# - The String Pool is a special memory area in the JVM where string literals are stored.
String s1 = "Java";
String s2 = "Java";

System.out.println(s1 == s2); // true
# Both variables point to the same object in the String Pool.

# Using new
String s1 = new String("Java");
String s2 = new String("Java");

System.out.println(s1 == s2); // false
# Creates separate objects in heap memory.

# intern()
String s1 = new String("Java");
String s2 = s1.intern();

System.out.println(s2 == "Java"); // true
# Interview Question: Why are Strings immutable?
# - Security
# - Thread safety
# - String Pool optimization
# - Hash code caching

# 2. HashMap Internals
# Structure
# Java 8+
HashMap 
│ 
├── Array (Buckets) 
│ 
├── Linked List 
│ 
└── Red-Black Tree

# Working
# 1. Calculate hashcode
# 2. Determine bucket index
# 3. Store entry
# 4. Handle collision
map.put("A", 100);
# Collision Handling
# Before Java 8:
Bucket -> Linked List
# Java 8 onwards:
Bucket -> Linked List -> Red Black Tree
# Tree conversion occurs:
# - Bucket size > 8
# - Capacity >= 64
# Complexity
Operation | Average
Put       | O(1)
Get       | O(1)
Remove    | O(1)
Worst Case| O(log n)

# 3. equals() and hashCode()
# Contract
# If: a.equals(b) == true
# Then: a.hashCode() == b.hashCode()
# must be true.
# Example
class Employee {
    int id;

    @Override
    public boolean equals(Object o) {
        Employee e = (Employee) o;
        return this.id == e.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

# Why Important?
# Required for: HashMap, HashSet, Hashtable

# 4. ArrayList vs LinkedList
Feature         |   ArrayList   |   LinkedList
Structure       | Dynamic Array |   Doubly Linked List
Random Access   | O(1)          |   O(n)
Insert Middle   | O(n)          |   O(1)
Memory          | Less          |   More
Search          | Faster        |   Slower

# Interview Answer
# Use:
# - ArrayList → frequent reads
# - LinkedList → frequent insertions/deletions

# 5. HashMap vs ConcurrentHashMap
Feature     |   HashMap    | ConcurrentHashMap
Thread Safe |   No         | Yes
Null Key    |   1 allowed  | Not allowed
Null Value  |   Allowed    | Not allowed
Locking     |   No         | Fine-grained locking

ConcurrentHashMap<String,Integer> map =
      new ConcurrentHashMap<>();

# Why Better?
# - Multiple threads can read/write simultaneously.
# - Better performance than Hashtable.

# 6. Interface vs Abstract Class
Interface                   |   Abstract Class
Multiple inheritance        |   Single inheritance
No constructor              |   Constructor allowed
100% abstraction (old Java) |   Partial abstraction
Implements                  |   Extends

# Interface
interface Payment {
    void pay();
}

# Abstract Class
abstract class Payment {
    abstract void pay();
}

# When to Use?
# - Interface → capability/contract
# - Abstract Class → common behavior/state

# 7. Overloading vs Overriding
# Overloading: Same method name, different parameters.
void add(int a,int b){}
void add(int a,int b,int c){}
# - Compile-time polymorphism.

# Overriding
class Animal {
    void sound() {}
}

class Dog extends Animal {
    @Override
    void sound() {}
}
# - Runtime polymorphism.

# 8. Checked vs Unchecked Exceptions
# Checked: Checked at compile time.
IOException
SQLException
# Must handle:
try {
}
catch(IOException e){
}

# Unchecked
# Runtime exceptions.
NullPointerException
ArithmeticException
ArrayIndexOutOfBoundsException
# - Not mandatory to handle.

# 9. final vs finally vs finalize
# final
final int x = 10;
# - Cannot change.

# finally
try {
}
finally {
   System.out.println("Always executes");
}
# - Always executes.

# finalize()
protected void finalize() {}
# - Called before GC.
# - ⚠️ Deprecated and should not be used.

# 10. synchronized vs volatile
# synchronized
# Provides:
# - Mutual exclusion
# - Visibility
synchronized void increment() {
    count++;
}
# - Only one thread at a time.

# volatile
volatile boolean running = true;
# Provides:
# - Visibility
# - Not atomicity
# Good for status flags.

# 11. Stream API
# Introduced in Java 8.
# Example
List<Integer> nums =
    Arrays.asList(1,2,3,4,5);

nums.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n*n)
    .forEach(System.out::println);

# Common Operations
# Intermediate:
# - filter()
# - map()
# - sorted()
# Terminal:
# - collect()
# - forEach()
# - reduce()

# 12. Generics and Type Erasure
# Generics
List<String> list = new ArrayList<>();
# - Compile-time type safety.
# Type Erasure
# - After compilation:
List<String>
# becomes
List
# - Generic type information is removed.
# Limitation
new T(); // Not allowed

# 13. JVM Memory and Garbage Collection
# JVM Memory
Heap 
├─ Young Generation 
├─ Old Generation 

Stack 
Metaspace 
PC Register 
Native Method Stack

# Garbage Collection
# - Removes unreachable objects.
# Example:
Employee e = new Employee();
e = null;
# Object becomes eligible for GC.
# Major Collectors
# - Serial GC
# - Parallel GC
# - G1 GC
# - ZGC
# - Shenandoah

# 14. SOLID Principles
# S - Single Responsibility
# One class → one responsibility.

# O - Open/Closed
# Open for extension, closed for modification.

# L - Liskov Substitution
# Subclass should replace parent safely.

# I - Interface Segregation
# Small interfaces are better.

# D - Dependency Inversion
# Depend on abstractions.
interface Payment {}
# not
CreditCardPayment

# 15. Spring Bean Lifecycle
# Sequence
Instantiate Bean 
↓ 
Dependency Injection 
↓ 
@PostConstruct 
↓ 
Bean Ready 
↓ 
@PreDestroy 
↓ 
Destroy

# Example:
@PostConstruct
public void init(){}

@PreDestroy
public void destroy(){}

# 16. Dependency Injection (DI)
# Without DI
Service service = new Service();

# With DI
@Autowired
Service service;

# Benefits:
# - Loose coupling
# - Easy testing
# - Better maintainability

# Types
# - Constructor Injection ✅
# - Setter Injection
# - Field Injection

# 17. JPA N+1 Problem
# Problem
List<Department> departments =
    repo.findAll();
# 1 query for departments
# Then:
department.getEmployees();
# executes N more queries.
1 + N Queries

# Solution
@Query("""
SELECT d
FROM Department d
JOIN FETCH d.employees
""")
# Use:
# - Fetch Join
# - EntityGraph
# - Batch Fetching

# 18. Transaction Propagation
# Common types:
# REQUIRED (Default)
# - Uses existing transaction or creates new.
@Transactional
# REQUIRES_NEW
# - Suspends current transaction and starts new.
# SUPPORTS
# - Uses transaction if available.
# MANDATORY
# - Must already have transaction.
# NEVER
# - Fails if transaction exists.

# 19. JWT Authentication
# Flow
User Login 
↓ 
Validate Credentials 
↓ 
Generate JWT 
↓ 
Client Stores JWT 
↓ 
Send JWT in Header 
↓ 
Validate JWT 
↓ 
Access Resource

# Example Header: Authorization: Bearer jwt-token

# Contains
# - Header
# - Payload
# - Signature

# Benefits
# - Stateless
# - Scalable
# - Fast authentication

# 20. Microservices Resilience
# Circuit Breaker
# - Prevents cascading failures.
# Popular: Resilience4j
# Retry
# - Retries failed requests.
@Retry
# Bulkhead
# - Isolates resources.
# Rate Limiter
# - Limits request count.
# Timeout
# - Stops waiting indefinitely.
@TimeLimiter

# Top 10 Most Frequently Asked Java Interview Questions
1. Explain HashMap internals.
2. Difference between HashMap and ConcurrentHashMap.
3. Difference between ArrayList and LinkedList.
4. Explain String Pool.
5. equals() vs hashCode().
6. synchronized vs volatile.
7. Interface vs Abstract Class.
8. Checked vs Unchecked Exceptions.
9. JVM Memory Structure and Garbage Collection.
10. Explain Dependency Injection in Spring.

# These 20 topics cover the majority of Java Core, Collections, Multithreading, JVM, Spring Boot, JPA, and Microservices questions asked in Java developer interviews.