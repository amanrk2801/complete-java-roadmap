# Java Exception Handling
# Exception handling is a mechanism used to handle runtime errors so that the normal flow of a program is maintained.

# 1. Errors vs Exceptions
# Error
# - Serious problems that applications generally cannot handle.
# Examples: OutOfMemoryError, StackOverflowError
public class Test {
    public static void main(String[] args) {
        main(args); // Causes StackOverflowError
    }
}

# Exception
# - Conditions that can be caught and handled.
# Examples: ArithmeticException, NullPointerException, IOException
int a = 10;
int b = 0;
System.out.println(a / b); // ArithmeticException

# 2. Exception Hierarchy
Object 
| 
Throwable 
| 
+---- Error 
| 
+---- Exception 
        | 
        +---- RuntimeException 
        | 
        +---- IOException 
        +---- SQLException

# - Throwable is the parent class.
# - Error → System-level problems.
# - Exception → Application-level problems.

# 3. Checked Exceptions
# Checked at compile time.
# Examples: IOException, SQLException, FileNotFoundException
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data.txt");
    }
}
# Compiler forces handling.

# 4. Unchecked Exceptions
# - Occur at runtime.
# Examples:
# - ArithmeticException
# - NullPointerException
# - ArrayIndexOutOfBoundsException
int arr[] = {1,2,3};

System.out.println(arr[5]);

# Output: ArrayIndexOutOfBoundsException

# 5. try
# - Used to place risky code.
try {
    int x = 10 / 0;
}

# 6. catch
# - Handles exceptions from a try block.
try {
    int x = 10 / 0;
}
catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
# Output: Cannot divide by zero

# 7. Multiple catch Blocks
try {
    int arr[] = new int[2];
    arr[5] = 10;
}
catch (ArithmeticException e) {
    System.out.println("Arithmetic Error");
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array Error");
}

# Output: Array Error

# 8. Multi-Catch
# - One catch block handles multiple exception types.
try {
    int x = 10 / 0;
}
catch (ArithmeticException | NullPointerException e) {
    System.out.println("Exception Handled");
}

# 9. finally
# - Always executes whether exception occurs or not.
try {
    int x = 10 / 0;
}
catch (ArithmeticException e) {
    System.out.println("Handled");
}
finally {
    System.out.println("Finally executed");
}

# Output:
# Handled 
# Finally executed

# Common use:
# - Close files
# - Release resources
# - Database cleanup

# 10. throw
# - Used to explicitly throw an exception.
public class Main {
    public static void main(String[] args) {
        throw new ArithmeticException("Custom Error");
    }
}

# Output: 
Exception in thread "main" 
java.lang.ArithmeticException: Custom Error

# 11. throws
# Declares exceptions that a method may throw.
import java.io.*;

class Test {

    static void readFile() throws IOException {
        FileReader fr = new FileReader("abc.txt");
    }

    public static void main(String[] args)
            throws IOException {
        readFile();
    }
}

# Difference Between throw and throws
| throw                   | throws                      |
| ----------------------- | --------------------------- |
| Used to throw exception | Used to declare exception   |
| Inside method body      | In method signature         |
| One exception at a time | Multiple exceptions allowed |

# 12. Custom Exceptions
# - Creating user-defined exceptions.
class AgeException extends Exception {

    AgeException(String msg) {
        super(msg);
    }
}

public class Main {

    static void checkAge(int age)
            throws AgeException {

        if(age < 18)
            throw new AgeException(
                    "Not Eligible");

        System.out.println("Eligible");
    }

    public static void main(String[] args) {

        try {
            checkAge(15);
        }
        catch(AgeException e) {
            System.out.println(e.getMessage());
        }
    }
}

# Output: Not Eligible

# 13. Exception Chaining
# - One exception causes another.
class Main {

    public static void main(String[] args) {

        try {

            try {
                int x = 10 / 0;
            }
            catch (ArithmeticException e) {
                throw new Exception(
                        "Calculation Failed", e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
# Output shows original cause.

# 14. Exception Propagation
# - Unchecked exceptions automatically move up the call stack.
class Main {

    static void m3() {
        int x = 10 / 0;
    }

    static void m2() {
        m3();
    }

    static void m1() {
        m2();
    }

    public static void main(String[] args) {
        m1();
    }
}

# Flow: m3 → m2 → m1 → main

# 15. Try-With-Resources
# Automatically closes resources.
import java.io.*;

public class Main {

    public static void main(String[] args)
            throws IOException {

        try (FileReader fr =
                     new FileReader("abc.txt")) {

            int ch;
            while((ch = fr.read()) != -1) {
                System.out.print((char)ch);
            }
        }
    }
}

# No need for explicit close().

# 16. Suppressed Exceptions
# When multiple exceptions occur, Java keeps secondary exceptions as suppressed.
class TestResource
        implements AutoCloseable {

    public void close() throws Exception {
        throw new Exception("Close Exception");
    }
}

public class Main {

    public static void main(String[] args) {

        try(TestResource t =
                    new TestResource()) {

            throw new Exception(
                    "Main Exception");

        } catch(Exception e) {

            System.out.println(
                    e.getMessage());

            for(Throwable s :
                    e.getSuppressed()) {

                System.out.println(
                        s.getMessage());
            }
        }
    }
}
# Output: 
Main Exception
Close Exception

# 17. Best Practices for Exception Handling
# ✅ Catch specific exceptions
catch(IOException e)
# ❌ Avoid
catch(Exception e)
# unless required.

# ✅ Log exceptions properly
e.printStackTrace();
# or logging frameworks.

# ✅ Use custom exceptions when needed.

# ✅ Close resources using: try-with-resources

# ✅ Provide meaningful messages.
throw new Exception(
    "Database connection failed");

# 18. Avoiding Swallowed Exceptions
# Bad Practice:
try {
    int x = 10 / 0;
}
catch(Exception e) {
}
# Exception disappears silently.

# Good Practice:
catch(Exception e) {
    System.out.println(
            e.getMessage());
}
# Or log it.

# 19. Designing Application-Specific Exceptions
# Create exceptions representing business rules.
class InsufficientBalanceException
        extends Exception {

    InsufficientBalanceException(
            String msg) {

        super(msg);
    }
}

# Usage:
class Account {

    double balance = 5000;

    void withdraw(double amount)
            throws InsufficientBalanceException {

        if(amount > balance) {

            throw new
              InsufficientBalanceException(
               "Insufficient Balance");
        }

        balance -= amount;
    }
}

# Benefits:
# - Clear code
# - Better debugging
# - Business-rule representation
# - Easier maintenance

# Interview Questions
# Q1. Difference between Error and Exception?
# - Error: Serious system problem.
# - Exception: Recoverable application problem.

# Q2. Difference between Checked and Unchecked Exceptions?
# - Checked: Compile-time (IOException)
# - Unchecked: Runtime (NullPointerException)

# Q3. Difference between throw and throws?
# - throw actually throws an exception.
# - throws declares possible exceptions.

# Q4. Does finally always execute?
# Yes, except:
# - JVM crash
# - System.exit()
# - Power failure

# Q5. Why use try-with-resources?
# - It automatically closes resources and prevents resource leaks.

# Q6. Can we have try without catch?
# - Yes, if finally exists.
try {
    System.out.println("Hello");
}
finally {
    System.out.println("Done");
}

# Q7. Can we have multiple catch blocks?
# - Yes, but place child exceptions before parent exceptions.
catch(FileNotFoundException e) {}
catch(IOException e) {}
# - Otherwise compilation error occurs.