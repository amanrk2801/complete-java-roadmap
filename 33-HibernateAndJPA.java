# Hibernate and JPA 
# 1. ORM Fundamentals
# ORM (Object Relational Mapping) maps Java objects to database tables.
# Without ORM
String sql = "SELECT * FROM users WHERE id = 1";
# You manually:
# - Write SQL
# - Create connections
# - Map ResultSet to objects
# With ORM
User user = entityManager.find(User.class, 1L);
# Hibernate automatically:
# - Creates SQL
# - Executes queries
# - Maps rows to Java objects

# 2. JPA Specification vs Hibernate Implementation
# JPA
# - Specification (set of rules/interfaces)
# - Defines how ORM should work
# - Provided by Jakarta EE
# Hibernate
# - Most popular JPA implementation
# - Actually performs ORM operations
# Analogy
JPA        = Interface
Hibernate  = Implementation
@Entity
public class User {
}
# This annotation comes from JPA.
# Hibernate reads it and executes the work.

# 3. Entities
# An entity is a Java class mapped to a database table.
@Entity
public class Student {

    @Id
    private Long id;

    private String name;
}
# Table:
students
---------
id
name

# 4. Entity Lifecycle
# States
New (Transient)
      ↓
Managed (Persistent)
      ↓
Detached
      ↓
Removed

# Example
Student s = new Student(); // Transient

entityManager.persist(s); // Managed

entityManager.detach(s); // Detached

entityManager.remove(s); // Removed

# 5. @Entity
# Marks class as a JPA entity.
@Entity
public class Employee {
}

# 6. @Id
# Marks primary key.
@Entity
public class Employee {

    @Id
    private Long id;
}

# 7. Primary Key Generation
# AUTO
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
# IDENTITY
@GeneratedValue(strategy = GenerationType.IDENTITY)
# - Uses database auto increment.
# SEQUENCE
@GeneratedValue(strategy = GenerationType.SEQUENCE)
# TABLE
@GeneratedValue(strategy = GenerationType.TABLE)

# 8. @Column
# Customize column mapping.
@Column(name = "employee_name",
        nullable = false,
        length = 50)
private String name;

# 9. @Table
# Customize table mapping.
@Entity
@Table(name = "employees")
public class Employee {
}

# 10. One-to-One Relationship
# Example
# Employee ↔ Passport
@Entity
public class Employee {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;
}
@Entity
public class Passport {

    @Id
    private Long id;
}

# 11. One-to-Many Relationship
# Department → Employees
@Entity
public class Department {

    @Id
    private Long id;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}

# 12. Many-to-One Relationship
# Many employees belong to one department.
@Entity
public class Employee {

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

# 13. Many-to-Many Relationship
# Students ↔ Courses
@Entity
public class Student {

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}

# 14. Owning Side and Inverse Side
# Owning Side
# Contains: @JoinColumn
# or
# @JoinTable

# Inverse Side
Contains: mappedBy
# Example:
@OneToMany(mappedBy = "department")
private List<Employee> employees;
# Department is inverse side.
# Employee is owning side.

# 15. mappedBy
# Avoids duplicate foreign keys.
@OneToMany(mappedBy = "department")
private List<Employee> employees;
# Here: private Department department;
# owns the relationship.

# 16. Cascade Types
@OneToMany(
    mappedBy = "department",
    cascade = CascadeType.ALL
)
private List<Employee> employees;
# Types:
CascadeType.PERSIST
CascadeType.MERGE
CascadeType.REMOVE
CascadeType.REFRESH
CascadeType.DETACH
CascadeType.ALL

# 17. Orphan Removal
# Deletes child entity if removed from collection.
@OneToMany(
    mappedBy = "department",
    orphanRemoval = true
)
private List<Employee> employees;
department.getEmployees().remove(emp);
# Employee gets deleted.

# 18. Lazy vs Eager Loading
# LAZY
# Load only when needed.
@OneToMany(fetch = FetchType.LAZY)
# Query 1:
SELECT * FROM department;
# Employees fetched later.

# EAGER
# Load immediately.
@OneToMany(fetch = FetchType.EAGER)
# Both tables loaded instantly.

# 19. N+1 Query Problem
# Suppose:
List<Department> departments =
    repository.findAll();
# 1 query loads departments.
# Then each department loads employees.
1 + N queries
# Example:
1 Department query
100 Employee queries

Total = 101 queries
# Performance issue.

# 20. Fetch Joins
# Solution to N+1.
@Query("""
SELECT d
FROM Department d
JOIN FETCH d.employees
""")
List<Department> findAllWithEmployees();
# Single SQL query.

# 21. Entity Graphs
# Alternative to fetch joins.
@EntityGraph(attributePaths = "employees")
List<Department> findAll();
# Controls fetch plan.

# 22. JPQL
# JPA Query Language.
# Uses entity names, not table names.
SELECT e FROM Employee e
# Example:
TypedQuery<Employee> query =
entityManager.createQuery(
    "SELECT e FROM Employee e",
    Employee.class
);

# 23. Native SQL
# Use actual SQL.
@Query(
 value = "SELECT * FROM employees",
 nativeQuery = true
)
List<Employee> findAllEmployees();

# 24. Criteria API
# Dynamic query building.
CriteriaBuilder cb =
    entityManager.getCriteriaBuilder();

CriteriaQuery<Employee> cq =
    cb.createQuery(Employee.class);

Root<Employee> root =
    cq.from(Employee.class);

cq.select(root);

List<Employee> result =
entityManager.createQuery(cq)
             .getResultList();

# 25. Spring Data JPA
# Reduces boilerplate.
# Without Spring Data:
entityManager.persist(employee);
# With Spring Data:
employeeRepository.save(employee);

# 26. JpaRepository
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {
}

# Provides:
save()
findById()
findAll()
delete()
count()
existsById()

# 27. Derived Query Methods
# Spring generates query automatically.
List<Employee> findByName(String name);
# Generated JPQL automatically.
Employee findByEmail(String email);
List<Employee> findBySalaryGreaterThan(
        Double salary);

# 28. @Query
# Custom queries.
@Query("""
SELECT e
FROM Employee e
WHERE e.salary > :salary
""")
List<Employee> findRichEmployees(
    @Param("salary") Double salary);

# 29. Specifications
# Dynamic filtering.
public class EmployeeSpecification {

