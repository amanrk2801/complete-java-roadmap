# Functional Programming in Java
# Functional Programming (FP) is a programming paradigm where functions are treated as first-class citizens. Java introduced major functional programming features in Java 8, making code more concise, readable, and suitable for parallel processing.

# 1. Functional Programming Concepts
# Key Principles:
# - Functions as data
# - Immutability
# - Pure functions
# - Higher-order functions
# - Function composition
# - Declarative programming

# Imperative vs Functional
# Imperative Style
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

List<Integer> result = new ArrayList<>();
for (int n : numbers) {
    if (n % 2 == 0) {
        result.add(n);
    }
}
System.out.println(result);

# Functional Style
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

numbers.stream()
       .filter(n -> n % 2 == 0)
       .forEach(System.out::println);

# 2. Functional Interfaces
# A functional interface contains exactly one abstract method.
# Examples:
Runnable
Callable
Comparator
Predicate
Function
Consumer
Supplier

# Example:
interface Greeting {
    void sayHello();
}
# This is a functional interface because it has only one abstract method.

# 3. @FunctionalInterface
# Used to explicitly declare a functional interface.
@FunctionalInterface
interface Greeting {
    void sayHello();
}
# Valid:
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}
# Invalid:
@FunctionalInterface
interface Test {
    void method1();
    void method2(); // Error
}

# 4. Lambda Expressions
# - A lambda expression is an anonymous function.
# Syntax: (parameters) -> expression
# or
(parameters) -> {
    statements;
}

# Examples
Greeting g = () -> System.out.println("Hello");
g.sayHello();

MathOperation add = (a, b) -> a + b;
System.out.println(add.operate(10, 20));

# 5. Method References
# - A shorthand for lambda expressions.
# Syntax: ClassName::methodName
# Static Method Reference
class Demo {
    static void show() {
        System.out.println("Hello");
    }
}

Runnable r = Demo::show;
r.run();

# Instance Method Reference
String str = "Java";

Supplier<Integer> s = str::length;

System.out.println(s.get());

# 6. Constructor References
# - Reference a constructor using ::new.
class Student {
    Student() {
        System.out.println("Object Created");
    }
}
Supplier<Student> sup = Student::new;

Student s = sup.get();
# Output: Object Created

# 7. Built-in Functional Interfaces
# Available in: java.util.function
# Common interfaces:
| Interface      | Purpose                      |
| -------------- | ---------------------------- |
| Predicate      | Test condition               |
| Function       | Transform data               |
| Consumer       | Consume data                 |
| Supplier       | Supply data                  |
| UnaryOperator  | One input, same output type  |
| BinaryOperator | Two inputs, same output type |
| BiFunction     | Two inputs, one output       |
| BiPredicate    | Two inputs, boolean output   |
| BiConsumer     | Two inputs, no return        |

# 8. Predicate
# Represents a boolean-valued function.
# Method
boolean test(T t)
# Example:
Predicate<Integer> isEven = n -> n % 2 == 0;

System.out.println(isEven.test(10));
System.out.println(isEven.test(7));
# Output:
true 
false

# Chaining
Predicate<Integer> positive = n -> n > 0;
Predicate<Integer> even = n -> n % 2 == 0;

Predicate<Integer> result = positive.and(even);

System.out.println(result.test(8));

# 9. Function
# Takes input and returns output.
# Method
R apply(T t)
# Example:
Function<String, Integer> length = s -> s.length();

System.out.println(length.apply("Java"));
# Output: 4

# 10. Consumer
# Accepts input and returns nothing.
# Method
void accept(T t)
# Example:
Consumer<String> printer =
        msg -> System.out.println(msg);

printer.accept("Hello Java");
# Output: Hello Java

# 11. Supplier
# Provides data without taking input.
# Method
T get()
# Example:
Supplier<Double> random =
        () -> Math.random();

System.out.println(random.get());

# 12. UnaryOperator
# Specialized Function where input and output types are the same.
UnaryOperator<Integer> square =
        n -> n * n;

System.out.println(square.apply(5));
# Output: 25

