# Object-Oriented Programming (OOP)
# Classes and Objects (Java)
# - Classes and Objects are the foundation of Object-Oriented Programming (OOP) in Java.

# 1. Classes and Objects
# Class: A class is a blueprint or template from which objects are created.
# Syntax
class Student {
    int id;
    String name;
}
# Object: An object is an instance of a class.
# Example
class Student {
    int id;
    String name;
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();

        s1.id = 101;
        s1.name = "Aman";

        System.out.println(s1.id);
        System.out.println(s1.name);
    }
}
# Output: 101 Aman

# 2. Fields and Methods
# Fields (Variables)
# Fields store data of an object.
class Student {
    int id;
    String name;
}
# Methods: Methods define behavior of an object.
class Student {
    int id;
    String name;

    void display() {
        System.out.println(id + " " + name);
    }
}
# Example
class Student {
    int id;
    String name;

    void display() {
        System.out.println(id + " " + name);
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student();

        s.id = 101;
        s.name = "Aman";

        s.display();
    }
}

# Output: 101 Aman

# 3. Object Creation with new
# - The new keyword allocates memory and creates an object.
Student s = new Student();
# Working
# 1. Memory allocated.
# 2. Constructor called.
# 3. Object reference returned.
Student s1 = new Student();
Student s2 = new Student();
# Each object has its own copy of instance variables.

# 4. Constructors
# A constructor is a special method used to initialize objects.
# Rules
# - Constructor name = class name.
# - No return type.
# - Automatically called when object is created.

# Example
class Student {
    Student() {
        System.out.println("Constructor Called");
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
    }
}

# Output: Constructor Called

# 5. Default and Parameterized Constructors
# Default Constructor
# No parameters.
class Student {
    Student() {
        System.out.println("Default Constructor");
    }
}
# Example
Student s = new Student();

# Parameterized Constructor
# Accepts parameters.
class Student {
    int id;
    String name;

    Student(int i, String n) {
        id = i;
        name = n;
    }

    void display() {
        System.out.println(id + " " + name);
    }
}
public class Main {
    public static void main(String[] args) {
        Student s = new Student(101, "Aman");
        s.display();
    }
}
# Output: 101 Aman

# 6. Constructor Overloading
# - Multiple constructors with different parameter lists.
class Student {
    Student() {
        System.out.println("No Parameter");
    }

    Student(int id) {
        System.out.println("ID: " + id);
    }

    Student(int id, String name) {
        System.out.println(id + " " + name);
    }
}
public class Main {
    public static void main(String[] args) {

        Student s1 = new Student();
        Student s2 = new Student(101);
        Student s3 = new Student(101, "Aman");
    }
}
# Output
No Parameter 
ID: 101 
101 Aman

# 7. Constructor Chaining with this()
# - One constructor calls another constructor in the same class using this().
# Example
class Student {

    Student() {
        this(101);
        System.out.println("Default Constructor");
    }

    Student(int id) {
        System.out.println("ID = " + id);
    }
}
public class Main {
    public static void main(String[] args) {
        Student s = new Student();
    }
}
# Output
ID = 101 
Default Constructor

# Rules
# - this() must be the first statement.
# - Used for constructor reuse.

# 8. this Keyword
# - this refers to the current object.
# Example
class Student {
    int id;

    Student(int id) {
        this.id = id;
    }

    void show() {
        System.out.println(this.id);
    }
}
public class Main {
    public static void main(String[] args) {
        Student s = new Student(101);
        s.show();
    }
}
# Output: 101

# Uses of this
# 1. Refer current object variable.
# 2. Invoke current class method.
# 3. Invoke current class constructor (this()).
# 4. Pass current object as argument.
# 5. Return current object.

# 9. Object Initialization Order
# When an object is created:
Student s = new Student();
# Execution order:
# 1. Instance variables initialized.
# 2. Instance initializer block executes.
# 3. Constructor executes.

# Example
class Student {

    int x = 10;

    {
        System.out.println("Initializer Block");
    }

    Student() {
        System.out.println("Constructor");
    }
}
public class Main {
    public static void main(String[] args) {
        Student s = new Student();
    }
}
# Output
Initializer Block 
Constructor

# 10. Instance Initializer Blocks
# Executed whenever an object is created.
# Example
class Student {

    {
        System.out.println("Instance Block");
    }

    Student() {
        System.out.println("Constructor");
    }
}
public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();
        Student s2 = new Student();
    }
}
# Output
Instance Block 
Constructor 
Instance Block 
Constructor

# Use: Common initialization code for all constructors.

# 11. Static Initializer Blocks
# Executed only once when the class loads.
# Example
class Student {

    static {
        System.out.println("Static Block");
    }

    Student() {
        System.out.println("Constructor");
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();
        Student s2 = new Student();
    }
}

# Output
Static Block 
Constructor 
Constructor

