# Java Strings
# 1. String Class
# - String is a predefined class in Java used to store text.
String name = "Aman";
System.out.println(name);

# 2. String Creation
# Using String Literal
String s1 = "Hello";
String s2 = "Hello";

# Using new Keyword
String s3 = new String("Hello");

# 3. String Immutability
# Strings cannot be changed after creation.
String s = "Java";
s.concat(" Programming");

System.out.println(s); # Java

# To store the result: 
s = s.concat(" Programming");
System.out.println(s);

# 4. String Pool
# - Java stores string literals in a special memory area called the String Pool.
String s1 = "Java";
String s2 = "Java";

System.out.println(s1 == s2);

# Output: true
# Both variables point to the same object.

# 5. == vs equals()
# ==
# Compares references.
String s1 = new String("Java");
String s2 = new String("Java");
System.out.println(s1 == s2);

# Output: false

# equals()
# - Compares content.
System.out.println(s1.equals(s2));

# Output: true

# 6. equalsIgnoreCase()
String s1 = "java";
String s2 = "JAVA";

System.out.println(s1.equalsIgnoreCase(s2));

# Output: true

# 7. compareTo()
String s1 = "Apple";
String s2 = "Banana";

System.out.println(s1.compareTo(s2));

# Output: -1

# Rules:
# 1. 0 → Equal
# 2. Negative → First string comes before second
# 3. Positive → First string comes after second

# 8. substring()
String s = "Programming";

System.out.println(s.substring(3));
System.out.println(s.substring(0, 4));

# Output:
# gramming 
# Prog

# 9. charAt()
String s = "Java";

System.out.println(s.charAt(0));

# Output: J

# 10. indexOf() and lastIndexOf()
String s = "banana";

System.out.println(s.indexOf('a')); # 1
System.out.println(s.lastIndexOf('a')); # 5

# 11. contains()
String s = "Java Programming";

System.out.println(s.contains("Program")); # true

# 12. startsWith() and endsWith()
String s = "Java Programming";

System.out.println(s.startsWith("Java")); # true
System.out.println(s.endsWith("ing")); # true 

# 13. split()
String s = "Java,Python,C++";

String[] arr = s.split(",");

for(String lang : arr)
{
    System.out.println(lang);
}

# Output:
Java Python C++

# 14. replace()
String s = "I like Java";

System.out.println(s.replace("Java", "Python"));

# Output: I like Python

# 15. trim() and strip()
# trim() 
String s = "   Hello   ";

System.out.println(s.trim());

# strip() (Java 11+)
System.out.println(s.strip());
# Both remove leading and trailing spaces.

# 16. isEmpty() and isBlank()
String s1 = "";
String s2 = "   ";

System.out.println(s1.isEmpty()); # true
System.out.println(s2.isBlank()); # true 

# Difference:
"".isEmpty();     // true
"   ".isEmpty();  // false

"   ".isBlank();  // true

# 17. String Formatting
# printf()
String name = "Aman";
int age = 22;

System.out.printf("Name: %s Age: %d", name, age);

# String.format()
String result = String.format(
    "Name: %s Age: %d",
    name,
    age
);

System.out.println(result);

# 18. StringBuilder
# - Mutable string class.
StringBuilder sb = new StringBuilder("Java");

sb.append(" Programming");

System.out.println(sb);

# Output: Java Programming

# Common Methods
sb.append("123");
sb.insert(0, "Hi ");
sb.delete(0, 3);
sb.reverse();

# 19. StringBuffer
# - Similar to StringBuilder but thread-safe.
StringBuffer sb = new StringBuffer("Java");

sb.append(" Programming");

System.out.println(sb);

# Difference
| StringBuilder    | StringBuffer   |
| ---------------- | -------------- |
| Faster           | Slower         |
| Not synchronized | Synchronized   |
| Single-threaded  | Multi-threaded |

# 20. String Concatenation Performance
# Inefficient
String s = "";

for(int i = 0; i < 1000; i++)
{
    s += i;
}
# Each concatenation creates a new String object.

# Efficient
StringBuilder sb = new StringBuilder();

for(int i = 0; i < 1000; i++)
{
    sb.append(i);
}
# Preferred for loops.

# 21. Text Blocks (Java 15+)
String text = """
        Hello
        Welcome to Java
        Text Blocks
        """;

System.out.println(text);

# Output:
Hello 
Welcome to Java 
Text Blocks

# 22. Unicode and Character Encoding
# - Java uses Unicode to support multiple languages.
String hindi = "नमस्ते";
String chinese = "你好";

System.out.println(hindi);
System.out.println(chinese);

# Unicode Example
char ch = '\u0041';

System.out.println(ch); # A 

# Encoding
String s = "Java";

byte[] data = s.getBytes();

System.out.println(data.length);

# Common encodings:
UTF-8
UTF-16 (Java internally uses Unicode representation)
ASCII

# Interview Questions
# Q1. Why are Strings immutable in Java?
Security
String pool optimization
Thread safety
HashCode caching

# Q2. Difference between == and equals()?
== → compares references
equals() → compares content

# Q3. Difference between String, StringBuilder, and StringBuffer?
| Feature     | String                 | StringBuilder | StringBuffer |
| ----------- | ---------------------- | ------------- | ------------ |
| Mutable     | No                     | Yes           | Yes          |
| Thread Safe | Yes                    | No            | Yes          |
| Performance | Slow for modifications | Fastest       | Slower       |

# Q4. What is the String Pool?
A special memory area where string literals are stored and reused.

# Q5. Which is better for repeated concatenation?
StringBuilder because it avoids creating multiple String objects.
