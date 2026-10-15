# Arrays (Java)
# Arrays are used to store multiple values of the same data type in a single variable. Once created, the size of an array cannot be changed.

# 1. Array Declaration and Initialization
# Declaration: int[] numbers;
# Initialization: numbers = new int[5];
# Declaration + Initialization: int[] numbers = {10, 20, 30, 40, 50};

# 2. Single-Dimensional Arrays
# A one-dimensional array stores elements in a single row.
public class Main {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50};

        System.out.println(arr[0]); // 10
        System.out.println(arr[2]); // 30
    }
}
# Memory Representation:
# Index : 0 1 2 3 4 
# Value :10 20 30 40 50

# 3. Multidimensional Arrays
# Arrays containing other arrays.
# 2D Array
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

# Accessing elements: System.out.println(matrix[1][2]); // 6
# Traversal: 
for (int i = 0; i < matrix.length; i++) {
    for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
    }
    System.out.println();
}

# 4. Jagged Arrays
# - Arrays where each row can have different lengths.
int[][] jagged = {
    {1, 2},
    {3, 4, 5},
    {6, 7, 8, 9}
};

# Traversal:
for (int i = 0; i < jagged.length; i++) {
    for (int j = 0; j < jagged[i].length; j++) {
        System.out.print(jagged[i][j] + " ");
    }
    System.out.println();
}

# 5. Array Indexing
# - Array indexing starts from 0.
int[] arr = {100, 200, 300};

System.out.println(arr[0]); // 100
System.out.println(arr[1]); // 200
System.out.println(arr[2]); // 300

# Invalid index: System.out.println(arr[3]);
# Output: ArrayIndexOutOfBoundsException

# 6. Array Traversal
# Using for loop
int[] arr = {10, 20, 30, 40};

for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}

# Enhanced for-each loop
for (int num : arr) {
    System.out.println(num);
}

# 7. Array Copying
# Manual Copy
int[] source = {1, 2, 3};
int[] dest = new int[source.length];

for (int i = 0; i < source.length; i++) {
    dest[i] = source[i];
}

# Using clone()
int[] copy = source.clone();

# Using System.arraycopy()
System.arraycopy(source, 0, dest, 0, source.length);

# Using Arrays.copyOf()
import java.util.Arrays;

int[] copy = Arrays.copyOf(source, source.length);

# 8. Passing Arrays to Methods
public class Main {

    static void display(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    public static void main(String[] args) {
        int[] numbers = {10, 20, 30};
        display(numbers);
    }
}

# Output: 10 20 30

# 9. Arrays of Objects
class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}

public class Main {
    public static void main(String[] args) {

        Student[] students = {
            new Student("John"),
            new Student("Alice"),
            new Student("Bob")
        };

        for (Student s : students) {
            System.out.println(s.name);
        }
    }
}

# Output: John Alice Bob

# 10. Arrays Utility Class
# - java.util.Arrays provides useful methods.
import java.util.Arrays;

int[] arr = {5, 2, 1, 4, 3};

Arrays.sort(arr);
System.out.println(Arrays.toString(arr));

# Output: [1, 2, 3, 4, 5]

# Common Methods:
| Method                | Purpose                 |
| --------------------- | ----------------------- |
| Arrays.sort()         | Sort array              |
| Arrays.toString()     | Convert array to string |
| Arrays.fill()         | Fill with value         |
| Arrays.copyOf()       | Copy array              |
| Arrays.equals()       | Compare arrays          |
| Arrays.binarySearch() | Search in sorted array  |

# 11. Sorting and Searching Arrays
# Sorting
int[] arr = {40, 10, 30, 20};

Arrays.sort(arr);

System.out.println(Arrays.toString(arr));

# Output: [10, 20, 30, 40]

# Binary Search
int[] arr = {10, 20, 30, 40, 50};

int index = Arrays.binarySearch(arr, 30);

System.out.println(index);

# Output: 2

# 12. Arrays.equals() and Arrays.deepEquals()
# - Arrays.equals()
# - Used for one-dimensional arrays.
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};

System.out.println(Arrays.equals(a, b));

# Output: true

# Arrays.deepEquals()
# - Used for multidimensional arrays.
int[][] a = {{1, 2}, {3, 4}};
int[][] b = {{1, 2}, {3, 4}};

System.out.println(Arrays.deepEquals(a, b));

# Output: true

# 13. Array Limitations
# 1. Fixed size after creation.
# 2. Can store only the same data type.
# 3. Insertion and deletion are costly.
# 4. No built-in methods except through Arrays utility class.
# 5. May waste memory if allocated size is larger than needed.

int[] arr = new int[1000];
# - If only 10 elements are used, remaining memory is wasted.

# 14. Arrays vs Collections
| Feature          | Arrays               | Collections     |
| ---------------- | -------------------- | --------------- |
| Size             | Fixed                | Dynamic         |
| Data Types       | Primitives & Objects | Objects only    |
| Performance      | Faster               | Slightly slower |
| Built-in Methods | Limited              | Many methods    |
| Memory Usage     | Less                 | More            |
| Flexibility      | Low                  | High            |

# Array Example
int[] arr = {10, 20, 30};

# ArrayList Example
import java.util.ArrayList;

ArrayList<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
list.add(30);

# Interview Questions
# 1. What is an array?
# An array is a collection of elements of the same type stored in contiguous memory locations.

# 2. What is the default value of an int array?
# 0

# 3. What is the default value of a boolean array?
# false

# 4. Difference between Arrays.equals() and Arrays.deepEquals()?
# Arrays.equals() → 1D arrays
# Arrays.deepEquals() → Multidimensional arrays

# 5. Can array size be changed after creation?
# No, Java arrays have a fixed size.

# 6. Which is better: Array or ArrayList?
# Use Array when size is fixed.
# Use ArrayList when size changes dynamically.

# Quick Summary
Array
│
├── Single Dimensional
├── Multidimensional
├── Jagged Array
├── Indexing
├── Traversal
├── Copying
├── Passing to Methods
├── Arrays of Objects
├── Arrays Utility Class
├── Sorting & Searching
├── equals() / deepEquals()
├── Limitations
└── Arrays vs Collections