# Characteristics
# - Runs only once.
# - Executes before main().
# - Used for static variable initialization.

# Complete Initialization Order in Java
class Demo {

    static {
        System.out.println("1. Static Block");
    }

    int x = 10;

    {
        System.out.println("2. Instance Block");
    }

    Demo() {
        System.out.println("3. Constructor");
    }

    public static void main(String[] args) {
        Demo d = new Demo();
    }
}

# Output
1. Static Block 
2. Instance Block 
3. Constructor

# Final Order
Class Loading 
↓ 
Static Variables 
↓ 
Static Blocks 
↓ 
Object Creation 
↓ 
Instance Variables 
↓ 
Instance Initializer Blocks 
↓ 
Constructor

# Interview Questions
# Q1. Difference between Class and Object?
# Class → Blueprint.
# Object → Instance of class.

# Q2. Can a constructor be private?
# Yes.
class Test {
    private Test() {
    }
}
# Used in Singleton Design Pattern.

# Q3. Can a constructor be inherited?
# No

# Q4. Can we overload constructors?
# Yes

# Q5. Can we override constructors?
# No, constructors are not inherited.

# Q6. What is the purpose of this()?
# To call another constructor of the same class.

# Q7. How many times does a static block execute?
# Only once when the class is loaded.

# Q8. How many times does an instance block execute?
# Every time an object is created.

# ---------------------------------------------- # 

# Four Pillars of OOP
1. Encapsulation
2. Abstraction
3. Inheritance
4. Polymorphism

# OOP is based on four main principles that help in writing reusable, secure, and maintainable code.

# 1. Encapsulation
# Definition: Encapsulation means binding data (variables) and methods (functions) together inside a class and restricting direct access to some data.
# Purpose: Protect data from accidental modification.
# Example
class BankAccount:
    def __init__(self, balance):
        self.__balance = balance  # private variable

    def deposit(self, amount):
        self.__balance += amount

    def get_balance(self):
        return self.__balance


acc = BankAccount(1000)
acc.deposit(500)
print(acc.get_balance())   # 1500

# Key Point: __balance cannot be accessed directly from outside the class.

# 2. Abstraction
# Definition: Abstraction means showing only essential features and hiding implementation details.
# Purpose: Reduce complexity and improve usability.
# Example
from abc import ABC, abstractmethod

class Vehicle(ABC):

    @abstractmethod
    def start(self):
        pass


class Car(Vehicle):
    def start(self):
        print("Car starts with a key")


c = Car()
c.start()

# Key Point: User knows what a car does (start()), not how it works internally.

# 3. Inheritance
# Definition: Inheritance allows a class to acquire properties and methods of another class.
# Purpose: Code reusability.
# Example
class Animal:
    def eat(self):
        print("Animal is eating")


class Dog(Animal):
    def bark(self):
        print("Dog is barking")


d = Dog()
d.eat()     # inherited method
d.bark()

# Key Point: Dog inherits the eat() method from Animal.

# 4. Polymorphism
# Definition: Polymorphism means one interface, many forms. The same method name behaves differently for different objects.
# Purpose: Flexibility and extensibility.
# Example
class Dog:
    def sound(self):
        print("Bark")


class Cat:
    def sound(self):
        print("Meow")


animals = [Dog(), Cat()]

for animal in animals:
    animal.sound()

# Output
Bark 
Meow
# Key Point: The same method sound() produces different behavior based on the object.

# Quick Summary
| Pillar        | Meaning                                 |
| ------------- | --------------------------------------- |
| Encapsulation | Hiding and protecting data              |
| Abstraction   | Hiding implementation details           |
| Inheritance   | Acquiring properties from another class |
| Polymorphism  | Same method, different behavior         |

# Real-Life Car Example
# 1. Encapsulation: Car's engine data is hidden inside the car.
# 2. Abstraction: Driver uses the steering wheel and pedals without knowing engine internals.
# 3. Inheritance: ElectricCar inherits features from Car.
# 4. Polymorphism: Different cars implement start() differently (key, button, voice command).
# Memory Tip: 👉 E-A-I-P = Encapsulation → Abstraction → Inheritance → Polymorphism.

# --------------------------------------------- #

# Java Advanced OOP
# 1. Single Inheritance
# - One child class inherits from one parent class.
class Animal {
    void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Barking");
    }
}
Dog d = new Dog();
d.eat();
d.bark();

# 2. Multilevel Inheritance
# - A class inherits from another derived class.
class A {
    void showA() {}
}

class B extends A {
    void showB() {}
}

class C extends B {
    void showC() {}
}
# Hierarchy:
A 
| 
B 
| 
C

# 3. Hierarchical Inheritance
# - Multiple classes inherit from one parent.
class Animal {
    void eat() {}
}

class Dog extends Animal {}
class Cat extends Animal {}

    Animal 
    /   \ 
  Dog   Cat

