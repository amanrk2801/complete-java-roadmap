# Multithreading and Concurrency (Java)
# This is one of the most important Java topics for interviews and real-world backend development.

# 1. Process vs Thread
# Process
# A process is an independent program in execution.
# Examples: Chrome browser, IntelliJ IDEA, VS Code
# Characteristics:
# - Own memory space
# - Heavyweight
# - Inter-process communication is expensive

# Thread
# A thread is a lightweight unit of execution within a process.
# Characteristics:
# - Shares process memory
# - Faster creation
# - Can run concurrently
Process
 ├── Thread-1
 ├── Thread-2
 └── Thread-3

# 2. Thread Lifecycle
NEW
 ↓
RUNNABLE
 ↓
RUNNING
 ↓
BLOCKED / WAITING / TIMED_WAITING
 ↓
TERMINATED

# States:
| State          | Description             |
| -------------- | ----------------------- |
| NEW            | Created but not started |
| RUNNABLE       | Ready to run            |
| RUNNING        | Executing               |
| BLOCKED        | Waiting for lock        |
| WAITING        | Waiting indefinitely    |
| TIMED\_WAITING | Waiting for fixed time  |
| TERMINATED     | Finished                |

# 3. Creating Threads
# Method 1: Extend Thread
class MyThread extends Thread {
    public void run() {
        System.out.println("Running...");
    }
}

public class Main {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}

# Method 2: Implement Runnable
class Task implements Runnable {
    public void run() {
        System.out.println("Task running");
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t = new Thread(new Task());
        t.start();
    }
}

# Preferred because Java allows only single inheritance.

# 4. Thread Class
# Common methods:
start()
run()
sleep()
join()
interrupt()
setPriority()
getName()

# Example: Thread.currentThread().getName();

# 5. Runnable
# Functional interface:
@FunctionalInterface
public interface Runnable {
    void run();
}
# Example:
Runnable r = () -> {
    System.out.println("Hello");
};

new Thread(r).start();

# 6. Callable
# Unlike Runnable:
# - Returns value
# - Can throw exceptions
Callable<Integer> task = () -> 100;

# 7. Future
# Represents result of asynchronous computation.
ExecutorService service =
        Executors.newSingleThreadExecutor();

Future<Integer> future =
        service.submit(() -> 20);

System.out.println(future.get());
# Output: 20

# 8. FutureTask
# Combination of:
Runnable + Future

# Example:
Callable<Integer> task = () -> 50;

FutureTask<Integer> ft =
        new FutureTask<>(task);

Thread thread = new Thread(ft);
thread.start();

System.out.println(ft.get());

# 9. Thread Priorities
# Range: 1 to 10
# Constants:
MIN_PRIORITY = 1
NORM_PRIORITY = 5
MAX_PRIORITY = 10

# Example: thread.setPriority(Thread.MAX_PRIORITY);
# Note: Priority is only a scheduling hint.

# 10. Daemon Threads
# Background threads.
# Examples:
# - Garbage Collector
# - Monitoring services
Thread t = new Thread(task);

t.setDaemon(true);

t.start();
# JVM exits when only daemon threads remain.

# 11. Interrupts
# Used to stop waiting/blocking operations.
thread.interrupt();
# Example:
try {
    Thread.sleep(5000);
} catch (InterruptedException e) {
    System.out.println("Interrupted");
}

# 12. sleep(), join(), yield()
# sleep()
# Pauses thread.
Thread.sleep(1000);

# join()
# Wait for another thread.
thread.start();
thread.join();

# yield()
# Gives scheduler a chance to execute another thread.
Thread.yield();

# Synchronization
# 13. Race Condition
# Occurs when multiple threads update shared data simultaneously.
# Example:
count++;
# Possible:
Expected = 2000
Actual   = 1785

# 14. Critical Section
# Code accessing shared resources.
balance = balance + amount;
# Must be synchronized.

# 15. synchronized Methods and Blocks
# Method Synchronization
public synchronized void increment() {
    count++;
}

# Block Synchronization
synchronized(this) {
    count++;
}

# 16. Intrinsic Locks
# Every object has a monitor lock.
synchronized(obj) {
    // lock acquired
}
# Only one thread can enter.

# 17. volatile
# Ensures visibility across threads.
volatile boolean running = true;
# Without volatile:
Thread may read stale value
# With volatile:
Latest value always visible

# 18. Atomicity, Visibility, Ordering
# Atomicity
# Operation happens fully or not at all.
AtomicInteger.incrementAndGet();

# Visibility
# Changes are visible to all threads.
volatile

# Ordering
# Instructions execute in expected order.
# Handled by:
synchronized
volatile
locks

# 19. Deadlock
# Two threads waiting forever.
Thread1:
 lockA -> lockB

Thread2:
 lockB -> lockA

# Result: Deadlock

# 20. Livelock
# Threads keep responding but no progress.
# Example:
Both workers continuously give way
to each other.

# 21. Starvation
# Thread never gets CPU or lock.
# Example:
Low priority thread
never scheduled.

# 22. wait(), notify(), notifyAll()
# Used for thread communication.
# wait()
obj.wait();
# Releases lock.

# notify()
obj.notify();
# Wakes one waiting thread.

# notifyAll()
obj.notifyAll();
# Wakes all waiting threads.

# 23. Thread Safety
# Code behaves correctly under concurrent access.
# Methods:
# - Synchronization
# - Atomic variables
# - Immutable objects
# - Concurrent collections

# 24. Immutable Objects
# - Naturally thread-safe.
# Example:
String
Integer
LocalDate

# Custom immutable:
final class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
}

# Concurrency Utilities
# 25. Executor Framework
# Manages thread creation and scheduling.
ExecutorService service =
      Executors.newFixedThreadPool(5);
