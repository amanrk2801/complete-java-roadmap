# Unit Testing and Quality (Java)
# Unit Testing and Quality is a crucial part of software development that ensures code correctness, reliability, maintainability, and early bug detection.

# 1. Unit Testing Fundamentals
# What is Unit Testing?
# Unit Testing is the process of testing individual units (methods, functions, classes) of an application independently.
# Benefits
# - Detects bugs early
# - Improves code quality
# - Facilitates refactoring
# - Documents behavior
# - Increases confidence in code
# Example
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
# Test:
@Test
void testAdd() {
    Calculator calc = new Calculator();
    assertEquals(5, calc.add(2, 3));
}

# 2. JUnit 5
# JUnit 5 is the modern Java testing framework.
# Maven Dependency
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.x.x</version>
    <scope>test</scope>
</dependency>

# Common Annotations
| Annotation  | Purpose                    |
| ----------- | -------------------------- |
| @Test       | Marks test method          |
| @BeforeEach | Runs before every test     |
| @AfterEach  | Runs after every test      |
| @BeforeAll  | Runs once before all tests |
| @AfterAll   | Runs once after all tests  |
| @Disabled   | Skip test                  |

# Example:
@Test
void sampleTest() {
    assertEquals(4, 2 + 2);
}

# 3. Test Lifecycle
# Lifecycle controls setup and cleanup.
class UserServiceTest {

    @BeforeAll
    static void initAll() {
        System.out.println("Start Tests");
    }

    @BeforeEach
    void init() {
        System.out.println("Before Test");
    }

    @Test
    void testOne() {}

    @AfterEach
    void cleanup() {
        System.out.println("After Test");
    }

    @AfterAll
    static void finish() {
        System.out.println("End Tests");
    }
}

# Execution:
BeforeAll
BeforeEach
Test
AfterEach
AfterAll

# 4. Assertions
# Assertions verify expected outcomes.
# Common Assertions
assertEquals(10, result);

assertNotEquals(5, result);

assertTrue(condition);

assertFalse(condition);

assertNull(value);

assertNotNull(value);

assertSame(obj1, obj2);

assertNotSame(obj1, obj2);

# Grouped Assertions
assertAll(
    () -> assertEquals("John", user.getName()),
    () -> assertEquals(25, user.getAge())
);

# 5. Parameterized Tests
# Run the same test with multiple inputs.
@ParameterizedTest
@ValueSource(ints = {1,2,3,4})
void testPositiveNumbers(int number) {
    assertTrue(number > 0);
}
# CSV Source
@ParameterizedTest
@CsvSource({
    "2,3,5",
    "5,5,10"
})
void addTest(int a, int b, int expected) {
    assertEquals(expected, a+b);
}

# 6. Nested Tests
# Organize related tests.
class UserServiceTest {

    @Nested
    class UserCreation {

        @Test
        void createUser() {
        }
    }

    @Nested
    class UserDeletion {

        @Test
        void deleteUser() {
        }
    }
}

# 7. Exception Testing
# Verify exception behavior.
@Test
void testException() {

    Exception ex = assertThrows(
        IllegalArgumentException.class,
        () -> {
            throw new IllegalArgumentException();
        }
    );

    assertEquals(null, ex.getMessage());
}
# Verify no exception:
assertDoesNotThrow(() -> service.process());

# 8. Mockito
# Mockito is a mocking framework for unit testing.
# Dependency
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
# Purpose:
# - Mock dependencies
# - Isolate unit under test
# - Verify interactions

# 9. Mock, Stub, Spy
# Mock
# Fake object with behavior verification.
List<String> mockList = mock(List.class);

# Stub
# Returns predefined values.
when(userRepository.findById(1L))
        .thenReturn(user);

# Spy
# Partial mock.
ArrayList<String> list = new ArrayList<>();

List<String> spyList = spy(list);

spyList.add("Java");

verify(spyList).add("Java");

# Difference
| Type | Purpose                  |
| ---- | ------------------------ |
| Mock | Verify interactions      |
| Stub | Provide test data        |
| Spy  | Partial real object mock |

# 10. Argument Matchers
# Used when exact values are not important.
when(repo.find(anyLong()))
        .thenReturn(user);
# Common Matchers:
any()
anyString()
anyInt()
anyLong()
eq()
isNull()
notNull()
# Example:
verify(repo).save(any(User.class));

# 11. Capturing Arguments
# Capture parameters passed to methods.
ArgumentCaptor<User> captor =
    ArgumentCaptor.forClass(User.class);

verify(repo).save(captor.capture());

