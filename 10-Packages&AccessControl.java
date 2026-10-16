# 10. Packages and Access Control in Java
# Packages help organize Java classes and interfaces, while access control determines which classes and members can be accessed from different locations.

# 1. Built-in Packages
# - Java provides many predefined packages.
# Common Built-in Packages
| Package     | Purpose                             |
| ----------- | ----------------------------------- |
| `java.lang` | Core classes (String, Math, System) |
| `java.util` | Collections, Scanner, Date          |
| `java.io`   | File handling                       |
| `java.net`  | Networking                          |
| `java.sql`  | Database connectivity               |
| `java.time` | Date and Time API                   |

Example:
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name:");
        String name = sc.nextLine();
        System.out.println(name);
    }
}

# 2. Custom Packages
# A package created by the programmer.
# Package Structure
com 
└── company 
        └── Employee.java
# Employee.java
package com.company;

public class Employee {
    public void display() {
        System.out.println("Employee Package");
    }
}

# Main.java
import com.company.Employee;

public class Main {
    public static void main(String[] args) {
        Employee e = new Employee();
        e.display();
    }
}

# 3. Package Declaration
# Must be the first statement in a Java source file.
# Syntax: package packageName;
# Example: package com.myapp;

# 4. Import Statements
# Used to access classes from another package.
# Import Specific Class
import java.util.Scanner;
# Import Entire Package
import java.util.*;
# Without Import
java.util.Scanner sc = new java.util.Scanner(System.in);

# 5. Static Imports
# Allows direct access to static members without class name.
# Without Static Import
System.out.println(Math.sqrt(25));
# With Static Import
import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(sqrt(25));
        System.out.println(pow(2,3));
    }
}

Output: 
5.0 
8.0

# 6. Fully Qualified Class Names (FQCN)
# Complete path of a class including package name.
# Syntax: packageName.ClassName
# Example
java.util.ArrayList
java.util.Scanner
java.io.File
# Usage:
java.util.ArrayList<String> list = new java.util.ArrayList<>();
# No import statement needed.

# 7. Package-Private Access
# Default access modifier (no modifier).
# Accessible only within the same package.
# Example
package pack1;

class Test {
    void show() {
        System.out.println("Package Private");
    }
}

# Valid:
package pack1;

public class Main {
    public static void main(String[] args) {
        Test t = new Test();
        t.show();
    }
}
# Invalid from another package.

# 8. Access Across Packages

# public: Accessible from everywhere.
public class Employee {
}

# protected:
# Accessible:
# - Same package
# - Subclasses in other packages
# Example:
package pack1;

public class Parent {
    protected void display() {
        System.out.println("Protected");
    }
}
# package pack2;

import pack1.Parent;

public class Child extends Parent {
    public void show() {
        display();
    }
}

# Default (Package-Private)
# Accessible only inside same package.
class Test {
}

# private: Accessible only inside same class.
class Test {
    private int age;
}

# Access Modifier Summary
| Modifier  | Same Class | Same Package | Subclass Other Package | Everywhere |
| --------- | ---------- | ------------ | ---------------------- | ---------- |
| private   | ✅          | ❌            | ❌                      | ❌          |
| default   | ✅          | ✅            | ❌                      | ❌          |
| protected | ✅          | ✅            | ✅                      | ❌          |
| public    | ✅          | ✅            | ✅                      | ✅          |

# 9. Classpath
# - Classpath tells JVM where to find compiled classes and libraries.
# Example: java -cp . Main
# . means current directory.

# Multiple Locations
# Windows: java -cp .;lib/* Main
# Linux/Mac: java -cp .:lib/* Main

# 10. Module Path
# - Introduced with Java 9 modules.
# - Used to locate modules instead of ordinary classpath entries.
# Example
java --module-path mods
# or
javac --module-path mods

# 11. Java Platform Module System (JPMS)
# - Introduced in Java 9.
# Provides:
# - Better encapsulation
# - Strong dependency management
# - Smaller runtime images
# - Faster startup
# - Improved security
# Traditional Java
Classpath
 ├── Class A
 ├── Class B
 └── Class C

# No strong boundaries.

# Modular Java
Module A
   ↓
Module B
   ↓
Module C
# Dependencies are explicitly declared.

# 12. module-info.java
# - Descriptor file of a module.
# Example
module mymodule {
}
# Placed at the root of the module.
# Structure:
src
 └── mymodule
      ├── module-info.java
      └── com
           └── example

# 13. requires, exports, opens
# - These are module directives.

# requires
# - Declares dependency on another module.
module mymodule {
    requires java.sql;
}
# Meaning: mymodule depends on java.sql

# exports
# - Makes a package available to other modules.
module mymodule {
    exports com.example.service;
}
# - Only exported packages are accessible.

# opens
# - Allows runtime reflection access.
# Used by frameworks such as:
# - Spring
# - Hibernate
# - Jackson
module mymodule {
    opens com.example.entity;
}

# Open to Specific Module
opens com.example.entity
    to hibernate.core;

# Complete Module Example
# module-info.java
module employee.app {
    requires java.sql;

    exports com.employee.service;

    opens com.employee.entity;
}

# Meaning
# ✅ Uses java.sql module
# ✅ Shares com.employee.service
# ✅ Allows reflection on com.employee.entity

# Interview Questions
# Q1: Difference between package and module?
# Package: Groups related classes.
# Module: Groups packages and controls dependencies.

# Q2: What is classpath?
# Location where JVM searches for classes and JAR files.

# Q3: What is package-private access?
# Member with no access modifier, accessible only within the same package.

# Q4: Why use static import?
# To access static methods/variables directly without class name.
import static java.lang.Math.*;

# Q5: Why was JPMS introduced?
# To provide:
# - Strong encapsulation
# - Dependency management
# - Better maintainability
# - Improved security
# - Modular applications