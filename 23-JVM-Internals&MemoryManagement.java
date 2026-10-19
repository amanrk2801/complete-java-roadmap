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
# - 