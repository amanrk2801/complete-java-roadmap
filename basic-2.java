# 2. Variables and Data Types (Java)
# Variables are named memory locations used to store data. Java is a statically typed language, which means every variable must have a declared type.

# 1. Variables and Declarations
# A variable declaration specifies the data type and variable name.
# Syntax: dataType variableName;
int age;
age = 25;
String name = "Aman";
# You can also declare multiple variables:
int a = 10, b = 20, c = 30;

# 2. Local Variables
# Variables declared inside a method, constructor, or block.
# Characteristics
# - Accessible only within the block where declared.
# - No default value.
# - Created when method starts.
# - Destroyed when method ends.
class Test {
    void display() {
        int x = 100; // Local variable
        System.out.println(x);
    }
}

# 3. Instance Variables
# - Variables declared inside a class but outside methods and without static.
# Characteristics
# - Each object gets its own copy.
# - Stored in heap memory.
# - Default values are assigned automatically.
class Student {
    String name;  // Instance variable
    int age;

    void display() {
        System.out.println(name + " " + age);
    }
}
Student s1 = new Student();
s1.name = "Aman";
s1.age = 21;

# 4. Static/Class Variables
# - Variables declared with the static keyword.
# Characteristics
# - Shared among all objects.
# - Only one copy exists.
# - Loaded when class is loaded.
class Student {
    static String college = "ABC College";

    String name;
}
System.out.println(Student.college);

# 5. Primitive Data Types
# Java provides 8 primitive data types.
| Type    | Size          | Example   |
| ------- | ------------- | --------- |
| byte    | 1 byte        | `10`      |
| short   | 2 bytes       | `2000`    |
| int     | 4 bytes       | `100000`  |
| long    | 8 bytes       | `100000L` |
| float   | 4 bytes       | `12.5f`   |
| double  | 8 bytes       | `12.5`    |
| char    | 2 bytes       | `'A'`     |
| boolean | JVM dependent | `true`    |

# Example
int age = 21;
double salary = 35000.50;
char grade = 'A';
boolean status = true;

# 6. Reference Data Types
# - Reference variables store the address of objects.
# Examples
String
Arrays
Classes
Interfaces
Enums

String name = "Aman";
int[] numbers = {1, 2, 3};
Student s = new Student();

# 7. Default Values
# Default values are assigned only to instance and static variables.
| Type      | Default Value |
| --------- | ------------- |
| byte      | 0             |
| short     | 0             |
| int       | 0             |
| long      | 0L            |
| float     | 0.0f          |
| double    | 0.0d          |
| char      | '\u0000'      |
| boolean   | false         |
| Reference | null          |

class Demo {
    int num;
    boolean flag;

    void display() {
        System.out.println(num);   // 0
        System.out.println(flag);  // false
    }
}

# Local variables don't receive default values.
void test() {
    int x;
    System.out.println(x); // Compile-time error
}

# 8. Literals
# Fixed values assigned directly in code.
# Integer Literals
int a = 100;
int b = 0x64;  // Hexadecimal
int c = 0b1100100; // Binary

# Floating Point Literals
float f = 10.5f;
double d = 20.75;

# Character Literal
char ch = 'A';

# String Literal
String str = "Hello";

# Boolean Literal
boolean flag = true;

# 9. Type Casting
# Converting one data type into another.
# Types
# 1. Implicit Casting
# 2. Explicit Casting

# Implicit Casting (Automatic)
# Smaller type → Larger type
int num = 100;
double d = num;
System.out.println(d);
# Output: 100.0

# Explicit Casting
# Larger type → Smaller type
double d = 10.75;
int num = (int)d;
System.out.println(num);
# Output: 10

# 10. Widening and Narrowing Conversions
# Widening Conversion
# Automatic conversion from smaller type to larger type.
# byte -> short -> char -> int -> long -> float -> double
# Example:
int x = 100;
double y = x;

# Narrowing Conversion
# Manual conversion from larger type to smaller type.
double x = 25.67;
int y = (int)x;
# Output: 25
# Possible data loss may occur.

# 11. Type Promotion
# - Java automatically promotes smaller data types during arithmetic operations.
byte a = 10;
byte b = 20;
int result = a + b;
System.out.println(result);
# - Even though both operands are byte, result becomes int.

# Another Example
char ch = 'A';
int value = ch + 1;
System.out.println(value);
# Output: 66

# 12. Wrapper Classes
# - Each primitive type has a corresponding wrapper class.
| Primitive | Wrapper   |
| --------- | --------- |
| byte      | Byte      |
| short     | Short     |
| int       | Integer   |
| long      | Long      |
| float     | Float     |
| double    | Double    |
| char      | Character |
| boolean   | Boolean   |

Integer num = Integer.valueOf(100);
System.out.println(num);

# Why Use Wrapper Classes?
# - Collections require objects.
# - Utility methods available.
# - Can store null.

# 13. Autoboxing and Unboxing
# Autoboxing
# - Primitive → Wrapper object automatically.
int num = 100;
Integer obj = num;
# Equivalent to:
Integer obj = Integer.valueOf(num);

# Unboxing
# Wrapper object → Primitive.
Integer obj = 200;
int num = obj;
# Equivalent to: 
int num = obj.intValue();

# 14. var Local Variable Type Inference
# - Introduced in Java 10.
# - Compiler automatically determines the type.
var name = "Aman";
var age = 21;
var salary = 45000.50;
# Compiler infers:
String name = "Aman";
int age = 21;
double salary = 45000.50;

# Rules
# ✅ Valid: var x = 10;
# ❌ Invalid: var x;
# ❌ Invalid: var x = null;
# - var can be used only for local variables.

# 15. Constants Using final
# - The final keyword makes a variable constant.
final double PI = 3.14159;
System.out.println(PI); # PI = 3.14; // Error

# Naming Convention
final int MAX_SIZE = 100;
# - Use uppercase letters with underscores.

# 16. Scope and Lifetime of Variables
# Scope: Defines where a variable can be accessed.
# Local Scope
void test() {
    int x = 10;
}
# - x exists only inside the method.

# Block Scope
if(true) {
    int a = 5;
}

System.out.println(a); // Error

# Class Scope
class Student {
    int age;
}
# - Accessible through object reference.

# Lifetime
| Variable Type     | Lifetime                          |
| ----------------- | --------------------------------- |
| Local Variable    | Until method/block execution ends |
| Instance Variable | Until object is destroyed         |
| Static Variable   | Until program/class unloading     |

# Quick Interview Questions
# Q1. Difference between Instance and Static Variables?
| Instance                 | Static                  |
| ------------------------ | ----------------------- |
| Separate copy per object | Single shared copy      |
| Created with object      | Created with class      |
| Access using object      | Access using class name |

# Q2. Can local variables have default values?
# - No. Local variables must be initialized before use.

# Q3. What is Type Promotion?
# - Automatic conversion of smaller numeric types (byte, short, char) to int during arithmetic operations.

# Q4. What is the difference between Primitive and Reference Types?
| Primitive           | Reference               |
| ------------------- | ----------------------- |
| Stores actual value | Stores object reference |
| Fixed size          | Variable size           |
| Cannot be null      | Can be null             |
| Faster              | Relatively slower       |

# Q5. Why use Wrapper Classes?
# - To work with collections.
# - To use utility methods.
# - To allow null values.
# - For autoboxing/unboxing support.