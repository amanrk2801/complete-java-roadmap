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
