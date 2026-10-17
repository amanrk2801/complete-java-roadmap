# 14. Generics (Java)
# Generics allow you to write classes, interfaces, and methods that work with different data types while providing compile-time type safety.

# 1. Why Generics Exist
# Before Generics (Java 5), collections stored objects as Object.
ArrayList list = new ArrayList();
list.add("Hello");

String s = (String) list.get(0);

# Problems:
# - Explicit casting required
# - Runtime ClassCastException
# - Less readable code

# With Generics:
ArrayList<String> list = new ArrayList<>();
list.add("Hello");

String s = list.get(0);

# Benefits: Type safety, No casting, Reusable code, Better readability

# 2. Generic Classes
# A class that works with any type.
class Box<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
# Usage:
Box<String> box = new Box<>();
box.set("Java");

System.out.println(box.get()); # Java

# 3. Generic Interfaces
# Interfaces can also use type parameters.
interface Storage<T> {
    void store(T item);
    T retrieve();
}
# Implementation:
class StringStorage implements Storage<String> {

    private String item;

    public void store(String item) {
        this.item = item;
    }

    public String retrieve() {
        return item;
    }
}

# 4. Generic Methods
# Method-level generics.
class Utility {

    public static <T> void print(T value) {
        System.out.println(value);
    }
}
# Usage:
Utility.print("Hello");
Utility.print(100);
Utility.print(3.14);

# 5. Type Parameters
# Common naming conventions:
| Symbol | Meaning |
| ------ | ------- |
| T      | Type    |
| E      | Element |
| K      | Key     |
| V      | Value   |
| N      | Number  |
| R      | Result  |

# Example:
class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

# 6. Bounded Type Parameters
# Restrict acceptable types.
class Calculator<T extends Number> {

    T value;

    Calculator(T value) {
        this.value = value;
    }

    double square() {
        return value.doubleValue() * value.doubleValue();
    }
}

# Valid:
Calculator<Integer> c1 = new Calculator<>(10);
Calculator<Double> c2 = new Calculator<>(4.5);

# Invalid:
Calculator<String> c = new Calculator<>("abc");

# 7. Multiple Bounds
# A type can extend one class and implement multiple interfaces.
class Example<T extends Number & Comparable<T>> {

}
# Rules: Class first, Interfaces after
# ✅ <T extends Number & Comparable<T>>
# ❌ <T extends Comparable<T> & Number>

# 8. Wildcards
# Represent unknown type.
List<?> list;
# Can point to:
List<String>
List<Integer>
List<Double>
# Example: List<?> list = new ArrayList<String>();

# 9. Upper-Bounded Wildcards
# Syntax: <? extends Type>
# Example:
public static void printNumbers(
        List<? extends Number> list) {

    for(Number n : list) {
        System.out.println(n);
    }
}
# Usage:
List<Integer> nums = List.of(1,2,3);

printNumbers(nums);
# Can read safely.
# Cannot add elements (except null).
list.add(10); // Error

# 10. Lower-Bounded Wildcards
# Syntax: <? super Type>
# Example:
public static void addNumbers(
        List<? super Integer> list) {

    list.add(10);
    list.add(20);
}
# Valid:
List<Integer> list1 = new ArrayList<>();
List<Number> list2 = new ArrayList<>();
List<Object> list3 = new ArrayList<>();
# Lower bound is mainly for writing.

# 11. Unbounded Wildcards
# Syntax: List<?>
# Example:
public static void display(List<?> list){

    for(Object obj : list){
        System.out.println(obj);
    }
}
# Use when type doesn't matter.

# 12. PECS: Producer Extends, Consumer Super
# A famous rule.
# Producer → Extends
# If data is read from collection:
List<? extends Number>
# Example:
double sum(List<? extends Number> list)
# You are producing Numbers.

# Consumer → Super
# If data is inserted into collection:
List<? super Integer>
# Example:
void addData(List<? super Integer> list)
# You are consuming Integers.

# Memory Trick
PECS 
Producer = Extends 
Consumer = Super

# 13. Type Inference
# Compiler determines type automatically.
# Java 7+ Diamond Operator:
List<String> list = new ArrayList<>();
# Instead of: List<String> list =
    new ArrayList<String>();
# Generic Method:
public static <T> T identity(T value) {
    return value;
}

String s = identity("Java");
# Compiler infers T = String.

# 14. Generic Constructors
# Constructor can have its own type parameter.
class Demo {

    <T> Demo(T value) {
        System.out.println(value);
    }
}
# Usage:
new Demo("Java");
new Demo(100);
# Output:
Java 
100

# 15. Type Erasure
# Generics exist only at compile time.
List<String> names =
        new ArrayList<>();

List<Integer> nums =
        new ArrayList<>();
# After compilation:
List names;
List nums;
# Type information is erased.
# Implications:
if(list instanceof List<?>) {
    System.out.println("Yes");
}
# Allowed.
if(list instanceof List<String>)
# Not allowed.

# 16. Raw Types
# Using generic types without parameters.
List list = new ArrayList();
# Example:
List list = new ArrayList();

list.add("Hello");
list.add(100);
# Problems:
No type checking
Unsafe
Compilers give warnings
# Avoid raw types in modern Java.

# 17. Generic Restrictions
# Cannot create generic arrays
❌ : T[] arr = new T[10];
# Cannot instantiate type parameter
❌ : T obj = new T();
# Cannot use primitives
❌ : List<int>
✅ : List<Integer>
# Cannot use static type parameter
❌ : class Test<T> {
    static T value;
}
# Cannot catch generic exceptions
❌ : catch(T e)

# 18. Generic Inheritance
# Example:
class Animal {}

class Dog extends Animal {}

# Even though: 
Dog extends Animal

# The following is NOT true:
List<Dog> dogs = new ArrayList<>();

List<Animal> animals = dogs; // Error
# Reason:
# Generics are invariant.
# Use wildcard:
List<? extends Animal> animals = dogs;

# 19. Bridge Methods
# Bridge methods are compiler-generated methods used to maintain polymorphism after type erasure.
# Example:
class Parent<T> {

    T get() {
        return null;
    }
}
class Child extends Parent<String> {

    @Override
    String get() {
        return "Java";
    }
}
# After type erasure:
class Parent {
    Object get() {
        return null;
    }
}
class Child {

    String get() {
        return "Java";
    }

    // Compiler-generated bridge method
    Object get() {
        return get();
    }
}
# This ensures runtime polymorphism works correctly.

# Quick Revision
Generics
│
├── Generic Classes
├── Generic Interfaces
├── Generic Methods
├── Type Parameters
├── Bounded Types
├── Multiple Bounds
├── Wildcards
│   ├── ?
│   ├── ? extends T
│   └── ? super T
├── PECS
├── Type Inference
├── Generic Constructors
├── Type Erasure
├── Raw Types
├── Restrictions
├── Generic Inheritance
└── Bridge Methods

# Interview Questions
What problem do Generics solve?
Difference between T and ?.
Explain PECS.
What is Type Erasure?
Why is List<Dog> not a subtype of List<Animal>?
Difference between extends and super wildcards.
What are raw types?
Why can't we create new T()?
What are bridge methods?
Explain bounded type parameters with examples.


