# 13. Java Collections Framework (JCF)
# - The Java Collections Framework (JCF) is a set of interfaces, classes, and algorithms used to store and manipulate groups of objects efficiently.

# 1. Collection Hierarchy
Iterable
    |
Collection
    |
    +---- List
    |       +---- ArrayList
    |       +---- LinkedList
    |       +---- Vector
    |             +---- Stack
    |
    +---- Set
    |       +---- HashSet
    |       +---- LinkedHashSet
    |       +---- TreeSet
    |       +---- EnumSet
    |
    +---- Queue
            +---- PriorityQueue
            +---- LinkedList
            +---- Deque
                     +---- ArrayDeque

Map (Separate Hierarchy)
    |
    +---- HashMap
    +---- LinkedHashMap
    +---- TreeMap
    +---- Hashtable
    +---- ConcurrentHashMap
    +---- WeakHashMap
    +---- IdentityHashMap
    +---- EnumMap

# 2. Iterable Interface
# - Root interface for all collections.
public interface Iterable<T> {
    Iterator<T> iterator();
}

# - Used in enhanced for-loop:
List<String> list = List.of("A", "B", "C");

for(String s : list) {
    System.out.println(s);
}

# 3. Collection Interface
# - Basic operations:
add()
remove()
contains()
size()
isEmpty()
clear()

# Example:
Collection<String> names = new ArrayList<>();

names.add("John");
names.add("Mike");

System.out.println(names.size());

# 4. List Interface
# Characteristics:
# - Ordered
# - Duplicate elements allowed
# - Index-based access
List<String> list = new ArrayList<>();

list.add("A");
list.add("B");
list.add("A");

System.out.println(list); # [A, B, A]

# 5. ArrayList
# - Internal Structure
# Uses dynamic array.
List<Integer> numbers = new ArrayList<>();

numbers.add(10);
numbers.add(20);
numbers.add(30);

# Advantages
# - Fast random access
# - Less memory overhead

# Disadvantages
# - Slow insertion/deletion in middle

# Time Complexity
| Operation     | Complexity |
| ------------- | ---------- |
| get()         | O(1)       |
| add() end     | O(1)       |
| add(index)    | O(n)       |
| remove(index) | O(n)       |

# 6. LinkedList
# - Uses doubly linked list.
LinkedList<String> list = new LinkedList<>();

list.add("A");
list.add("B");

# Advantages
# - Fast insertions/deletions

# Disadvantages
# - Slow random access

# Complexity
| Operation     | Complexity |
| ------------- | ---------- |
| get(index)    | O(n)       |
| addFirst()    | O(1)       |
| removeFirst() | O(1)       |

# 7. Vector
# - Legacy class.
Vector<Integer> vector = new Vector<>();

vector.add(10);

# Features:
# - Synchronized
# - Thread-safe
# - Slower than ArrayList

# 8. Stack
# - LIFO structure.
Stack<Integer> stack = new Stack<>();

stack.push(10);
stack.push(20);

System.out.println(stack.pop()); # 20

# Why Deque is Preferred?
# - Stack is legacy.
# Use:
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);
stack.push(20);

stack.pop();

# Benefits:
# - Faster
# - Modern API
# - No unnecessary synchronization

# 9. Set Interface
# Characteristics:
# - No duplicates
# - Unordered (generally)
Set<Integer> set = new HashSet<>();

set.add(10);
set.add(10);

System.out.println(set); # [10]

# 10. HashSet
# - Backed by HashMap.
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");

# Features: Fast operations, No ordering guarantee
# Complexity:
Add     O(1)
Remove  O(1)
Search  O(1)

# 11. LinkedHashSet
# Maintains insertion order.
Set<String> set = new LinkedHashSet<>();

set.add("B");
set.add("A");
set.add("C");
# Output: [B, A, C]

# 12. TreeSet
# Sorted set.
TreeSet<Integer> set = new TreeSet<>();

set.add(50);
set.add(20);
set.add(10);
# Output: [10, 20, 50]
# Complexity: Add/Search/Remove = O(log n)
# Uses Red-Black Tree.

