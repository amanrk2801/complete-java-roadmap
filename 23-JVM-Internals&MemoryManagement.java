# JVM Internals and Memory Management
# JVM (Java Virtual Machine) is the runtime environment that executes Java bytecode. Understanding JVM internals is crucial for Java developers, especially for performance tuning, debugging, and memory optimization.

# 1. JVM Architecture
# The JVM consists of several major components:
+-----------------------+
|      Class Loader     |
+-----------------------+
           |
           v
+-----------------------+
| Runtime Data Areas    |
| Heap, Stack, PC, etc. |
+-----------------------+
           |
           v
+-----------------------+
| Execution Engine      |
| Interpreter + JIT     |
+-----------------------+
           |
           v
+-----------------------+
| Native Method Interface|
+-----------------------+
           |
           v
+-----------------------+
| Native Libraries      |
+-----------------------+

# Main Components:
# 1. Class Loader Subsystem
# 2. Runtime Memory Areas
# 3. Execution Engine
# 4. Native Interface (JNI)
# 5. Native Libraries

# 2. Class Loading
# Class loading is the process of bringing a .class file into JVM memory and creating a corresponding Class object.
# Example: Student s = new Student();
# Before creating the object, JVM loads Student.class.

# 3. Loading, Linking, Initialization
# Class loading occurs in three phases.

# A. Loading
# JVM reads bytecode and creates a Class object.
Class<?> c = Student.class;

# B. Linking
# Contains three steps:
# Verification
# Checks:
# - Correct bytecode format
# - Type safety
# - Access restrictions
# Preparation
# Allocates memory for static variables.
static int count;
# Memory allocated and initialized to:
count = 0;
# Resolution
# Symbolic references replaced with direct references.

# C. Initialization
# Static blocks and static variable assignments execute.
class Test {
    static int x = 10;

    static {
        System.out.println("Static Block");
    }
}
# Output: Static Block

# 4. Class Loaders
# Class loaders load class files into memory.
# Java follows Parent Delegation Model.
Application Loader
        |
Platform Loader
        |
Bootstrap Loader
# Request goes upward first.

# 5. Bootstrap, Platform, and Application Class Loaders
# Bootstrap Class Loader
# Loads core Java classes.
# Examples:
java.lang.String
java.util.ArrayList
# Location: <JAVA_HOME>/lib

# Platform Class Loader
# Loads platform modules.
# Examples:
java.sql
java.xml

# Application Class Loader
# Loads classes from: classpath
# Example: MyApplication.class

# 6. Bytecode
# Java source:
public class Demo {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
# Compiled: javac Demo.java
# Produces: Demo.class
# Bytecode can be viewed: javap -c Demo
# Sample:
0: getstatic
3: ldc
5: invokevirtual

# 7. Bytecode Verification
# Ensures uploaded bytecode is safe before execution.
# Checks:
# - Illegal memory access
# - Stack underflow
# - Type mismatches
# - Invalid instructions
# This improves JVM security.

# 8. Execution Engine
# Responsible for running bytecode.
# Contains:
# 1. Interpreter
# 2. JIT Compiler
# 3. Garbage Collector
Bytecode
    |
Interpreter
    |
JIT Compiler
    |
Machine Code

# 9. Interpreter
# Reads and executes bytecode line by line.
# Advantages: Fast startup
# Disadvantages: Slower execution
Bytecode -> Execute -> Bytecode -> Execute

# 10. JIT Compilation
# JIT = Just-In-Time Compiler
# Frequently used code is compiled into native machine code.
Bytecode
   |
Interpreter
   |
Hot Code
   |
JIT
   |
Native Machine Code
# Example:
for(int i=0;i<1000000;i++) {
    sum += i;
}
# Loops become optimized native instructions.
# Benefits:
# - Better performance
# - Method inlining
# - Dead code removal

# 11. HotSpot JVM
# Most popular JVM implementation.
# Features:
# - Adaptive optimization
# - Dynamic compilation
# - Efficient garbage collection
# HotSpot identifies "hot methods" and optimizes them.

# 12. Runtime Data Areas
+------------------+
| Method Area      |
+------------------+
| Heap             |
+------------------+
| JVM Stack        |
+------------------+
| PC Register      |
+------------------+
| Native Stack     |
+------------------+
# Some areas are shared.
# Some are per-thread.

# 13. Heap
# Stores:
new Object()
new ArrayList()
new String()
# Example: 
Student s = new Student();
s -> Heap Object
# Shared by all threads.
# Largest JVM memory area.

# 14. Thread Stacks
# Each thread has its own stack.
# Stores:
# - Local variables
# - Method calls
# - Return addresses
# Example:
public void m() {
    int x = 10;
}
# x lives inside stack frame.

# 15. Program Counter (PC) Register
# Each thread has a PC register.
# Stores: Current instruction address
# Used to resume execution after context switching.

# 16. Native Method Stacks
# Used for native code execution.
# Example:
System.loadLibrary("myLib");
# C/C++ code executes using native stacks.

# 17. Metaspace
# Introduced in Java 8.
# Replaced: PermGen
# Stores:
# - Class metadata
# - Method metadata
# - Constant pools
# Located in native memory.
# Benefits:
# - Dynamic growth
# - Reduced OutOfMemory issues

# 18. Garbage Collection (GC)
# Automatically removes unused objects.
# Example:
Student s = new Student();
s = null;
# Object becomes eligible for GC.

# 19. GC Roots
# GC starts searching from roots.
# Examples:
Active Threads
Static Variables
Local Variables
JNI References
# Objects unreachable from GC roots are collected.

# 20. Strong, Soft, Weak, Phantom References
# Strong Reference
Student s = new Student();
# GC never removes it.

# Soft Reference
SoftReference<Student> ref =
        new SoftReference<>(new Student());
# Collected only when memory is low.
# Used in caches.

# Weak Reference
WeakReference<Student> ref =
        new WeakReference<>(new Student());
# Collected during next GC cycle.

# Phantom Reference
PhantomReference<Student> ref =
        new PhantomReference<>(obj, queue);
# Used for advanced cleanup.
# Object already finalized.

# 21. Generational Garbage Collection
# Based on observation:
# - Most objects die young.
# Memory divided into:
Heap
 |
 +-- Young Generation
 |      |
 |      +-- Eden
 |      +-- Survivor S0
 |      +-- Survivor S1
 |
 +-- Old Generation
 # Minor GC
 # - Young Generation only.
 # Major GC
 # - Old Generation.
 # Full GC
 # - Entire heap.

