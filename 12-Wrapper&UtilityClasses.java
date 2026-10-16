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