# 4. Why Classes Don't Support Multiple Inheritance
# - Java avoids multiple inheritance of classes because of the Diamond Problem.
class A {
    void show() {}
}

class B extends A {}
class C extends A {}

// Not Allowed
class D extends B, C {}

# If both B and C have show(), Java cannot determine which one D should use.

# 5. Interfaces and Multiple Interface Inheritance
# - Java allows multiple inheritance through interfaces.
interface A {
    void display();
}

interface B {
    void show();
}

class Test implements A, B {
    public void display() {}
    public void show() {}
}

# 6. Method Overriding
# - Child class provides its own version of a parent method.
class Animal {
    void sound() {
        System.out.println("Animal Sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Bark");
    }
}

# 7. Dynamic Method Dispatch
# - Method call resolved at runtime.
Animal a = new Dog();
a.sound();
# Output: Bark
# - Runtime decides which method executes.

# 8. Compile-Time vs Runtime Polymorphism
# - Compile-Time Polymorphism
# Method Overloading
class Calc {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}

# Runtime Polymorphism
# - Method Overriding
Animal a = new Dog();
a.sound();

# 9. Abstract Classes
# - Cannot create objects directly.
abstract class Shape {
    abstract void draw();
}
Shape s = new Shape(); // Error

# 10. Abstract Methods
# - Methods without implementation.
abstract class Shape {
    abstract void draw();
}
# Child class must implement.
class Circle extends Shape {
    void draw() {
        System.out.println("Circle");
    }
}

# 11. Interfaces
# - Contain abstract methods and constants.
interface Vehicle {
    void start();
}
class Car implements Vehicle {
    public void start() {
        System.out.println("Car Started");
    }
}

# 12. Default and Static Interface Methods
# Default Method
interface Test {
    default void show() {
        System.out.println("Default Method");
    }
}

# Static Method
interface Test {
    static void display() {
        System.out.println("Static Method");
    }
}

# Usage: Test.display();

# 13. Private Interface Methods
# - Used internally within an interface.
interface Demo {

    private void message() {
        System.out.println("Private");
    }

    default void show() {
        message();
    }
}
# Available from Java 9.

# 14. Composition vs Inheritance
# Inheritance (IS-A)
class Dog extends Animal {}
# Dog IS-A Animal.

# Composition (HAS-A)
class Engine {}

class Car {
    Engine e = new Engine();
}
# Car HAS-A Engine.
# - Composition is generally preferred for flexibility.

# 15. Association, Aggregation, Composition
# Association
# - Two independent objects related.
# Teacher ---- Student

# Aggregation
# - Weak HAS-A relationship.
class Department {
    List<Teacher> teachers;
}
# Teachers can exist without Department.

# Composition
# - Strong HAS-A relationship.
class House {
    Room room = new Room();
}
# Room cannot exist independently.

# 16. Coupling and Cohesion
# Coupling
# Dependency between classes.
# - Low Coupling ✅
# - High Coupling ❌

# Cohesion
# How closely related responsibilities are.
# - High Cohesion ✅
# - Low Cohesion ❌

# Example:
class Calculator {
    int add(int a, int b) {
        return a + b;
    }
}
# Single responsibility = high cohesion.

# 17. Immutable Classes
# - Objects cannot be modified after creation.
final class Employee {

    private final int id;
    private final String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

# Features:
# - Class final
# - Variables final
# - No setters

# 18. Object Cloning
# - Create a copy of an object.
class Student implements Cloneable {

    int id;

    protected Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }
}
Student s2 = (Student)s1.clone();

# 19. Object Class Methods
# - Every class inherits from Object.
# - Important methods:
toString()
equals()
hashCode()
clone()
getClass()
wait()
notify()
notifyAll()

# Example:
Object obj = new Object();
System.out.println(obj.getClass());

# 20. equals(), hashCode(), toString()
# equals(): Compares object contents.
@Override
public boolean equals(Object obj) {
    return true;
}

# hashCode(): Returns hash value.
@Override
public int hashCode() {
    return id;
}

# Rule: equal objects → same hashCode

# toString(): String representation.
@Override
public String toString() {
    return name;
}

# 21. Covariant Return Types
# - Overridden method can return a subclass type.
class Animal {}

class Dog extends Animal {}

class A {
    Animal getObject() {
        return new Animal();
    }
}

class B extends A {
    @Override
    Dog getObject() {
        return new Dog();
    }
}

# Dog is a subclass of Animal, so this is valid.

# Quick Interview Questions
1. What is the Diamond Problem?
2. Difference between abstract class and interface?
3. What is dynamic method dispatch?
4. Why override hashCode() when overriding equals()?
5. Composition vs inheritance?
6. What is an immutable class?
7. What is covariant return type?
8. Difference between aggregation and composition?
9. What is runtime polymorphism?
10. Can an interface have methods with bodies?