 # 22. G1 GC (Garbage First)
 # Default collector in modern Java.
 # Heap divided into regions.
 +----+----+----+----+
| R1 | R2 | R3 | R4 |
+----+----+----+----+
# Features:
# - Predictable pauses
# - Parallel collection
# - Incremental cleanup
# Enable: -XX:+UseG1GC

# 23. ZGC and Other Collectors
# ZGC
# Ultra-low pause collector.
# Pause times: < 1 ms to a few ms
# Enable: -XX:+UseZGC

# Shenandoah
# - Low latency collector.
-XX:+UseShenandoahGC

# Serial GC
# Single-threaded.
-XX:+UseSerialGC

# Parallel GC
# High throughput.
-XX:+UseParallelGC

# 24. Memory Leaks in Java
# Java can still have memory leaks.
# Example:
List<Object> data = new ArrayList<>();

while(true){
    data.add(new Object());
}
# Objects remain referenced.
# Common causes:
# - Static collections
# - Unclosed resources
# - Listener registrations
# - ThreadLocal misuse

# 25. OutOfMemoryError
# Occurs when JVM cannot allocate memory.
# Example:
List<Object> list = new ArrayList<>();

while(true){
    list.add(new byte[1024 * 1024]);
}
# Error:
java.lang.OutOfMemoryError:
Java heap space
# Types:
# - Java Heap Space
# - Metaspace
# - Direct Buffer Memory
# - GC Overhead Limit Exceeded

# 26. StackOverflowError
# Caused by excessive recursion.
# Example:
public void recurse() {
    recurse();
}
# Output: java.lang.StackOverflowError
# Each call creates another stack frame.

# 27. Heap Dumps
# Snapshot of JVM heap.
# Generate:
jmap -dump:live,format=b,file=heap.hprof <pid>
# or
-XX:+HeapDumpOnOutOfMemoryError
# Analyze using:
# - Eclipse MAT
# - VisualVM
# - JMC
# Useful for finding:
# - Memory leaks
# - Large objects
# - Retained memory

# 28. Thread Dumps
# Snapshot of all thread states.
# Generate: jstack <pid>
# or
# kill -3 <pid>
# Shows:
RUNNABLE
WAITING
BLOCKED
TIMED_WAITING
# Useful for:
# - Deadlocks
# - Stuck threads
# - Performance debugging

# 29. JVM Flags
# - Common flags:
# Heap Size
-Xms512m
-Xmx2g

# Stack Size
-Xss1m

# GC Logging
-Xlog:gc*

# Heap Dump
-XX:+HeapDumpOnOutOfMemoryError

# 30. Java Flight Recorder (JFR)
# Built-in JVM profiler.
# Collects: CPU usage, Memory usage, GC events, Thread activity, Locks
# Start Recording:
java -XX:StartFlightRecording=filename=app.jfr

# 31. Java Mission Control (JMC)
# GUI tool for analyzing JFR files.
# Features:
# - CPU profiling
# - Memory analysis
# - GC analysis
# - Thread analysis
# Workflow:
Application
      |
      v
JFR Recording
      |
      v
JMC Analysis

# 32. JDK Diagnostic Tools
# jps
# List Java processes.
jps -l

# jstack
# Thread dump.
jstack <pid>

# jmap
# Heap information.
jmap -heap <pid>

# jstat
# GC statistics.
jstat -gc <pid>

# jcmd
# Most powerful diagnostic tool.
jcmd <pid> help

# Examples:
jcmd <pid> GC.heap_info
jcmd <pid> Thread.print
jcmd <pid> VM.flags

# Quick Interview Questions
# Q1. What is the difference between Heap and Stack?
# Heap: Stores objects and shared data.
# Stack: Stores method calls and local variables per thread.

# Q2. What replaced PermGen?
# Metaspace in Java 8.

# Q3. What is a GC Root?
# Starting point used by Garbage Collector to determine reachable objects.

# Q4. Difference between Minor GC and Full GC?
# Minor GC → Young Generation
# Full GC → Entire Heap

# Q5. What causes StackOverflowError?
# Excessive recursion or very deep method calls.

# Q6. What causes OutOfMemoryError?
# Insufficient memory in heap, metaspace, or native memory.

# Q7. What is JIT?
# A compiler that converts frequently executed bytecode into optimized native machine code during runtime.

# JVM Memory Summary
JVM
│
├── Heap
│   ├── Young Generation
│   │   ├── Eden
│   │   ├── Survivor S0
│   │   └── Survivor S1
│   └── Old Generation
│
├── Metaspace
├── Stack (Per Thread)
├── PC Register (Per Thread)
└── Native Method Stack (Per Thread)

# This covers the complete foundation of JVM Internals and Memory Management, one of the most important Java interview and advanced Java topics.