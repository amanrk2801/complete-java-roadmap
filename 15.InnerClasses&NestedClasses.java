# 15. Inner Classes and Nested Classes (Java)
# In Java, a nested class is a class defined inside another class.
# Types of Nested Classes:
# 1. Member Inner Class
# 2. Static Nested Class
# 3. Local Class
# 4. Anonymous Inner Class

# 1. Member Inner Classes
# - A non-static class declared inside another class.
class Outer {
    private String msg = "Hello from Outer";

    class Inner {
        void display() {
            System.out.println(msg);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Outer obj = new Outer();
        Outer.Inner in = obj.new Inner();

        in.display();
    }
}

# Output: Hello from Outer
# Key Points
# - Cannot have static members (except constants).
# - Can access all members of outer class, including private members.
# - Requires an object of outer class.
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();

# 2. Static Nested Classes
# - A nested class declared with the static keyword.
class Outer {
    static int x = 100;

    static class Nested {
        void show() {
            System.out.println(x);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Outer.Nested obj = new Outer.Nested();
        obj.show();
    }
}

# Output: 100

# Key Points
# - Behaves like a static member of outer class.
# - Can access only static members directly.
# - No outer class object required.
Outer.Nested obj = new Outer.Nested();

# 3. Local Classes
# - A class declared inside a method, constructor, or block.
class Outer {
    void show() {
        class Local {
            void display() {
                System.out.println("Local Class");
            }
        }

        Local obj = new Local();
        obj.display();
    }
}

public class Main {
    public static void main(String[] args) {
        new Outer().show();
    }
}

# Output: Local Class

# Characteristics
# - Visible only within that method/block.
# - Cannot be accessed outside.
# - Useful for helper functionality limited to a method.

# 4. Anonymous Inner Classes
# - A class without a name created and instantiated simultaneously.
# Example with Class
class Animal {
    void sound() {
        System.out.println("Animal Sound");
    }
}

public class Main {
    public static void main(String[] args) {

        Animal a = new Animal() {
            void sound() {
                System.out.println("Dog Barks");
            }
        };

        a.sound();
    }
}

# Output: Dog Barks
# Example with Interface
interface Greeting {
    void sayHello();
}

public class Main {
    public static void main(String[] args) {

        Greeting g = new Greeting() {
            public void sayHello() {
                System.out.println("Hello");
            }
        };

        g.sayHello();
    }
}
# Output: Hello
# Modern Alternative
# Anonymous classes are often replaced by Lambda Expressions.
Greeting g = () -> System.out.println("Hello");

# 5. Access to Enclosing Class Members
# - Inner classes can access all members of the outer class, even private ones.
class Outer {

    private int data = 50;

    class Inner {
        void show() {
            System.out.println(data);
        }
    }
}
# Output: 50
# Even private variables and methods are accessible.
class Outer {
    private void secret() {
        System.out.println("Private Method");
    }

    class Inner {
        void call() {
            secret();
        }
    }
}

# 6. Effectively Final Variables
# - Local and anonymous classes can access local variables only if they are final or effectively final.
public class Demo {

    public void display() {

        int number = 10; // effectively final

        class Local {
            void show() {
                System.out.println(number);
            }
        }

        new Local().show();
    }
}

# Valid
int number = 10;
System.out.println(number);

# Invalid
int number = 10;

number = 20; // modified

class Local {
    void show() {
        System.out.println(number); // Error
    }
}

# Why?
- The local class stores a copy of the variable. Therefore Java requires the value to remain unchanged.

# 7. Nested Classes vs Top-Level Classes
# Top-Level Class
class Student {
}
# Nested Class
class College {
    class Student {
    }
}
# Comparison
| Feature              | Nested Class         | Top-Level Class |
| -------------------- | -------------------- | --------------- |
| Scope                | Inside another class | Independent     |
| Encapsulation        | Better               | Less            |
| Reusability          | Limited              | High            |
| Access outer members | Yes                  | No              |
| Separate object      | Depends              | Yes             |

# When to Use Nested Classes
# - Use when the class is closely related to another class and not useful elsewhere.
class Car {
    class Engine {
    }
}
# An Engine is strongly related to a Car.

# 8. Practical Use Cases
# GUI Event Handling
# Anonymous class:
button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button Clicked");
    }
});

# Builder Pattern
# - Static nested classes are commonly used.
class User {

    private String name;

    static class Builder {
        private String name;

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        User build() {
            User u = new User();
            u.name = name;
            return u;
        }
    }
}

# Usage:
User user = new User.Builder()
                .setName("Aman")
                .build();

# Data Hiding
class Bank {

    private class Account {
        double balance;
    }
}
# The Account class is hidden from outside classes.

# Interview Questions
# Q1. Difference between Inner Class and Static Nested Class?
| Inner Class            | Static Nested Class                     |
| ---------------------- | --------------------------------------- |
| Non-static             | Static                                  |
| Needs outer object     | No outer object needed                  |
| Can access all members | Can directly access only static members |
| More memory overhead   | Less overhead                           |

# Q2. Can an inner class access private members of outer class? - Yes

# Q3. Can a static nested class access non-static members directly?
# No, It must use an outer object.

# Q4. What is an anonymous inner class?
# - A class without a name that is declared and instantiated simultaneously.

# Q5. Why must local variables be effectively final?
# Because local and anonymous classes capture a copy of the variable, so Java prevents modifications after capture.

# Summary
Nested Classes
│
├── Static Nested Class
│
└── Inner Classes
     │
     ├── Member Inner Class
     ├── Local Class
     └── Anonymous Inner Class

# Rule to Remember:
Member Inner Class → Needs outer object.
Static Nested Class → No outer object needed.
Local Class → Exists inside a method.
Anonymous Class → No class name, one-time use.
Local variables used inside local/anonymous classes must be effectively final.