    public static Specification<Employee>
    hasName(String name) {

        return (root, query, cb) ->
            cb.equal(root.get("name"), name);
    }
}
# Usage:
repository.findAll(
    EmployeeSpecification.hasName("John")
);

# 30. Projections
# Fetch only required fields.
# Interface Projection
public interface EmployeeView {

    String getName();
    String getEmail();
}
List<EmployeeView> findAllProjectedBy();

# 31. Pagination
Page<Employee> page =
repository.findAll(
    PageRequest.of(0, 10)
);
# Page 0 = first page
# 10 = size

# 32. Optimistic Locking
# Uses version column.
@Entity
public class Product {

    @Version
    private Long version;
}
# If another user updates same row:
OptimisticLockException
# thrown.

# 33. Pessimistic Locking
# Locks record.
@Lock(LockModeType.PESSIMISTIC_WRITE)
Optional<Employee> findById(Long id);
# Database row locked until transaction ends.

# 34. First-Level and Second-Level Cache
# First-Level Cache
# Per Persistence Context.
Employee e1 =
entityManager.find(Employee.class, 1L);

Employee e2 =
entityManager.find(Employee.class, 1L);
# SQL executes once.
# Second-Level Cache
# Shared across sessions.
# Common provider:
Ehcache
Hazelcast
Infinispan

# 35. Persistence Context
# Collection of managed entities.
EntityManager
# manages:
Create
Update
Delete
Tracking
Caching

# 36. Dirty Checking
# Hibernate automatically detects changes.
Employee emp =
entityManager.find(
    Employee.class,1L);

emp.setName("Aman");
# No update query written manually.
# At commit:
UPDATE employee
SET name='Aman'
WHERE id=1;
# generated automatically.

# 37. Entity Auditing
# Track creation/update information.
@Entity
@EntityListeners(
    AuditingEntityListener.class
)
public class Employee {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
# Enable auditing: @EnableJpaAuditing

# 38. Database Migrations (Flyway/Liquibase)
# Flyway
# Migration file:
V1__create_employee_table.sql

CREATE TABLE employee(
    id BIGINT PRIMARY KEY,
    name VARCHAR(100)
);
# On startup:
V1
V2
V3
# executed automatically in order.

# Liquibase
# XML Example:
<changeSet id="1" author="admin">
    <createTable tableName="employee">
        <column name="id" type="BIGINT"/>
        <column name="name" type="VARCHAR(100)"/>
    </createTable>
</changeSet>

# Interview Questions
# Q1: Difference between JPA and Hibernate?
# JPA is a specification, Hibernate is its implementation.

# Q2: What is Persistence Context?
# Collection of managed entities maintained by EntityManager.

# Q3: What causes N+1 Problem?
# Lazy loading causing one parent query and N child queries.

# Q4: Difference between Fetch Join and Entity Graph?
# Fetch Join → JPQL based
# Entity Graph → Annotation/configuration based

# Q5: What is Dirty Checking?
# Hibernate automatically detects modified entities and updates the database.

# Q6: Difference between Optimistic and Pessimistic Locking?
# Optimistic: Version-based conflict detection
# Pessimistic: Database row lock

# These 38 topics cover most Hibernate, JPA, and Spring Data JPA concepts commonly asked in Java backend interviews.