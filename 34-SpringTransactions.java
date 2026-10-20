# Spring Transactions (Java + Spring Framework)
# Transactions ensure that a group of database operations either all succeed or all fail together, maintaining data consistency.

# 1. Transaction Fundamentals
# A transaction is a sequence of operations treated as a single unit of work.
# Example
# Transferring money:
public void transfer(Long fromId, Long toId, double amount) {
    debit(fromId, amount);
    credit(toId, amount);
}
# If debit() succeeds but credit() fails, the system becomes inconsistent.
# A transaction guarantees:
# - Both operations succeed → Commit
# - Any operation fails → Rollback

# 2. ACID Properties
# A - Atomicity
# All operations execute completely or none execute.
debit();
credit();
# Both succeed or both rollback.

# C - Consistency
# Database moves from one valid state to another.
# Example:
Before Transfer:
A = 1000
B = 500

After Transfer 100:
A = 900
B = 600
# Total money remains 1500.

# I - Isolation
# Concurrent transactions should not interfere with each other.
# Example:
User1 reads balance
User2 updates balance
User1 should not see inconsistent data

# D - Durability
# Once committed, data survives crashes.
transaction.commit();
# Data remains stored even after server restart.

# 3. @Transactional
# Spring provides declarative transaction management using:
@Transactional
public void transferMoney() {
    debit();
    credit();
}
# What Spring Does
# 1. Starts transaction
# 2. Executes method
# 3. Commits if successful
# 4. Rolls back on exception

# Service Example
@Service
public class AccountService {

    @Transactional
    public void transfer(Long from, Long to, double amount) {

        withdraw(from, amount);
        deposit(to, amount);
    }
}

# Transaction on Class
@Service
@Transactional
public class EmployeeService {
}
# All public methods become transactional.

# 4. Transaction Propagation
# Propagation determines what happens when a transactional method calls another transactional method.
# REQUIRED (Default)
@Transactional(propagation = Propagation.REQUIRED)
# Behavior:
# - Use existing transaction
# - Create new if none exists
A() --> B()
# Both share same transaction.

# REQUIRES_NEW
@Transactional(propagation = Propagation.REQUIRES_NEW)
# Always creates new transaction.
A() ---- Tx1

B() ---- Tx2
# If B fails:
Rollback Tx2
Tx1 can still commit

# SUPPORTS
@Transactional(propagation = Propagation.SUPPORTS)
# - Join existing transaction
# - Otherwise run without transaction

# NOT_SUPPORTED
@Transactional(propagation = Propagation.NOT_SUPPORTED)
# Runs outside transaction.

# NEVER
@Transactional(propagation = Propagation.NEVER)
# Throws exception if transaction exists.

# MANDATORY
@Transactional(propagation = Propagation.MANDATORY)
# Must have existing transaction.

# NESTED
@Transactional(propagation = Propagation.NESTED)
# Uses savepoints.
Outer Transaction
    |
    +-- Nested Transaction
# Nested rollback does not necessarily rollback outer transaction.

# 5. Isolation Levels
# Isolation controls visibility between concurrent transactions.
# READ_UNCOMMITTED
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
# Allows dirty reads.
# Problem
Tx1 updates value = 100
Tx2 reads 100

Tx1 rollback
# Tx2 read invalid data.

# READ_COMMITTED
# Most common.
# Preventional: ✅ Dirty reads
# Allows: ❌ Non-repeatable reads

# REPEATABLE_READ
# Guarantees same row returns same result.
Read row
Read row again
# Same value.
# Prevents:
# ✅ Dirty Reads
# ✅ Non-repeatable Reads

# SERIALIZABLE
# Highest isolation.
# Transactions execute as if one-by-one.
# Prevents:
# ✅ Dirty Reads
# ✅ Non-repeatable Reads
# ✅ Phantom Reads
# Most expensive.

# Phantom Read Example
SELECT * FROM employee;
# Returns 10 rows.
# Another transaction inserts row.
# Second query returns 11 rows.
# This extra row is phantom data.

# 6. Rollback Rules
# By default Spring rolls back only for:
RuntimeException
Error
# Default
@Transactional
public void save() {
    throw new RuntimeException();
}
# Rollback occurs.

# Checked Exception
@Transactional
public void save() throws Exception {
    throw new Exception();
}
# No rollback by default.

# Custom Rollback
@Transactional(
    rollbackFor = Exception.class
)
@Transactional(
    noRollbackFor = CustomException.class
)