# Benefits:
# - Reuse threads
# - Better performance
# - Simpler code

# 26. Executor
# Basic interface.
Executor executor =
    Executors.newFixedThreadPool(3);

executor.execute(task);

# 27. ExecutorService
# Advanced executor.
ExecutorService service =
        Executors.newFixedThreadPool(4);
# Methods:
submit()
shutdown()
shutdownNow()
invokeAll()
invokeAny()

# 28. ScheduledExecutorService
# Schedules tasks.
ScheduledExecutorService scheduler =
        Executors.newScheduledThreadPool(1);

scheduler.schedule(
        task,
        5,
        TimeUnit.SECONDS
);

# 29. Thread Pools
# Types:
newFixedThreadPool()
newCachedThreadPool()
newSingleThreadExecutor()
newScheduledThreadPool()

# 30. ThreadPoolExecutor
# Core implementation.
ThreadPoolExecutor executor =
   new ThreadPoolExecutor(
      2,
      5,
      60,
      TimeUnit.SECONDS,
      new LinkedBlockingQueue<>()
   );

# 31. CompletableFuture
# Modern asynchronous programming.
CompletableFuture
        .supplyAsync(() -> "Hello")
        .thenApply(String::toUpperCase)
        .thenAccept(System.out::println);

# Output: HELLO

# 32. CountDownLatch
# Waits until tasks complete.
CountDownLatch latch =
        new CountDownLatch(3);

latch.await();

# 33. CyclicBarrier
# All threads wait for each other.
CyclicBarrier barrier =
        new CyclicBarrier(3);
# When 3 threads reach barrier:
Continue together

# 34. Semaphore
# Controls access count.
Semaphore sem =
        new Semaphore(3);
# Only 3 threads allowed simultaneously.

# 35. Phaser
# Advanced synchronization.
# Supports dynamic participants.
Phaser phaser = new Phaser(3);

# 36. ReentrantLock
# Alternative to synchronized.
Lock lock = new ReentrantLock();

lock.lock();

try {
   count++;
}
finally {
   lock.unlock();
}
# Advantages:
# - Fair locking
# - tryLock()
# - Interruptible locks

# 37. ReadWriteLock
# Multiple readers, one writer.
ReadWriteLock lock =
        new ReentrantReadWriteLock();
# Read: lock.readLock().lock();
# Write: lock.writeLock().lock();

# 38. Condition
# Replacement for wait/notify.
Condition condition =
        lock.newCondition();
# Methods:
await()
signal()
signalAll()

# 39. Atomic Variables
# Lock-free thread-safe operations.
AtomicInteger count =
        new AtomicInteger(0);

count.incrementAndGet();
# Types:
AtomicInteger
AtomicLong
AtomicBoolean
AtomicReference

# 40. Concurrent Collections
# Thread-safe collections.
# Examples:
ConcurrentHashMap
CopyOnWriteArrayList
ConcurrentLinkedQueue

# 41. Blocking Queues
# Used in Producer-Consumer pattern.
BlockingQueue<Integer> queue =
        new ArrayBlockingQueue<>(10);
# Methods:
put()
take()

# 42. Fork/Join Framework
# Splits large task into subtasks.
ForkJoinPool pool =
        new ForkJoinPool();

# 43. Work Stealing
# Idle threads steal tasks from busy threads.
# Improves CPU utilization.
# Used by: ForkJoinPool

# 44. Java Memory Model (JMM)
# Defines:
# - Thread interaction
# - Visibility
# - Ordering
# - Atomicity
# Ensures predictable behavior across CPUs.

# 45. Happens-Before Relationship
# Guarantees visibility.
# Examples:
unlock happens-before lock
write to volatile
happens-before
read of volatile
thread.start()
# happens-before all actions in thread.

# 46. Virtual Threads (Java 21+)
# Lightweight threads introduced by Project Loom.
Thread.startVirtualThread(() -> {
    System.out.println("Virtual Thread");
});
# Advantages:
# - Millions of threads
# - Low memory usage
# - Great for I/O operations

# 47. Structured Concurrency Concepts
# Treat concurrent tasks as one unit of work.
# Traditional:
Task A
Task B
Task C
# Hard to manage failures.
# Structured concurrency:
Parent Task
 ├─ Child A
 ├─ Child B
 └─ Child C
# If one fails:
All related tasks handled together
# Benefits:
# - Better cancellation
# - Better error handling
# - Easier debugging

# Interview Questions
# Q1. Difference between Runnable and Callable?
| Runnable             | Callable            |
| -------------------- | ------------------- |
| run()                | call()              |
| No return value      | Returns value       |
| No checked exception | Can throw exception |

# Q2. synchronized vs ReentrantLock?
| synchronized        | ReentrantLock    |
| ------------------- | ---------------- |
| Simpler             | More flexible    |
| JVM managed         | API managed      |
| No tryLock()        | Has tryLock()    |
| No fairness control | Fairness support |

# Q3. volatile vs synchronized?
| volatile        | synchronized           |
| --------------- | ---------------------- |
| Visibility only | Visibility + Atomicity |
| Non-blocking    | Uses lock              |
| Fast            | Slower                 |

# Q4. Deadlock vs Starvation?
# Deadlock: Threads wait forever for each other.
# Starvation: Thread never gets required resource even though system is running.

# Q5. What is CompletableFuture?
# A powerful API for asynchronous, non-blocking programming that supports task chaining, combining, and exception handling.
CompletableFuture.supplyAsync(() -> 10)
                 .thenApply(x -> x * 2)
                 .thenAccept(System.out::println);
# Output: 20

# This covers the complete Java Multithreading, Synchronization, and Concurrency Utilities roadmap from basic thread creation to modern features like Virtual Threads and Structured Concurrency.
