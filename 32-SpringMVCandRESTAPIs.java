# Spring MVC and REST APIs (Java + Spring Boot)
# This section is one of the most important parts of modern Java backend development. Spring MVC is used to build web applications and REST APIs.

# 1. MVC Architecture
# MVC stands for:
# - Model → Data and business logic
# - View → UI (HTML, JSP, Thymeleaf)
# - Controller → Receives requests and returns responses

# Flow
Client
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
Database

# Example
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}

# 2. DispatcherServlet
# The DispatcherServlet is the front controller of Spring MVC.
# All incoming requests first go to DispatcherServlet.
Request
   |
   v
DispatcherServlet
   |
   +--> Controller
   |
   +--> Service
   |
   +--> View Resolver
# Responsibilities:
# - Receive request
# - Find controller
# - Execute controller method
# - Return response

# 3. Controllers
# Controllers handle HTTP requests.
@Controller
public class UserController {

    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }
}
# For REST APIs:
@RestController
public class UserController {

}

# 4. @RestController
# Combination of:
@Controller + @ResponseBody
# Instead of returning a view, it returns JSON.
@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring";
    }
}
# Output: Hello Spring

# 5. @RequestMapping
# Maps URL paths.
@RestController
@RequestMapping("/api/users")
public class UserController {

}
# All endpoints start with:
/api/users

# 6. @GetMapping
# Handles GET requests.
@GetMapping("/{id}")
public User getUser(@PathVariable Long id) {
    return userService.getUser(id);
}
# Request: GET /api/users/1

# 7. @PostMapping
# Creates a resource.
@PostMapping
public User createUser(@RequestBody User user) {
    return userService.save(user);
}
# Request: POST /api/users
# Body:
{
  "name": "Aman"
}

# 8. @PutMapping
# Updates entire resource.
@PutMapping("/{id}")
public User updateUser(
        @PathVariable Long id,
        @RequestBody User user) {

    return userService.update(id, user);
}

# 9. @PatchMapping
# Updates partial resource.
@PatchMapping("/{id}")
public User updateEmail(
        @PathVariable Long id,
        @RequestBody Map<String,String> body) {

    return userService.updateEmail(id, body.get("email"));
}

# 10. @DeleteMapping
# Deletes resource.
@DeleteMapping("/{id}")
public void deleteUser(@PathVariable Long id) {
    userService.delete(id);
}

# 11. @PathVariable
# Reads values from URL.
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userService.findById(id);
}
# Request: GET /users/101
# Value: id = 101

# 12. @RequestParam
# Reads query parameters.
@GetMapping("/search")
public String search(@RequestParam String keyword) {
    return keyword;
}
# Request: GET /search?keyword=java
# Output: java
# Default value:
@RequestParam(defaultValue = "1")
int page

# 13. @RequestBody
# Converts JSON to Java object.
# JSON
{
  "name":"Aman",
  "email":"aman@gmail.com"
}
# Java
@PostMapping
public User create(@RequestBody User user) {
    return user;
}

# 14. @ResponseBody
# Returns object directly as HTTP response.
@Controller
public class UserController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello";
    }
}
# Equivalent: @RestController

# 15. ResponseEntity
# Provides full control over HTTP response.
@GetMapping("/{id}")
public ResponseEntity<User> getUser(
        @PathVariable Long id) {

    User user = service.findById(id);

    return ResponseEntity.ok(user);
}
# Custom status:
return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(user);
# Not found:
return ResponseEntity
        .notFound()
        .build();

# 16. DTOs (Data Transfer Objects)
# Used to transfer data between client and server.
# Avoid exposing entity directly.
# Entity
@Entity
public class User {

    private Long id;
    private String name;
    private String password;
}
# DTO
public class UserDTO {

    private Long id;
    private String name;
}
# Controller returns:
UserDTO
# instead of:
User
# Benefits:
# - Better security
# - Cleaner APIs
# - Validation support

# 17. Request Validation
# Ensures client sends valid data.
# Example:
public class UserDTO {

    @NotBlank
    private String name;

    @Email
    private String email;
}

# 18. Bean Validation
# Uses Jakarta Validation API.
# Common annotations:
@NotNull
@NotBlank
@NotEmpty
@Size
@Min
@Max
@Email
@Pattern
@Positive
@Past
@Future
# Example:
@Size(min = 3, max = 20)
private String username;

