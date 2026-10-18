# 16. Enums, Records, and Sealed Classes (Java)
# - These features were introduced to make Java code safer, cleaner, and more expressive.
# 1. Enum Declaration
# - An enum is a special type that represents a fixed set of constants.
# Syntax
enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}
# Usage
public class Test {
    public static void main(String[] args) {
        Day today = Day.MONDAY;
        System.out.println(today);
    }
}
# Output: MONDAY

# 2. Enum Constructors
# - Enums can have constructors.
# Example
enum Size {
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L");

    private String code;

    Size(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println(Size.SMALL.getCode());
    }
}
# Output: S 
# Notes: 
# - Enum constructors are always private.
# - Cannot create enum objects using new.

# 3. Enum Methods
# - Java provides several built-in enum methods.
# Example
enum Color {
    RED, GREEN, BLUE
}

public class Main {
    public static void main(String[] args) {

        Color c = Color.GREEN;

        System.out.println(c.name());
        System.out.println(c.ordinal());

        for (Color color : Color.values()) {
            System.out.println(color);
        }
    }
}
# Output
GREEN
1
RED
GREEN
BLUE

# Common Methods
| Method      | Description              |
| ----------- | ------------------------ |
| `name()`    | Returns constant name    |
| `ordinal()` | Position of constant     |
| `values()`  | Returns all constants    |
| `valueOf()` | Returns enum from string |

# 4. Enum Fields
# - Enums can contain fields and methods.
enum EmployeeType {
    MANAGER(50000),
    DEVELOPER(30000),
    TESTER(25000);

    private int salary;

    EmployeeType(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
}
System.out.println(EmployeeType.MANAGER.getSalary());
# Output: 50000

# 5. EnumSet
# - EnumSet is a specialized Set implementation for enum types.
# Benefits
# - Faster than HashSet
# - Memory efficient
import java.util.EnumSet;

enum Permission {
    READ, WRITE, EXECUTE
}

public class Main {
    public static void main(String[] args) {

        EnumSet<Permission> permissions =
                EnumSet.of(Permission.READ, Permission.WRITE);

        System.out.println(permissions);
    }
}
# Output: [READ, WRITE]
# Other Methods
EnumSet.allOf(Permission.class);

EnumSet.noneOf(Permission.class);

EnumSet.range(Permission.READ, Permission.EXECUTE);

# 6. EnumMap
# - EnumMap is a Map implementation whose keys are enum constants.
# Example
import java.util.EnumMap;

enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY
}

public class Main {
    public static void main(String[] args) {

        EnumMap<Day, String> map =
                new EnumMap<>(Day.class);

        map.put(Day.MONDAY, "Work");
        map.put(Day.TUESDAY, "Meeting");

        System.out.println(map);
    }
}
# Output: {MONDAY=Work, TUESDAY=Meeting}
# Advantages
# - Faster than HashMap for enum keys.
# - Maintains natural enum order.

# 7. Enum-Based Strategies
# - Enums can implement behavior.
enum Operation {

    ADD {
        public int apply(int a, int b) {
            return a + b;
        }
    },

    SUBTRACT {
        public int apply(int a, int b) {
            return a - b;
        }
    };

    public abstract int apply(int a, int b);
}
public class Main {
    public static void main(String[] args) {

        System.out.println(Operation.ADD.apply(10, 5));
        System.out.println(Operation.SUBTRACT.apply(10, 5));
    }
}
# Output:
15
5
# This is called the Strategy Pattern using Enums.

# 8. Records
# - Introduced for creating immutable data carriers.
# Traditional Class
class Student {
    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }
}
# Record
record Student(int id, String name) {}
# Java automatically generates:
Constructor
Getters (id(), name())
equals()
hashCode()
toString()

# Usage
Student s = new Student(1, "Aman");

System.out.println(s.name());

# 9. Record Constructors
# Records can define custom constructors.
record Employee(int id, String name) {

    public Employee {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
    }
}

# 10. Compact Constructors
# A shorter constructor syntax.
record Person(String name, int age) {

    public Person {

        if (age < 0) {
            throw new IllegalArgumentException(
                    "Age cannot be negative");
        }

        name = name.trim();
    }
}
# Usage
Person p = new Person(" Aman ", 25);

System.out.println(p.name());
# Output: Aman

# 11. Record Immutability Limitations
# - Records are shallowly immutable.
# Example
record Employee(List<String> skills) {}
List<String> skills =
        new ArrayList<>();

skills.add("Java");

Employee e = new Employee(skills);

skills.add("Spring");

System.out.println(e.skills());
# Output: [Java, Spring]
# Why?
# - The reference is final, but the object itself can still change.
# Solution
record Employee(List<String> skills) {

    public Employee {
        skills = List.copyOf(skills);
    }
}

# 12. Sealed Classes
# Sealed classes restrict inheritance.
# Syntax
public sealed class Shape
        permits Circle, Rectangle {
}
# Only permitted subclasses can extend it.
final class Circle extends Shape {
}

final class Rectangle extends Shape {
}

# Benefits
Better security
Controlled inheritance
Easier pattern matching

# 13. Sealed Interfaces
# - Interfaces can also be sealed.
public sealed interface Vehicle
        permits Car, Bike {
}
final class Car implements Vehicle {
}

final class Bike implements Vehicle {
}

# 14. Permitted Subclasses
# - A sealed class/interface explicitly lists allowed subclasses.
public sealed class Animal
        permits Dog, Cat {
}
final class Dog extends Animal {}

non-sealed class Cat extends Animal {}

# Types of Subclasses
# final: No further inheritance.
final class Dog extends Animal {}
# sealed: Can restrict further inheritance.
sealed class Bird extends Animal
        permits Eagle {}
# non-sealed: Removes restrictions.
non-sealed class Cat extends Animal {}

# 15. Pattern Matching with instanceof
# Old Style
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
# New Style
if (obj instanceof String s) {
    System.out.println(s.length());
}
# Example
Object obj = "Java";

if (obj instanceof String s) {
    System.out.println(s.toUpperCase());
}
# Output: JAVA 

# Benefits
No explicit casting
Cleaner syntax
Safer code

# 16. Pattern Matching for switch
# - Works especially well with sealed hierarchies.
sealed interface Shape
        permits Circle, Rectangle {}

record Circle(double radius)
        implements Shape {}

record Rectangle(double length,
                 double width)
        implements Shape {}
# Switch Pattern Matching
public static double area(Shape shape) {

    return switch (shape) {

        case Circle c ->
                Math.PI * c.radius() * c.radius();

        case Rectangle r ->
                r.length() * r.width();
    };
}
# Usage
System.out.println(
        area(new Circle(5))
);
# Output
78.53981633974483

# Advantages
Eliminates many if-else statements.
Compiler checks all cases.
Works perfectly with sealed classes.

# Quick Revision
Enums:
Fixed set of constants
Can have constructors, fields, methods
EnumSet = efficient Set for enums
EnumMap = efficient Map for enum keys
Can implement Strategy Pattern

Records:
Immutable data carriers
Auto-generate constructor, getters, equals, hashCode, toString
Support custom and compact constructors
Shallow immutability

Sealed Classes:
Restrict inheritance
Use permits
Subclasses must be final, sealed, or non-sealed
Ideal for pattern matching

Pattern Matching:
instanceof String s
Pattern matching in switch
Cleaner, safer, less casting

# These topics are commonly asked in Java 17+ interviews, especially Enums vs Constants, Records vs Classes, Sealed Classes, EnumSet/EnumMap, and Pattern Matching with switch.