# 13. EnumSet
# Specialized set for enums.
enum Day {
    MON,TUE,WED
}

EnumSet<Day> days =
        EnumSet.of(Day.MON, Day.WED);
# Extremely fast and memory efficient.

# 14. Queue Interface
# FIFO structure.
Queue<String> queue =
        new LinkedList<>();

queue.offer("A");
queue.offer("B");

System.out.println(queue.poll()); # A 

# 15. PriorityQueue
# Elements ordered by priority.
PriorityQueue<Integer> pq =
        new PriorityQueue<>();

pq.offer(50);
pq.offer(10);
pq.offer(20);

System.out.println(pq.poll()); # 10
# Min-heap by default.
# Complexity:
Insertion O(log n)
Deletion  O(log n)

# 16. ArrayDeque
# - Double-ended queue.
Deque<Integer> dq =
        new ArrayDeque<>();

dq.addFirst(10);
dq.addLast(20);

# Supports:
addFirst()
addLast()
removeFirst()
removeLast()

# Can be used as: Queue, Stack, Deque

# 17. HashMap
# Stores key-value pairs.
Map<Integer,String> map =
        new HashMap<>();

map.put(1, "Java");
map.put(2, "Python");
# Features:
# - Unique keys
# - One null key allowed
# - Multiple null values allowed

# 18. LinkedHashMap
# Maintains insertion order.
Map<Integer,String> map =
        new LinkedHashMap<>();
# Output order remains insertion order.

# 19. TreeMap
# Sorted by keys.
TreeMap<Integer,String> map =
        new TreeMap<>();
# Output:
1=Java
2=Python
3=C++

# Complexity: O(log n)

# 20. Hashtable
# Legacy synchronized map.
Hashtable<Integer,String> table =
        new Hashtable<>();
# Differences: Thread-safe, No null key, No null value

# 21. ConcurrentHashMap
# Designed for multithreading.
ConcurrentHashMap<Integer,String> map =
        new ConcurrentHashMap<>();
# Advantages: High concurrency, Better than Hashtable

# 22. WeakHashMap
# Entries removed when key is no longer referenced.
WeakHashMap<Object,String> map =
        new WeakHashMap<>();
# Useful for caching.

# 23. IdentityHashMap
# Uses: ==
# instead of: equals()
IdentityHashMap<String,Integer> map =
        new IdentityHashMap<>();

# 24. EnumMap
# Map specifically for enum keys.
EnumMap<Day,String> map =
        new EnumMap<>(Day.class);
# Very fast.

# 25. Iterator
# Forward traversal.
Iterator<String> it =
        list.iterator();

while(it.hasNext()) {
    System.out.println(it.next());
}

# 26. ListIterator
# Bidirectional traversal.
ListIterator<String> it =
        list.listIterator();
# Methods:
next()
previous()
add()
set()
remove()

# 27. Fail-Fast Behavior
# Throws: ConcurrentModificationException
# Example:
for(String s : list) {
    list.remove(s);
}
# Correct:
Iterator<String> it =
        list.iterator();

while(it.hasNext()) {
    if(it.next().equals("A"))
        it.remove();
}

# 28. Comparable
# Natural ordering.
class Student
implements Comparable<Student> {

    int id;

    public int compareTo(Student s) {
        return this.id - s.id;
    }
}

# 29. Comparator
# Custom ordering.
Comparator<Student> byName =
    (s1,s2) ->
      s1.name.compareTo(s2.name);

# 30. Sorting Collections
# Collections.sort(list);
# Custom:
Collections.sort(list, comparator);
# Java 8:
list.sort(comparator);

# 31. Natural Ordering
# Defined using Comparable.
Collections.sort(students);

# 32. Custom Sorting
students.sort(
    Comparator.comparing(Student::getName)
);

# Descending:
students.sort(
    Comparator.comparing(Student::getName)
              .reversed()
);

# 33. equals() and hashCode()
# Rule: If two objects are equal, their hash codes must be equal.
# Incorrect:
class Employee {
    int id;
}
# Correct:
class Employee {

