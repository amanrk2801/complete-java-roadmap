# 3. Operators (Java)
# Operators are symbols that perform operations on variables and values.

# 1. Arithmetic Operators
# - Used for mathematical calculations.
| Operator | Meaning             | Example |
| -------- | ------------------- | ------- |
| `+`      | Addition            | `a + b` |
| `-`      | Subtraction         | `a - b` |
| `*`      | Multiplication      | `a * b` |
| `/`      | Division            | `a / b` |
| `%`      | Modulus (remainder) | `a % b` |

int a = 10, b = 3;
System.out.println(a + b); // 13
System.out.println(a - b); // 7
System.out.println(a * b); // 30
System.out.println(a / b); // 3
System.out.println(a % b); // 1

# 2. Relational Operators
# - Used to compare values.
| Operator | Description              |
| -------- | ------------------------ |
| `==`     | Equal to                 |
| `!=`     | Not equal to             |
| `>`      | Greater than             |
| `<`      | Less than                |
| `>=`     | Greater than or equal to |
| `<=`     | Less than or equal to    |

int a = 10, b = 20;
System.out.println(a == b); // false
System.out.println(a < b);  // true
System.out.println(a != b); // true

# 3. Logical Operators
# - Used with boolean values.
| Operator | Meaning     |  
| -------- | ----------- | 
| `&&`     | Logical AND |  
| `||`     | Logical OR  |
| `!`      | Logical NOT |   

boolean a = true;
boolean b = false;
System.out.println(a && b); // false
System.out.println(a || b); // true
System.out.println(!a);     // false

# 4. Assignment Operators
# - Used to assign values.
| Operator | Example  | Equivalent  |
| -------- | -------- | ----------- |
| `=`      | `a = 10` | Assign      |
| `+=`     | `a += 5` | `a = a + 5` |
| `-=`     | `a -= 5` | `a = a - 5` |
| `*=`     | `a *= 5` | `a = a * 5` |
| `/=`     | `a /= 5` | `a = a / 5` |
| `%=`     | `a %= 5` | `a = a % 5` |

int a = 10;
a += 5;
System.out.println(a); // 15

# 5. Unary Operators
# - Operate on a single operand.
| Operator | Meaning     |
| -------- | ----------- |
| `+`      | Positive    |
| `-`      | Negative    |
| `++`     | Increment   |
| `--`     | Decrement   |
| `!`      | Logical NOT |

int a = 5;
System.out.println(-a); // -5
System.out.println(+a); // 5

# 6. Increment and Decrement
# Pre Increment
int a = 5;
System.out.println(++a); // 6
# - First increments, then uses the value.

# Post Increment
int a = 5;
System.out.println(a++); // 5
System.out.println(a);   // 6
# - First uses the value, then increments.

# Pre Decrement
int a = 5;
System.out.println(--a); // 4

# Post Decrement
int a = 5;
System.out.println(a--); // 5
System.out.println(a);   // 4

# 7. Bitwise Operators
# - Work on binary bits.
| Operator | Meaning     |            
| -------- | ----------- | 
| `&`      | Bitwise AND |            
| `|`      | Bitwise OR  |
| `^`      | Bitwise XOR |            
| `~`      | Bitwise NOT | 

int a = 5; // 0101
int b = 3; // 0011
System.out.println(a & b); // 1
System.out.println(a | b); // 7
System.out.println(a ^ b); // 6

# 8. Bit-Shift Operators
# - Shift bits left or right.
| Operator | Meaning              |
| -------- | -------------------- |
| `<<`     | Left shift           |
| `>>`     | Right shift          |
| `>>>`    | Unsigned right shift |

int a = 8; // 1000
System.out.println(a << 1); // 16
System.out.println(a >> 1); // 4

# Example
8 = 00001000 
8 << 1 = 00010000 = 16 
8 >> 1 = 00000100 = 4

# 9. Ternary Operator
# - Short form of if-else.
# Syntax: condition ? value1 : value2;
# Example
int age = 20;
String result = (age >= 18) ? "Adult" : "Minor";
System.out.println(result);
# Output: Adult

# 10. instanceof Operator
# - Checks whether an object belongs to a class.
String s = "Hello";
System.out.println(s instanceof String);
# Output: true

# 11. Operator Precedence
# - Higher precedence operators execute first.
int result = 10 + 5 * 2;
System.out.println(result);
# Output: 20
# Because: 10 + (5 * 2)

# Common Precedence Order
# 1. () Parentheses
# 2. Unary (++, --, !)
# 3. * / %
# 4. + -
# 5. Relational (< > <= >=)
# 6. Equality (== !=)
# 7. &&
# 8. ||
# 9. ?:
# 10. Assignment (= += -=)

# 12. Short-Circuit Evaluation
# - Java stops evaluating when the result is already known.
# AND (&&)
int a = 10;

if(a < 5 && ++a > 10) {
    System.out.println("Hello");
}

System.out.println(a); # 10
# - Since a < 5 is false, Java does not evaluate ++a > 10.

# OR (||)
int a = 10;

if(a > 5 || ++a > 10) {
    System.out.println("True");
}

System.out.println(a); 
# Output: 
# True
# 10
# - Since first condition is true, second condition is skipped.

# 13. Integer Overflow
# - Occurs when a value exceeds the range of its data type.
# Example
int max = Integer.MAX_VALUE;

System.out.println(max);     // 2147483647
System.out.println(max + 1); // -2147483648

# The value wraps around because int can store only:
# -2147483648 to 2147483647

# Avoiding Overflow
# - Use larger data types:
long num = 2147483647L;
System.out.println(num + 1);
# Output: 2147483648

# Quick Revision
# 1. Arithmetic → + - * / %
# 2. Relational → == != > < >= <=
# 3. Logical → && || !
# 4. Assignment → = += -= *= /= %=
# 5. Unary → + - ++ -- !
# 6. Increment/Decrement → Pre and Post
# 7. Bitwise → & | ^ ~
# 8. Bit Shift → << >> >>>
# 9. Ternary → condition ? true : false
# 10. instanceof → Checks object type
# 11. Precedence → Determines execution order
# 12. Short-Circuit → Stops unnecessary evaluation
# 13. Integer Overflow → Value exceeds type limit and wraps around.
