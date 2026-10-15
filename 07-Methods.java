# Java Methods
# Methods are blocks of code that perform a specific task. They help make programs modular, reusable, and easier to maintain.

# 1. Method Declaration
# A method declaration defines a method's name, return type, and parameters.

# Syntax
accessModifier returnType methodName(parameters) {
    // method body
}

# Example
public int add(int a, int b) {
    return a + b;
}

# - public → Access modifier
# - int → Return type
# - add → Method name
# - (int a, int b) → Parameters

# 2. Parameters and Arguments
# Parameters
# - Variables declared in a method definition.
public void greet(String name) {
    System.out.println("Hello " + name);
}
# name is a parameter.

# Arguments
# Actual values passed when calling a method.
# greet("Aman");
# "Aman" is an argument.

# 3. Return Types
# - The return type specifies what value a method returns.
# Example
public int square(int num) {
    return num * num;
}
# Usage:
int result = square(5);
System.out.println(result); # 25

# 4. void Methods
# - A void method does not return any value.
# Example
public void display() {
    System.out.println("Welcome");
}

# Calling: display();
# Output: Welcome

# 5. Method Calling
# Methods can be called by their name.
# Example
public static void greet() {
    System.out.println("Hello");
}

public static void main(String[] args) {
    greet();
}

# Output: Hello

# 6. Method Overloading
# - Multiple methods with the same name but different parameter lists.
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }
}

# Usage:
Calculator c = new Calculator();

System.out.println(c.add(10, 20)); # 30
System.out.println(c.add(5.5, 2.5)); # 8.0

# 7. Pass-by-Value in Java
# - Java always passes arguments by value.
# Primitive Example
public static void change(int x) {
    x = 50;
}

public static void main(String[] args) {
    int num = 10;
    change(num);
    System.out.println(num);
}

# Output: 10
# The original variable remains unchanged.

# Object Example
class Student {
    String name;
}

public static void changeName(Student s) {
    s.name = "Aman";
}
# The object's data can be modified because a copy of the reference is passed.

# 8. Varargs (Variable Arguments)
# Allows passing any number of arguments.
# Syntax: methodName(type... variableName)
# Example
public static int sum(int... numbers) {
    int total = 0;

    for (int num : numbers) {
        total += num;
    }

    return total;
}
# Usage:
System.out.println(sum(10, 20)); # 30
System.out.println(sum(10, 20, 30, 40)); # 100

# 9. Recursion
# - A method calling itself.
# Example: Factorial
public static int factorial(int n) {

    if (n == 1)
        return 1;

    return n * factorial(n - 1);
}

# Usage: System.out.println(factorial(5));
# Output: 120

# 10. Static Methods
# - Belong to the class, not an object.
class MathUtil {

    static int square(int x) {
        return x * x;
    }
}
# Calling: System.out.println(MathUtil.square(5));
# Output: 25

# Characteristics
# - Called using class name.
# - No object creation needed.
# - Can directly access static members only.

# 11. Instance Methods
# - Belong to an object.
class Person {

    void introduce() {
        System.out.println("I am Aman");
    }
}
# Calling: 
Person p = new Person();
p.introduce();

# Output: I am Aman

# 12. Method Signatures
# - A method signature consists of:
# Method name
# Parameter list

void display(int a)
void display(String s)

# - Different signatures allow method overloading.
# ❌ Not part of signature:
# - Return type
# - Access modifier

# Invalid:
int show()
double show()

# - This causes a compilation error.

# 13. Method References
# - Introduced in Java 8 to refer to methods using ::.

# Example
List<String> names =
        Arrays.asList("Aman", "Raj", "Vijay");

names.forEach(System.out::println);

# Equivalent to: names.forEach(name -> System.out.println(name));

# Types
# 1. Static method reference
# 2. Instance method reference
# 3. Constructor reference

# Example: Supplier<String> s = String::new;

# 14. Variable Scope
# - Scope determines where a variable can be accessed.
# Local Variable
public void test() {
    int x = 10;
}
# Accessible only within the method.

# Parameter Scope
public void show(int num) {
    System.out.println(num);
}
# Accessible only inside the method.

# Instance Variable
class Student {
    String name;
}
# Accessible throughout the object.

# Static Variable
class Test {
    static int count = 0;
}
# Shared among all objects.

# 15. Method Documentation with Javadoc
# Javadoc generates API documentation.
# Example
/**
 * Adds two integers.
 *
 * @param a first number
 * @param b second number
 * @return sum of a and b
 */
public int add(int a, int b) {
    return a + b;
}

# Common Tags
| Tag        | Description            |
| ---------- | ---------------------- |
| `@param`   | Describes a parameter  |
| `@return`  | Describes return value |
| `@author`  | Author name            |
| `@version` | Version information    |
| `@throws`  | Exception description  |

# Generate documentation: javadoc MyClass.java

Quick Summary
Method = reusable block of code.
Parameter = variable in method definition.
Argument = value passed during call.
Return type specifies returned value.
void methods return nothing.
Overloading = same method name, different parameters.
Java is pass-by-value.
Varargs allow flexible arguments.
Recursion means a method calls itself.
Static methods belong to a class.
Instance methods belong to objects.
Method signature = method name + parameters.
Method reference uses ::.
Scope controls variable visibility.
Javadoc creates documentation.