    int id;

    @Override
    public boolean equals(Object o) {
        return id == ((Employee)o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

# Used heavily by: HashSet, HashMap, Hashtable

# 34. Hash Collisions
# Collision occurs when: 
hashCode(A) == hashCode(B)
# but
!A.equals(B)
# Example:
"Aa".hashCode()
"BB".hashCode()
# may produce same hash.
# Handling: Bucket chaining, Tree nodes (Java 8+)

# 35. Internal Working of HashMap
# Step 1
# Generate hash.
hash(key)
# Step 2
# Calculate bucket index.
index = hash & (n-1)
# Step 3
# Store entry.
Node<K,V>
# Step 4
# Collision handling.
# Before Java 8:
Linked List
# After Java 8:
Linked List
      ↓
Red-Black Tree
# When bucket size becomes large (typically > 8).

# 36. Internal Working of ArrayList
# Uses dynamic array.
[10][20][30]
# When capacity full:
Old Capacity = 10
New Capacity = 15
# Approximately:
newCapacity = old + old/2
# (1.5x growth)
# Elements copied to new array.

# 37. Immutable Collections
# Cannot be modified.
# Java 9+
List<String> list =
    List.of("A","B","C");
list.add("D"); // Exception

# 38. Unmodifiable Collections
# Wrapper around existing collection.
List<String> list =
        new ArrayList<>();

List<String> readOnly =
        Collections.unmodifiableList(list);
# Difference: 
list.add("X");
# reflects inside readOnly.

# 39. Synchronized Collections
List<Integer> list =
Collections.synchronizedList(
    new ArrayList<>()
);
# Thread-safe wrapper.

# 40. Collection Time Complexity Cheat Sheet
| Collection    | Search   | Insert   | Delete   |
| ------------- | -------- | -------- | -------- |
| ArrayList     | O(1)*   | O(1) end | O(n)     |
| LinkedList    | O(n)     | O(1)     | O(1)     |
| HashSet       | O(1)     | O(1)     | O(1)     |
| TreeSet       | O(log n) | O(log n) | O(log n) |
| HashMap       | O(1)     | O(1)     | O(1)     |
| TreeMap       | O(log n) | O(log n) | O(log n) |
| PriorityQueue | O(n)     | O(log n) | O(log n) |

# * By index access.

# 41. Choosing the Right Collection
# Use ArrayList
# ✅ Frequent reads
# ✅ Random access

# Use LinkedList
# ✅ Frequent insertions/deletions

# Use HashSet
# ✅ Unique elements
# ✅ Fast lookups

# Use TreeSet
# ✅ Sorted unique data

# Use HashMap
# ✅ Fast key-value storage

# Use TreeMap
# ✅ Sorted keys

# Use ArrayDeque
# ✅ Stack operations
# ✅ Queue operations

# Use ConcurrentHashMap
# ✅ Multi-threaded applications

# Interview Questions
# Q1. ArrayList vs LinkedList?
| Feature    | ArrayList     | LinkedList         |
| ---------- | ------------- | ------------------ |
| Structure  | Dynamic Array | Doubly Linked List |
| Access     | O(1)          | O(n)               |
| Insert Mid | O(n)          | O(1)*             |
| Memory     | Less          | More               |

# * By index access.

# Q2. HashMap vs Hashtable?
| HashMap          | Hashtable    |
| ---------------- | ------------ |
| Not synchronized | Synchronized |
| Allows null      | No null      |
| Faster           | Slower       |

# Q3. Comparable vs Comparator?
| Comparable   | Comparator    |
| ------------ | ------------- |
| Natural sort | Custom sort   |
| compareTo()  | compare()     |
| Inside class | Outside class |

# Q4. HashSet vs TreeSet?
| HashSet    | TreeSet        |
| ---------- | -------------- |
| Unordered  | Sorted         |
| O(1)       | O(log n)       |
| Hash table | Red-Black Tree |

# This covers the complete Java Collections Framework foundation required for interviews, Java development, and advanced topics like Streams, Concurrency, and Spring Framework.