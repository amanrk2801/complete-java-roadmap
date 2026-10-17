# Wrapper Classes and Utility Classes in Java
# 1. Integer
# - Integer is the wrapper class for the primitive type int.
# Example
int a = 10;
Integer num = Integer.valueOf(a);

System.out.println(num);
System.out.println(Integer.MAX_VALUE);
System.out.println(Integer.MIN_VALUE);

# Common Methods
Integer.parseInt("100");
Integer.toString(50);
Integer.compare(10, 20);

# 2. Long
# - Wrapper class for long.
Long population = 8000000000L;

System.out.println(population);
System.out.println(Long.MAX_VALUE);

# String to Long
long value = Long.parseLong("123456789");

# 3. Double
# - Wrapper class for double.
Double price = 99.99;

System.out.println(price);
System.out.println(Double.MAX_VALUE);

# Useful Methods
Double.parseDouble("12.5");
Double.isNaN(Double.NaN);
Double.isInfinite(Double.POSITIVE_INFINITY);

# 4. Float
# - Wrapper class for float.
Float percentage = 75.5f;

System.out.println(percentage);

# Parsing
float f = Float.parseFloat("45.8");

# 5. Boolean
# - Wrapper class for boolean.
Boolean flag = true;

System.out.println(flag);

# Methods
Boolean.parseBoolean("true");   // true
Boolean.parseBoolean("false");  // false

# 6. Character
# Wrapper class for char.
Character ch = 'A';

# Useful Methods
System.out.println(Character.isDigit('5')); # true
System.out.println(Character.isLetter('A')); # true
System.out.println(Character.isUpperCase('A')); # true
System.out.println(Character.isLowerCase('a')); # true

# 7. Byte and Short
# Byte
Byte b = 100;
System.out.println(Byte.MAX_VALUE);

# Short
Short s = 2000;
System.out.println(Short.MAX_VALUE);

# 8. Parsing Strings into Numbers
# - Converting strings into numeric values.
String str = "123";

int n = Integer.parseInt(str);
double d = Double.parseDouble("12.34");

System.out.println(n); # 123
System.out.println(d); # 12.34

# 9. NumberFormatException
# - Occurs when a string cannot be converted into a number.
String str = "abc";

try {
    int num = Integer.parseInt(str);
}
catch(NumberFormatException e) {
    System.out.println("Invalid number");
}
# Output: Invalid number

# 10. Math Class
# - The Math class provides mathematical methods.
System.out.println(Math.sqrt(25)); # 5.0
System.out.println(Math.pow(2, 3)); # 8.0
System.out.println(Math.abs(-10)); # 10
System.out.println(Math.max(50, 80)); # 80
System.out.println(Math.min(50, 80)); # 50
System.out.println(Math.round(3.6)); # 4

# 11. Objects Class
# - Utility methods for working with objects.
import java.util.Objects;

String a = "Hello";
String b = "Hello";

System.out.println(Objects.equals(a, b)); # true

# Common Methods
Objects.equals()
Objects.hash()
Objects.requireNonNull()
Objects.isNull()
Objects.nonNull()

# 12. Arrays Class
# - Utility methods for arrays.
import java.util.Arrays;

int[] arr = {5, 2, 9, 1};

Arrays.sort(arr);

System.out.println(Arrays.toString(arr)); # [1, 2, 5, 9]

# Other Methods
Arrays.binarySearch()
Arrays.fill()
Arrays.copyOf()
Arrays.equals()

# 13. Collections Class
# - Provides utility methods for collections.
import java.util.*;

List<Integer> list = new ArrayList<>();

list.add(30);
list.add(10);
list.add(20);

Collections.sort(list);

System.out.println(list); # [10, 20, 30]

# Useful Methods
Collections.reverse()
Collections.shuffle()
Collections.min()
Collections.max()
Collections.frequency()

# 14. UUID
# - Unique Universal Identifier.
import java.util.UUID;

UUID id = UUID.randomUUID();

System.out.println(id); # 550e8400-e29b-41d4-a716-446655440000

# Uses
# - Unique IDs
# - Database keys
# - Session IDs
# - Distributed systems

# 15. Random and SecureRandom
# Random
import java.util.Random;

Random random = new Random();

System.out.println(random.nextInt(100)); # Generates numbers from 0 to 99.

# SecureRandom
# - Used for security-sensitive applications.
import java.security.SecureRandom;

SecureRandom sr = new SecureRandom();

System.out.println(sr.nextInt(100));

# Difference
| Random      | SecureRandom             |
| ----------- | ------------------------ |
| Faster      | Slower                   |
| Predictable | Cryptographically secure |
| General use | Security use             |

# 16. BigInteger
# - Handles extremely large integers.
import java.math.BigInteger;

BigInteger a = new BigInteger("999999999999999999999");
BigInteger b = new BigInteger("123456789");

System.out.println(a.add(b));

# Operations
a.add(b);
a.subtract(b);
a.multiply(b);
a.divide(b);

# 17. BigDecimal
# - Handles high-precision decimal numbers.
import java.math.BigDecimal;

BigDecimal price = new BigDecimal("12.99");
BigDecimal tax = new BigDecimal("2.01");

System.out.println(price.add(tax)); # 15.00

# Operations
add()
subtract()
multiply()
divide()

# 18. Floating-Point Precision
# - Floating-point numbers often produce unexpected results.
System.out.println(0.1 + 0.2); # 0.30000000000000004
# - This happens because decimal values are stored in binary format internally.

# 19. Financial Calculations with BigDecimal
# - Never use float or double for money calculations.
# Wrong
double amount = 0.1 + 0.2;
System.out.println(amount); # 0.30000000000000004

# Correct
import java.math.BigDecimal;

BigDecimal a = new BigDecimal("0.1");
BigDecimal b = new BigDecimal("0.2");

BigDecimal result = a.add(b);

System.out.println(result); # 0.3

# Interest Calculation Example
import java.math.BigDecimal;

BigDecimal principal = new BigDecimal("10000");
BigDecimal rate = new BigDecimal("0.05");

BigDecimal interest =
        principal.multiply(rate);

System.out.println("Interest = " + interest); # Interest = 500.00

# Autoboxing and Unboxing
# - Java automatically converts between primitives and wrapper objects.
Integer num = 10;    // Autoboxing

int value = num;     // Unboxing

System.out.println(value);

# Interview Questions
# Q1: Why use Wrapper Classes?
# - To treat primitive data as objects.
# - Required in Collections (ArrayList<Integer>).
# - Provide utility methods.

# Q2: Difference between parseInt() and valueOf()?
int x = Integer.parseInt("10");   // returns primitive int

Integer y = Integer.valueOf("10"); // returns Integer object

# Q3: Why use BigDecimal for money?
# Because double and float can introduce precision errors, while BigDecimal provides exact decimal arithmetic.

# Q4: When should SecureRandom be used?
# For: Password generation, OTP generation, Tokens, Cryptographic applications

# Summary
# Wrapper Classes: Integer, Long, Double, Float, Boolean, Character, Byte, Short
# Utility Classes: Math, Objects, Arrays, Collections, UUID, Random, SecureRandom
# Large Numbers: BigInteger
# Precise Decimal Arithmetic: BigDecimal
# Use BigDecimal for financial calculations.
# Be aware of floating-point precision issues with float and double.