User savedUser = captor.getValue();

assertEquals("John",
             savedUser.getName());

# 12. Mocking Dependencies
# Service:
class UserService {

    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User getUser(Long id) {
        return repo.findById(id);
    }
}
# Test:
@Mock
private UserRepository repo;

@InjectMocks
private UserService service;

@Test
void testGetUser() {

    User user = new User("John");

    when(repo.findById(1L))
        .thenReturn(user);

    User result = service.getUser(1L);

    assertEquals("John",
                 result.getName());
}

# 13. Integration Testing
# Tests interaction between components.
# Example:
@SpringBootTest
class UserIntegrationTest {

    @Autowired
    UserService service;

    @Test
    void testFlow() {
        // Real application context
    }
}

# Checks:
# - Database connectivity
# - API flow
# - Service interactions

# 14. Testcontainers
# - Provides disposable Docker containers for testing.
# Dependency:
<dependency>
  <groupId>org.testcontainers</groupId>
  <artifactId>postgresql</artifactId>
  <scope>test</scope>
</dependency>
# Example:
@Testcontainers
class PostgresTest {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:15");
}
# Benefits:
# - Real database
# - Consistent tests
# - No local installation

# 15. Spring Boot Testing
# @SpringBootTest
# Loads entire application.
@SpringBootTest
class AppTest {}
# @WebMvcTest
# Tests controllers only.
@WebMvcTest(UserController.class)
class ControllerTest {}
# @DataJpaTest
# Tests repository layer.
@DataJpaTest
class RepositoryTest {}
# MockMvc
mockMvc.perform(get("/users"))
       .andExpect(status().isOk());

# 16. Code Coverage
# Measures how much code is tested.
# Metrics:
# - Line Coverage
# - Branch Coverage
# - Method Coverage
# - Class Coverage
# Example:
Total lines = 100
Executed lines = 80

Coverage = 80%

# 17. JaCoCo
# Java Code Coverage Tool.
# Maven Plugin
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
</plugin>
# Generate report:
mvn test
mvn jacoco:report
# Produces:
HTML Report
XML Report
Coverage Statistics

# 18. Static Analysis
# Static analysis examines code without executing it.
# Detects:
# - Code smells
# - Bugs
# - Security issues
# - Style violations
# Examples:
# - Checkstyle
# - SpotBugs
# - SonarQube

# 19. Checkstyle
# Enforces coding standards.
# Checks:
# - Naming conventions
# - Indentation
# - Formatting
# - Imports
# Example: private String userName;
# Bad: private String UserName;

# 20. SpotBugs
# Finds potential bugs.
# Detects:
# - Null pointer issues
# - Resource leaks
# - Unused code
# - Infinite recursion
# Example:
String name = null;
System.out.println(name.length());
# SpotBugs flags this risk.

# 21. SonarQube
# Continuous code quality platform.
# Analyzes:
# - Bugs
# - Vulnerabilities
# - Duplications
# - Code Smells
# - Test Coverage
# Quality Gate Example:
Coverage > 80%
No Critical Bugs
No Blocker Vulnerabilities

# Workflow:
Code
 ↓
Build
 ↓
SonarQube Scan
 ↓
Quality Report

# 22. Test-Driven Development (TDD)
# Development approach where tests are written before code.
# TDD Cycle
Red
 ↓
Green
 ↓
Refactor
# Step 1: Write Failing Test
@Test
void shouldAddNumbers() {
    assertEquals(5,
        calculator.add(2,3));
}
# Step 2: Write Minimal Code
public int add(int a,int b) {
    return a+b;
}
# Step 3: Refactor
# Improve code while keeping tests passing.
# Benefits
# - Better design
# - High coverage
# - Fewer defects
# - Easier maintenance

# Interview Questions
# Q1: Difference between Unit Test and Integration Test?
# Unit Test: Tests a single class/method in isolation.
# Integration Test: Tests interaction among multiple components.

# Q2: Difference between Mock and Spy?
# Mock: Completely fake object.
# Spy: Wraps a real object and allows verification.

# Q3: Why use Mockito?
# Mockito helps isolate business logic by mocking external dependencies.

# Q4: What is TDD?
# Writing tests first, then code, then refactoring.

# Q5: What is JaCoCo?
# A Java tool used to measure code coverage and generate coverage reports.

# Learning Order
1. Unit Testing Fundamentals
2. JUnit 5
3. Assertions
4. Test Lifecycle
5. Parameterized

# This sequence takes you from basic Java testing to professional-quality testing practices used in enterprise Spring Boot applications.