# Java Stream API 
# 1. Stream Fundamentals
# - A Stream is a sequence of elements that supports functional-style operations on collections.
# Features
# - Does not store data.
# - Works on collections, arrays, files, etc.
# - Supports filtering, mapping, reducing.
# - Processes data lazily.
# - Can be sequential or parallel.
List<String> names = List.of("Aman", "Raj", "Vijay");

names.stream()
     .forEach(System.out::println);

# 2. Creating Streams
# From Collection
List<Integer> nums = List.of(1, 2, 3, 4);
Stream<Integer> stream = nums.stream();
# From Array
int[] arr = {1, 2, 3};
IntStream stream = Arrays.stream(arr);
# Using Stream.of()
Stream<String> stream = Stream.of("A", "B", "C");
# Infinite Streams
Stream<Integer> stream =
        Stream.iterate(1, n -> n + 1);

# 3. stream() and parallelStream()
# Sequential Stream
list.stream()
    .forEach(System.out::println);
# Parallel Stream
list.parallelStream()
    .forEach(System.out::println);
# Uses multiple threads internally.

# 4. Intermediate vs Terminal Operations
# Intermediate Operations
# Return another stream.
# Examples:
filter()
map()
sorted()
distinct()
limit()

# Terminal Operations
# Produce final result.
# Examples:
collect()
count()
forEach()
reduce()

# 5. filter()
# Select elements matching a condition.
List<Integer> nums = List.of(1,2,3,4,5);

nums.stream()
    .filter(n -> n % 2 == 0)
    .forEach(System.out::println);
# Output:
2
4

# 6. map()
# Transforms elements.
List<String> names = List.of("java", "python");

names.stream()
     .map(String::toUpperCase)
     .forEach(System.out::println);
# Output:
JAVA
PYTHON

# 7. flatMap()
# Flattens nested structures.
List<List<Integer>> data =
        List.of(
            List.of(1,2),
            List.of(3,4)
        );

data.stream()
    .flatMap(List::stream)
    .forEach(System.out::println);
# Output:
1
2
3
4

# 8. distinct()
# Removes duplicates.
List<Integer> nums = List.of(1,1,2,3,3,4);

nums.stream()
    .distinct()
    .forEach(System.out::println);
# Output: 1 2 3 4

# 9. sorted()
# Sort elements.
nums.stream()
    .sorted()
    .forEach(System.out::println);
# Reverse order:
nums.stream()
    .sorted(Comparator.reverseOrder())
    .forEach(System.out::println);

# 10. limit() and skip()
# limit()
Stream.iterate(1, n -> n + 1)
      .limit(5)
      .forEach(System.out::println);
# Output: 1 2 3 4 5

# skip()
nums.stream()
    .skip(2)
    .forEach(System.out::println);

# 11. takeWhile() and dropWhile()
# (Java 9+)
# takeWhile()
List<Integer> nums = List.of(1,2,3,4,1,2);

nums.stream()
    .takeWhile(n -> n < 4)
    .forEach(System.out::println);
# Output: 1 2 3

# dropWhile()
nums.stream()
    .dropWhile(n -> n < 4)
    .forEach(System.out::println);
# Output: 4 1 2

# 12. peek()
# Used for debugging.
nums.stream()
    .peek(System.out::println)
    .count();

# 13. forEach()
# Terminal operation.
nums.stream()
    .forEach(System.out::println);

# 14. collect()
# Convert stream to collection.
List<Integer> result =
        nums.stream()
            .collect(Collectors.toList());

# 15. reduce()
# Combines elements into one value.
int sum = nums.stream()
              .reduce(0, Integer::sum);

System.out.println(sum);

# 16. count()
long total =
        nums.stream()
            .count();

# 17. min() and max()
Optional<Integer> min =
        nums.stream().min(Integer::compare);

Optional<Integer> max =
        nums.stream().max(Integer::compare);

# 18. findFirst() and findAny()
# findFirst()
Optional<Integer> first =
        nums.stream()
            .findFirst();
# findAny()
Optional<Integer> any =
        nums.parallelStream()
            .findAny();

# 19. anyMatch(), allMatch(), noneMatch()
boolean any =
        nums.stream()
            .anyMatch(n -> n > 4);

boolean all =
        nums.stream()
            .allMatch(n -> n > 0);

boolean none =
        nums.stream()
            .noneMatch(n -> n < 0);

# 20. Collectors.toList()
List<String> names =
        stream.collect(Collectors.toList());

# 21. Collectors.toMap()
Map<Integer, String> map =
    students.stream()
            .collect(Collectors.toMap(
              Student::getId,
              Student::getName
            ));

# 22. groupingBy()
# Group data by condition.
Map<Integer, List<String>> map =
    names.stream()
         .collect(Collectors.groupingBy(
            String::length
         ));
# Output:
{
  3=[Tom],
  4=[John],
  5=[Aman]
}

# 23. partitioningBy()
# Creates two groups (true/false).
Map<Boolean,List<Integer>> result =
    nums.stream()
        .collect(Collectors.partitioningBy(
            n -> n % 2 == 0
        ));

# 24. joining()
# Merge strings.
String result =
    names.stream()
         .collect(Collectors.joining(", "));
# Output: Aman, Raj, Vijay

# 25. mapping()
# Used inside collectors.
Map<Integer,List<String>> result =
    students.stream()
            .collect(Collectors.groupingBy(
                Student::getAge,
                Collectors.mapping(
                    Student::getName,
                    Collectors.toList()
                )
            ));

# 26. counting()
Long count =
    names.stream()
         .collect(Collectors.counting());

# 27. Primitive Streams
# IntStream
IntStream.range(1, 6)
         .forEach(System.out::println);
# LongStream
LongStream.range(1, 5);
# DoubleStream
DoubleStream.of(1.5, 2.5, 3.5);

# 28. Optional Values from Stream Operations
# Many operations return Optional.
Optional<Integer> result =
    nums.stream().findFirst();
# Safe handling:
result.ifPresent(System.out::println);
int value = result.orElse(0);

# 29. Lazy Evaluation
# Intermediate operations execute only when a terminal operation exists.
nums.stream()
    .filter(n -> {
        System.out.println(n);
        return n > 2;
    });
# Nothing executes.
nums.stream()
    .filter(n -> n > 2)
    .count();
# Now execution happens.

# 30. Stream Pipelines
# A pipeline consists of:
Source
   ↓
Intermediate Operations
   ↓
Terminal Operation
# Example:
nums.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * 10)
    .sorted()
    .collect(Collectors.toList());

# 31. Parallel Streams
nums.parallelStream()
    .map(n -> n * 2)
    .forEach(System.out::println);
# Useful for large datasets and CPU-intensive tasks.

# 32. Stream Performance
# Good For
# - Filtering
# - Mapping
# - Aggregation
# - Readability
# Avoid When
# - Heavy side effects
# - Very small datasets
# - Complex loops where streams add overhead

# 33. Avoiding Side Effects
# Bad
List<Integer> result = new ArrayList<>();

nums.stream()
    .forEach(result::add);
# Good
List<Integer> result =
    nums.stream()
        .collect(Collectors.toList());
# Streams should ideally be stateless and side-effect free.

# Common Stream Workflow
List<String> result =
    employees.stream()
             .filter(e -> e.getSalary() > 50000)
             .map(Employee::getName)
             .sorted()
             .collect(Collectors.toList());
# Flow
Collection
    ↓
stream()
    ↓
filter()
    ↓
map()
    ↓
sorted()
    ↓
collect()
    ↓
Result

# This is the most commonly used Stream API pattern in Java interviews and real projects.