# Reflection and Annotations in Java
# Reflection and Annotations are advanced Java features widely used in frameworks such as Spring, Hibernate, JUnit, and Jakarta EE.

# 1. Reflection API
# The Reflection API allows a Java program to examine and manipulate classes, methods, fields, constructors, and objects at runtime.
# Package: import java.lang.reflect.*;
# Uses
# - Inspect class information
# - Access fields and methods dynamically
# - Create objects at runtime
# - Invoke methods dynamically
# - Framework development

# 2. Class Object
# - Every Java class has an associated Class object containing metadata about the class.
# Obtaining Class Object
# Method 1
Class<?> c = String.class;
# Method 2
String s = "Hello";
Class<?> c = s.getClass();
# Method 3
Class<?> c = Class.forName("java.lang.String");
# Example
Class<?> cls = String.class;

System.out.println(cls.getName());
System.out.println(cls.getSimpleName());

# Output:
java.lang.String 
String

# 3. Inspecting Fields and Methods
# Reflection can retrieve information about class members.
# Example
import java.lang.reflect.*;

class Student {
    private int id;

    public void show() {
        System.out.println("Student");
    }
}
Class<?> cls = Student.class;

Field[] fields = cls.getDeclaredFields();

for(Field f : fields) {
    System.out.println(f.getName());
}

Method[] methods = cls.getDeclaredMethods();

for(Method m : methods) {
    System.out.println(m.getName());
}

# Output:
id 
show

# 4. Creating Objects Through Reflection
# Objects can be created without using new.
# Example
class Employee {
    Employee() {
        System.out.println("Constructor Called");
    }
}
Class<?> cls = Employee.class;

Object obj = cls.getDeclaredConstructor().newInstance();
# Output: Constructor Called

# 5. Accessing Private Members
# Reflection can bypass access modifiers.
# Example
class Test {
    private String message = "Private Data";
}
Test t = new Test();

Field field = Test.class.getDeclaredField("message");

field.setAccessible(true);

System.out.println(field.get(t));
# Output: Private Data

# Modifying Private Field
field.set(t, "Changed");

System.out.println(field.get(t));
# Output: Changed

# 6. Reflection Limitations and Performance
# Advantages
# - Dynamic behavior
# - Framework development
# - Runtime inspection
# - Dependency injection
# Disadvantages
# - Slower than normal method calls
# - Breaks encapsulation
# - Security restrictions
# - Harder to debug
# - Reduced compile-time checking
# Example
# Direct method call: obj.show();
# Reflection call:
Method m = cls.getMethod("show");
m.invoke(obj);
# Reflection is significantly slower.

# 7. Built-in Annotations
# Java provides predefined annotations.
# @Override
@Override
public String toString() {
    return "Hello";
}
# @Deprecated
@Deprecated
public void oldMethod() {
}
# @SuppressWarnings
@SuppressWarnings("unchecked")
List list = new ArrayList();
# @FunctionalInterface
@FunctionalInterface
interface MyInterface {
    void display();
}

# 8. Creating Custom Annotations
# Syntax
@interface MyAnnotation {
}
# Example
@interface Author {
    String name();
}
# Usage:
@Author(name = "Aman")
class Book {
}

# 9. Annotation Targets
# @Target specifies where an annotation can be applied.
# Common Targets
| ElementType     | Meaning          |
| --------------- | ---------------- |
| TYPE            | Class, Interface |
| METHOD          | Method           |
| FIELD           | Variable         |
| CONSTRUCTOR     | Constructor      |
| PARAMETER       | Parameter        |
| LOCAL_VARIABLE  | Local Variable   |

# Example
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@interface TestMethod {
}
# Only methods can use this annotation.

# 10. Retention Policies
# @Retention determines how long an annotation is retained.
# RetentionPolicy.SOURCE
# Exists only in source code.
@Retention(RetentionPolicy.SOURCE)
# Example: @Override

# RetentionPolicy.CLASS
# Stored in .class file but unavailable at runtime.
@Retention(RetentionPolicy.CLASS)

# RetentionPolicy.RUNTIME
# Available during execution.
@Retention(RetentionPolicy.RUNTIME)
# Required for reflection-based processing.

# 11. Runtime Annotations
# Annotations available during execution can be read using Reflection.
# Example
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@interface Info {
    String value();
}
@Info("Student Class")
class Student {
}
Class<Student> cls = Student.class;

Info obj = cls.getAnnotation(Info.class);

System.out.println(obj.value());

# Output: Student Class

# 12. Repeatable Annotations
# Same annotation can appear multiple times on a declaration.
# Example
import java.lang.annotation.*;

@Repeatable(Roles.class)
@interface Role {
    String value();
}
# Container annotation:
@interface Roles {
    Role[] value();
}
# Usage:
@Role("Admin")
@Role("Manager")
class User {
}

# 13. Annotation Processing
# Annotation processing occurs during compilation.
# Used to:
# - Generate code
# - Validate annotations
# - Create configuration files
# - Build frameworks
# Example
# Lombok uses annotation processing:
@Getter
@Setter
class Employee {
    private String name;
}
# Compiler generates getter and setter methods automatically.

# 14. Reflection in Frameworks
# Many Java frameworks heavily rely on Reflection.
# Spring Framework
@Service
class UserService {
}
# Spring scans classes and creates objects automatically.
# Hibernate
@Entity
class Employee {
}
# Reflection maps objects to database tables.
# JUnit
@Test
public void testMethod() {
}
# JUnit discovers test methods through Reflection.

# 15. Dependency Injection Through Annotations
# Dependency Injection (DI) means objects are automatically provided by a container.
# Example (Spring)
@Component
class Engine {
}
@Component
class Car {

    @Autowired
    private Engine engine;

    public void drive() {
        System.out.println("Driving");
    }
}
# What Happens?
# 1. Spring scans annotations.
# 2. Reflection finds @Autowired.
# 3. Spring creates Engine.
# 4. Spring injects it into Car.
# Diagram:
Spring Container 
| 
v 
Engine 
| 
@Autowired 
| 
v 
Car

# Interview Questions
# Q1: What is Reflection in Java?
# Reflection is the ability to inspect and manipulate classes, methods, constructors, and fields at runtime.

# Q2: How do you get a Class object?
# Class<?> cls = ClassName.class;
# obj.getClass();
# Class.forName("ClassName");

# Q3: Why is Reflection slower?
# Because type checking and method resolution occur at runtime rather than compile time.

# Q4: Which retention policy is required for runtime access?
# RetentionPolicy.RUNTIME

# Q5: What is @Repeatable?
# It allows the same annotation to be applied multiple times to a single declaration.

# Q6: Why do frameworks use Reflection?
# To create objects dynamically, inject dependencies, discover annotations, and invoke methods at runtime.

# Quick Revision
- Reflection = Runtime inspection and manipulation.
- Class object stores metadata.
- getDeclaredFields() → fields.
- getDeclaredMethods() → methods.
- newInstance() / getDeclaredConstructor().newInstance() → create object.
- setAccessible(true) → access private members.
- Annotations add metadata.
- @Target defines where annotation is used.
- @Retention defines annotation lifetime.
- RUNTIME retention is needed for reflection.
- @Repeatable allows multiple annotations.
- Spring, Hibernate, and JUnit extensively use Reflection and Annotations.