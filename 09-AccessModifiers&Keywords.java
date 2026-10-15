# 9. Access Modifiers and Keywords in Java
# 1. public
# - Accessible from anywhere in the program.
public class Student {
    public String name;
}
# ✅ Can be accessed from any package.

# 2. protected
# Accessible:
# - Within the same class
# - Same package
# - Subclasses (even in different packages)
class Animal {
    protected void sound() {
        System.out.println("Animal Sound");
    }
}
# ✅ Used when inheritance is involved.

# 3. Package-Private (Default)
# - No access modifier specified.
class Test {
    void display() {
        System.out.println("Hello");
    }
}
# ✅ Accessible only within the same package.

# 4. private
# - Accessible only inside the same class.
class Employee {
    private double salary = 50000;
}
# ✅ Used for encapsulation and data hiding.

# Access Modifier Comparison
| Modifier  | Same Class | Same Package | Subclass | Other Package |
| --------- | ---------- | ------------ | -------- | ------------- |
| private   | ✅          | ❌            | ❌        | ❌             |
| default   | ✅          | ✅            | ❌        | ❌             |
| protected | ✅          | ✅            | ✅        | ❌             |
| public    | ✅          | ✅            | ✅        | ✅             |

# 5. static
# - Belongs to the class rather than objects.
class Counter {
    static int count = 0;
}
# Access: Counter.count++;
# ✅ Shared among all objects.

# 6. final
# Final Variable
final int MAX = 100;
# Cannot be changed.

# Final Method
final void show() {}
# Cannot be overridden.

# Final Class
final class MathUtil {}
# Cannot be inherited.

# 7. abstract
# Abstract class:
abstract class Shape {
    abstract void draw();
}
# Implementation: 
class Circle extends Shape {
    void draw() {
        System.out.println("Drawing Circle");
    }
}
# ✅ Used to achieve abstraction.

# 8. synchronized
# - Used in multithreading to allow only one thread at a time.
synchronized void display() {
    System.out.println("Thread Safe");
}
# ✅ Prevents race conditions.

# 9. volatile
# - Ensures that variable updates are immediately visible to all threads.
class Shared {
    volatile boolean flag = true;
}
# ✅ Used for thread communication.

# 10. transient
# - Excludes a field from serialization.
class User implements Serializable {
    transient String password;
}
# ✅ Sensitive data won't be saved.

# 11. native
# - Declares a method implemented in another language (usually C/C++).
public native void show();
# - Often used with JNI (Java Native Interface).

# 12. strictfp
# - Ensures consistent floating-point calculations across platforms.
strictfp class Calculation {
}

# Modern Relevance: Since modern JVMs already follow IEEE 754 standards consistently, strictfp is rarely needed today.

# 13. this
# - Refers to the current object.
class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}

# Uses:
# - Access current object's variables
# - Call current class methods
# - Constructor chaining
this();

# 14. super
# - Refers to the parent class.
class Animal {
    String type = "Animal";
}

class Dog extends Animal {
    void show() {
        System.out.println(super.type);
    }
}
# Constructor call: super();
# ✅ Access parent members.

# 15. instanceof
# - Checks object type.
String str = "Java";

System.out.println(str instanceof String);
# Output: true

# 16. assert
# - Used for debugging assumptions.
int age = 20;

assert age > 18;
# Enable assertions:
java -ea Test
# ✅ Helps detect logical errors.

# 17. enum
# - Represents a fixed set of constants.
enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY
}

# Usage: Day d = Day.MONDAY;
# ✅ Type-safe constants.

# 18. record (Java 16+)
# - Special class for immutable data.
record Student(String name, int age) {}
# Automatically generates:
Constructor
Getters
equals()
hashCode()
toString()

# Usage: Student s = new Student("Aman", 22);

# 19. sealed, non-sealed, permits (Java 17+)
# Control inheritance.
# Sealed Class
public sealed class Shape
       permits Circle, Rectangle {
}
# Only specified classes can inherit.

# Child Classes
final class Circle extends Shape {
}
non-sealed class Rectangle extends Shape {
}

# Meanings
sealed → Restricts inheritance.
permits → Lists allowed subclasses.
non-sealed → Removes restriction for a subclass.

# ✅ Better control over class hierarchies.

# Interview Questions
# Q1. Difference between this and super?
| this                               | super                              |
| ---------------------------------- | ---------------------------------- |
| Current class object               | Parent class object                |
| Access current members             | Access parent members              |
| `this()` calls current constructor | `super()` calls parent constructor |

# Q2. Difference between final, finally, and finalize()?
final → Restricts modification/inheritance.
finally → Executes after try-catch.
finalize() → Garbage collection method (deprecated).

# Q3. Difference between volatile and synchronized?
| volatile        | synchronized                  |
| --------------- | ----------------------------- |
| Visibility only | Visibility + Mutual Exclusion |
| Faster          | Slower                        |
| No locking      | Uses locking                  |

# Q4. Why use record?
Because it reduces boilerplate code when creating immutable data classes.
record Employee(int id, String name) {}
No need to manually write constructor, getters, equals(), hashCode(), and toString().

# Memory Trick
PPDP = Access Modifiers
- Public
- Protected
- Default
- Private

SFAVTN
- Static
- Final
- Abstract
- Volatile
- Transient
- Native

These are among the most frequently asked Java keywords in interviews and exams.