# 19. @Valid and @Validated
# @Valid
# Used for request body validation.
@PostMapping
public User createUser(
        @Valid @RequestBody UserDTO dto) {

    return service.save(dto);
}
# @Validated
# Used at class level.
@RestController
@Validated
public class UserController {

}
# For parameter validation:
@GetMapping("/{id}")
public String get(
        @Min(1) @PathVariable Long id) {

    return "OK";
}

# 20. Global Exception Handling
# Instead of try-catch everywhere:
try {
   ...
}
catch(Exception e) {
   ...
}
# Use centralized handling.

# 21. @ControllerAdvice
# Global exception handler.
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}

# Custom exception:
public class UserNotFoundException
        extends RuntimeException {

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
# Handler:
@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<String> handleUserNotFound(
        UserNotFoundException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}

# 22. Pagination and Sorting
# Provided by Spring Data JPA.
@GetMapping
public Page<User> getUsers(
        Pageable pageable) {

    return repository.findAll(pageable);
}
# Request:
GET /users?page=0&size=5&sort=name
# Multiple sorting:
GET /users?sort=name,asc&sort=id,desc

# 23. Filtering and Searching
# Example:
@GetMapping("/search")
public List<User> search(
        @RequestParam String name) {

    return repository.findByNameContaining(name);
}
# Repository:
List<User> findByNameContaining(
        String name);
# Request: GET /users/search?name=am

# 24. API Versioning
# URI Versioning
/api/v1/users
/api/v2/users
@RequestMapping("/api/v1/users")

# Header Versioning
X-API-VERSION: 1

# Parameter Versioning
/users?version=1
# URI versioning is the most common.

# 25. CORS (Cross-Origin Resource Sharing)
# Allows requests from another origin.
# Example:
# Frontend: http://localhost:3000
# Backend: http://localhost:8080
# Enable CORS:
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
}
# Global:
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(
                    CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOrigins(
                            "http://localhost:3000");
            }
        };
    }
}

# 26. OpenAPI and Swagger
# Dependency:
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.x.x</version>
</dependency>
# Access:
http://localhost:8080/swagger-ui.html
# API documentation generated automatically.
# Annotation example:
@Operation(summary = "Get User")
@GetMapping("/{id}")
public User getUser(
        @PathVariable Long id) {
    return service.findById(id);
}

# 27. REST API Best Practices
# Use Nouns
# ✅ Good
/users
/products
/orders

# ❌ Bad
/getUsers
/createUser
/deleteUser

# Use HTTP Methods Correctly
GET     Read
POST    Create
PUT     Update Entire Resource
PATCH   Partial Update
DELETE  Delete

# Proper Status Codes
200 OK
201 CREATED
204 NO CONTENT
400 BAD REQUEST
401 UNAUTHORIZED
403 FORBIDDEN
404 NOT FOUND
500 INTERNAL SERVER ERROR

# 28. Idempotency
# An operation is idempotent if multiple identical requests produce the same result.
# Idempotent
GET
PUT
DELETE
# Example:
DELETE /users/5
DELETE /users/5
DELETE /users/5
# Result remains same:
User deleted

# Non-Idempotent
# POST /users
# Each call creates a new resource.

# 29. Rate Limiting Concepts
# Protects API from abuse.
# Example: 
100 requests/minute
# Common algorithms:
# Fixed Window
100 requests/min
# Simple but can allow bursts.
# Sliding Window
# More accurate request tracking.
# Token Bucket
Bucket = 100 tokens
1 request = 1 token
# Widely used in production.
# Popular implementations:
# - Bucket4j
# - Resilience4j
# - API Gateway rate limiting

# Complete REST API Example
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(
            @Valid @RequestBody UserDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @PutMapping("/{id}")
    public UserDTO update(
            @PathVariable Long id,
            @RequestBody UserDTO dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}

# Interview Questions
Difference between @Controller and @RestController?
What is DispatcherServlet?
Difference between @RequestParam and @PathVariable?
Difference between PUT and PATCH?
What is DTO and why use it?
Difference between @Valid and @Validated?
What is ResponseEntity?
How does global exception handling work?
What is CORS?
Explain idempotency in REST APIs.