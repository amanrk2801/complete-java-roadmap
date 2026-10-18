# Java Optional API
# Optional<T> was introduced in Java 8 to represent a value that may or may not be present. It helps reduce NullPointerException and makes code more expressive.

# 1. Why Optional Exists
# Before Java 8:
String name = getName();

if (name != null) {
    System.out.println(name.length());
}
# Risk:
String name = getName();
System.out.println(name.length()); // NullPointerException
# Using Optional:
Optional<String> name = getName();

name.ifPresent(n -> System.out.println(n.length()));

# Benefits:
# - Avoids NullPointerException
# - Makes absence of value explicit
# - Encourages functional programming style
# - Improves readability

# 2. Optional.of()
# - Creates an Optional containing a non-null value.
Optional<String> name = Optional.of("Aman");

System.out.println(name.get());
# Output: Aman

# Important
# - Passing null causes an exception.
Optional<String> name = Optional.of(null);
# Output: NullPointerException
# Use when you're sure the value is not null.

# 3. Optional.ofNullable()
# - Creates an Optional that can handle both null and non-null values.
String name = null;

Optional<String> opt = Optional.ofNullable(name);

System.out.println(opt);
# Output: Optional.empty

# Non-null case:
Optional<String> opt = Optional.ofNullable("Aman");
System.out.println(opt);

# Output: Optional[Aman]
# Most commonly used factory method.

# 4. Optional.empty()
# Creates an empty Optional.
Optional<String> opt = Optional.empty();

System.out.println(opt);
# Output: Optional.empty
# Equivalent to:
Optional<String> opt = Optional.ofNullable(null);

# 5. isPresent()
# - Checks whether a value exists.
Optional<String> opt = Optional.of("Java");

if (opt.isPresent()) {
    System.out.println(opt.get());
}
# Output: Java
# Not recommended frequently because it resembles old-style null checks.
# Bad:
if(opt.isPresent()) {
    System.out.println(opt.get());
}
# Prefer:
opt.ifPresent(System.out::println);

# 6. ifPresent()
# Executes code only if a value exists.
Optional<String> opt = Optional.of("Java");

opt.ifPresent(System.out::println);
# Output: Java
# Example:
opt.ifPresent(name -> {
    System.out.println("Hello " + name);
});
# Output: Hello Java

# 7. map()
# Transforms the contained value.
Optional<String> opt = Optional.of("java");

Optional<String> upper = opt.map(String::toUpperCase);

System.out.println(upper.get());
# Output: JAVA
# Without Optional:
String result = name != null ? name.toUpperCase() : null;
# With Optional:
Optional<String> result =
        Optional.ofNullable(name)
                .map(String::toUpperCase);

# 8. flatMap()
# Used when the mapping function already returns an Optional.
# Problem with map()
Optional<String> name = Optional.of("Java");

Optional<Optional<String>> result =
    name.map(s -> Optional.of(s.toUpperCase()));
# Result: Optional[Optional[JAVA]]
# Nested Optional is undesirable.
# Solution: flatMap()
Optional<String> result =
    name.flatMap(s -> Optional.of(s.toUpperCase()));

System.out.println(result);
# Output: Optional[JAVA]
# Difference
map()     -> T → U
flatMap() -> T → Optional<U>

# 9. filter()
# Filters values based on a condition.
Optional<String> name = Optional.of("Aman");

Optional<String> result =
        name.filter(n -> n.length() > 3);

System.out.println(result);
# Output: Optional[Aman]
# Failing condition:
Optional<String> result =
        name.filter(n -> n.length() > 10);

System.out.println(result);
# Output: Optional.empty

# 10. orElse()
# Provides a default value if Optional is empty.
Optional<String> opt = Optional.empty();

String result = opt.orElse("Default");

System.out.println(result);
# Output: Default
# Non-empty:
Optional<String> opt = Optional.of("Java");

System.out.println(opt.orElse("Default"));
# Output: Java

# Important
# orElse() always evaluates its argument.
String name = opt.orElse(expensiveMethod());
# expensiveMethod() runs even when value exists.

# 11. orElseGet()
# Computes default value lazily.
String name =
        opt.orElseGet(() -> "Default");
# Example:
String result =
    Optional.of("Java")
            .orElseGet(() -> expensiveMethod());
# Here expensiveMethod() is not called.

# Difference
orElse(value)
# Eager evaluation.
orElseGet(() -> value)
# Lazy evaluation.

# 12. orElseThrow()
# Throws an exception when value is absent.
String name =
    Optional.of("Java")
            .orElseThrow();
# Output: Java
# Empty Optional: Optional.empty().orElseThrow();
# Output: NoSuchElementException
# Custom exception:
String name =
    Optional.empty()
            .orElseThrow(
                () -> new RuntimeException("Name not found")
            );
# Output: RuntimeException: Name not found

# 13. Appropriate Use in Return Types
# ✅ Good use of Optional.
public Optional<User> findUser(int id) {
    return Optional.ofNullable(database.get(id));
}
# Usage:
findUser(101)
    .ifPresent(System.out::println);
# Or:
User user =
    findUser(101)
    .orElse(new User());
# Why?
# - Clearly indicates result may not exist
# - Avoids returning null
# - Forces caller to think about absence

# 14. Avoiding Inappropriate Use
# Don't Use as Fields
# ❌ Bad
class Employee {
    Optional<String> name;
}
# ✅ Good
class Employee {
    String name;
}
# Reason:
# - Extra object creation
# - Serialization issues
# - Intended mainly for return types

# Don't Use as Method Parameters
# ❌ Bad
void printName(Optional<String> name) {
}
# Called as:
printName(Optional.of("Aman"));
# Unnecessary wrapping.
# ✅ Better
void printName(String name) {
}
# Handle null internally if needed.

# Don't Use in Collections
# ❌ Bad
List<Optional<String>> names;
# ✅ Good
List<String> names;
# Store actual values instead.

# Summary
Optional.of(value)            // Non-null value only
Optional.ofNullable(value)    // Null or non-null
Optional.empty()  

# Best Practice
# ✅ Use Optional primarily as a return type.
# ❌ Avoid using it:
# - As class fields
# - As method parameters
# - Inside collections
# A common modern pattern:
User user =
    findUser(id)
        .filter(User::isActive)
        .orElseThrow(
            () -> new RuntimeException("Active user not found")
        );
# - This is concise, null-safe, and idiomatic Java 8+.