# 13. BinaryOperator
# Takes two operands of the same type and returns the same type.
BinaryOperator<Integer> add =
        (a, b) -> a + b;

System.out.println(add.apply(10, 20));
# Output: 30

# 14. BiFunction, BiPredicate, BiConsumer
# BiFunction
BiFunction<Integer, Integer, Integer> add =
        (a, b) -> a + b;

System.out.println(add.apply(5, 6));
# Output: 11

# BiPredicate
BiPredicate<Integer, Integer> check =
        (a, b) -> a > b;

System.out.println(check.test(10, 5));
# Output: true

# BiConsumer
BiConsumer<String, Integer> display =
        (name, age) ->
                System.out.println(name + " " + age);

display.accept("Aman", 22);
# Output: Aman 22

# 15. Capturing Local Variables
# Lambdas can access local variables from the enclosing scope.
int x = 10;

Consumer<Integer> c =
        y -> System.out.println(x + y);

c.accept(5);
# Output: 15

# 16. Effectively Final Variables
# A local variable used inside a lambda must be:
# - final, or
# - effectively final (not modified after initialization)
# Valid:
int x = 10;

Runnable r = () ->
        System.out.println(x);
# Invalid:
int x = 10;

x++;

Runnable r = () ->
        System.out.println(x);
# Compilation Error:
Local variable x defined in an 
enclosing scope
must be final or effectively final

# 17. Higher-Order Functions
# A function that:
# - Accepts another function as input
# - Returns a function
public static int calculate(
        int a,
        int b,
        BinaryOperator<Integer> op) {

    return op.apply(a, b);
}

public static void main(String[] args) {

    int sum = calculate(10, 20, (x, y) -> x + y);

    System.out.println(sum);
}

# Output: 30

# 18. Function Composition
# Combining functions.
# andThen()
Function<Integer, Integer> multiply =
        x -> x * 2;

Function<Integer, Integer> add =
        x -> x + 3;

Function<Integer, Integer> result =
        multiply.andThen(add);

System.out.println(result.apply(5));
# Calculation:
5 × 2 = 10 
10 + 3 = 13
# Output: 13

# compose()
Function<Integer, Integer> result =
        multiply.compose(add);

System.out.println(result.apply(5));
# Calculation:
5 + 3 = 8 
8 × 2 = 16
# Output: 16

# 19. Default Methods in Interfaces
# Java 8 allows method implementation inside interfaces using default.
interface Vehicle {

    default void start() {
        System.out.println("Vehicle Started");
    }
}

class Car implements Vehicle {
}

public class Main {
    public static void main(String[] args) {

        Car car = new Car();
        car.start();
    }
}

# Output: Vehicle Started
# Why Default Methods?
# They allow adding new methods to interfaces without breaking existing implementations.

# Interview Questions
# 1. What is a Functional Interface?
# An interface having exactly one abstract method.

# 2. Why use @FunctionalInterface?
# Provides compile-time validation that the interface has only one abstract method.

# 3. Difference between Lambda and Method Reference?
# - Lambda: provides implementation.
# - Method Reference: refers to an existing method.
x -> System.out.println(x)
System.out::println

# 4. Difference between Function and Consumer?
# Function
Function<String,Integer>
# Returns a value.
# Consumer
Consumer<String>
# Returns nothing.

# 5. Difference between andThen() and compose()?
# f.andThen(g)
# Executes: f → g
# f.compose(g)
# Executes: g → f

# 6. What is an Effectively Final Variable?
# A variable whose value is not changed after initialization and can be used inside lambdas.

# Summary
# - Functional Programming introduced in Java 8.
# - Functional Interfaces contain one abstract method.
# - @FunctionalInterface enforces functional interface rules.
# - Lambda expressions provide anonymous functions.
# - Method and Constructor References simplify lambda code.
# - Built-in interfaces include Predicate, Function, Consumer, and Supplier.
# - Variables used in lambdas must be effectively final.
# - Function composition uses andThen() and compose().
# - Default methods allow concrete methods inside interfaces.