# 7. Read-Only Transactions
# Used for query operations.
@Transactional(readOnly = true)
public List<Employee> getEmployees() {
    return repository.findAll();
}
# Benefits:
# - Optimization by JPA/Hibernate
# - Prevent accidental updates
# - Better performance

# 8. Transaction Boundaries
# A transaction begins when entering a transactional method and ends when method exits.
@Transactional
public void processOrder() {

    step1();
    step2();
    step3();
}
# Boundary:
BEGIN
 step1
 step2
 step3
COMMIT

# Exception:
BEGIN
 step1
 step2 -> Exception
ROLLBACK

# 9. Proxy-Based Transaction Management
# Spring transactions are implemented using AOP proxies.
Client
   |
 Proxy
   |
 Target Object
# Flow:
Client
 ↓
Transaction Proxy
 ↓
Actual Service
# Proxy:
# - Starts transaction
# - Commits
# - Rolls back

# Example:
@Service
public class EmployeeService {

    @Transactional
    public void saveEmployee() {
    }
}
# Spring creates proxy around service bean.

# 10. Self-Invocation Issues
# A common Spring interview question.
# Problem
@Service
public class EmployeeService {

    public void methodA() {
        methodB();
    }

    @Transactional
    public void methodB() {
    }
}
# Calling: methodA();
# does NOT trigger transaction for methodB().
# Reason: methodA -> methodB
# Call happens inside same object.
# Proxy is bypassed.

# Solution 1
# Move transactional method to another bean.
@Service
class TransactionService {

    @Transactional
    public void methodB() {
    }
}

# Solution 2
# Inject proxy bean.
@Autowired
private EmployeeService self;
self.methodB();

# 11. Nested Transactions
# Supported using:
Propagation.NESTED
# Example:
@Transactional
public void outer() {

    saveA();

    inner();

    saveB();
}
@Transactional(
   propagation = Propagation.NESTED
)
public void inner() {
}
# Spring creates a database savepoint.
Outer Tx
 |
 Savepoint
 |
 Inner Tx

# Inner rollback:
Rollback to savepoint
# Outer transaction may still commit.

# 12. Optimistic vs Pessimistic Locking
# Used to handle concurrent updates.
# Optimistic Locking
# Assumes conflicts are rare.
# Uses version column.
@Entity
public class Employee {

    @Version
    private Long version;
}
# Two users update same record:
User1 Version = 1
User2 Version = 1
# User1 saves:
Version -> 2
# User2 saves:
OptimisticLockException

# Advantages
# Better performance
# No locks held
# Scalable

# Pessimistic Locking
# Assumes conflicts are frequent.
# Locks row immediately.
@Lock(LockModeType.PESSIMISTIC_WRITE)
# Example:
@Query("SELECT e FROM Employee e WHERE e.id=:id")
@Lock(LockModeType.PESSIMISTIC_WRITE)
Employee findById(Long id);
# Other transactions must wait.

# Advantages
# Strong consistency
# Prevents concurrent modifications

# Disadvantages
# Deadlocks
# Lower throughput

# 13. Distributed Transaction Challenges
# Occurs when a transaction spans multiple systems.
# Example
Service A
   |
Database A

Service B
   |
Database B

# Money transfer:
DB1 update
DB2 update

# If one succeeds and another fails:
Inconsistent system

# Traditional Solution - 2PC (Two-Phase Commit)
# Phase 1
# Prepare
Can you commit?
# All participants vote.

# Phase 2
# Commit
Commit everyone
# or
Rollback everyone

# Problems:
# - Slow, Locking, Coordinator failure, Poor scalability

# Modern Microservice Approach
# Saga Pattern
# Break transaction into smaller local transactions.
Order Service
      ↓
Payment Service
      ↓
Inventory Service
# If a step fails:
Compensation Transaction
# Example:
Reserve Inventory
Charge Payment
Create Order
# Failure:
Refund Payment
Release Inventory

# Interview Questions
# Q1. Does @Transactional work on private methods?
# No. Spring proxies intercept public methods (and sometimes protected/package-private with CGLIB), but private methods cannot be proxied.

# Q2. Default propagation?
# Propagation.REQUIRED

# Q3. Default isolation?
# Uses database default isolation level.

# Q4. Does Spring rollback checked exceptions?
# No, only unchecked exceptions by default.
rollbackFor = Exception.class
# for checked exceptions.

# Q5. Why does self-invocation fail?
# Because internal method calls bypass Spring's transactional proxy.

# Q6. Optimistic or Pessimistic Locking for most applications?
# Usually Optimistic Locking because it offers better performance and scalability.