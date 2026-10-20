# Spring Framework Core 
# 1. What is Spring?
# Spring Framework is an open-source Java framework used for building enterprise applications. It provides infrastructure support for developing Java applications with features like:
# - Dependency Injection (DI)
# - Inversion of Control (IoC)
# - Aspect-Oriented Programming (AOP)
# - Transaction Management
# - Data Access
# - Web Development
# Benefits
# - Loose coupling
# - Easy testing
# - Modular architecture
# - Better maintainability
@Service
public class UserService {
    public void display() {
        System.out.println("User Service");
    }
}

# 2. Spring Architecture
# Spring is divided into modules:
Spring Framework 
│ 
├── Core Container 
│   ├── Core 
│   ├── Beans 
│   ├── Context 
│   └── Expression Language 
│ 
├── AOP 
├── Data Access 
│   ├── JDBC 
│   ├── ORM 
│   └── Transactions 
│   
├── Web 
│   ├── Spring MVC 
│   └── WebSocket 
│ 
└── Test

# Core Container
# - Core
# - Beans
# - Context
# - SpEL

# 3. IoC (Inversion of Control)
# IoC means the object creation and dependency management responsibility is transferred to the Spring container.
# Without IoC
public class Car {
    private Engine engine = new Engine();
}
# With IoC
@Component
class Engine {
}

@Component
class Car {

    private Engine engine;

    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
    }
}
# Spring creates and injects objects.

# 4. Dependency Injection (DI)
# DI is the implementation of IoC.
# Types
# 1. Constructor Injection
# 2. Setter Injection
# 3. Field Injection
@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
# Advantages
# - Loose coupling
# - Easy testing
# - Better code design

# 5. BeanFactory and ApplicationContext
# BeanFactory
# Basic IoC container.
BeanFactory factory =
        new XmlBeanFactory(new ClassPathResource("beans.xml"));
# Features
# - Lazy loading
# - Lightweight

# ApplicationContext
# Advanced container.
ApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

# Features
# - Event handling
# - Internationalization
# - Bean post-processing
# - Automatic bean creation

# Difference
| BeanFactory        | ApplicationContext |
| ------------------ | ------------------ |
| Basic Container    | Advanced Container |
| Lazy loading       | Eager loading      |
| Less functionality | More functionality |

# 6. Spring Beans
# A Bean is an object managed by the Spring container.
@Component
public class Employee {
}
# Spring creates and manages this object.

# 7. Bean Scopes
# Singleton (Default)
@Component
@Scope("singleton")
public class UserService {
}
# Only one object per container.

# Prototype
@Component
@Scope("prototype")
public class UserService {
}
# New object every request.

# Web Scopes
# - request
# - session
# - application
# - websocket
@Scope("request")
@Component
public class RequestBean {
}

# 8. Bean Lifecycle
# Lifecycle Steps:
Instantiate Bean 
↓ 
Dependency Injection 
↓ 
@PostConstruct 
↓ 
Bean Ready 
↓ 
@PreDestroy
# Example:
@Component
public class Employee {

    @PostConstruct
    public void init() {
        System.out.println("Bean Created");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Bean Destroyed");
    }
}

# 9. Constructor Injection
# Recommended approach.
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
# Advantages
# ✅ Immutable dependencies
# ✅ Easy testing
# ✅ Mandatory dependencies

# 10. Setter Injection
@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
# Use Case
# Optional dependencies.

# 11. Field Injection and Its Drawbacks
@Service
public class UserService {

    @Autowired
    private UserRepository repository;
}
# Drawbacks
# - ❌ Difficult unit testing
# - ❌ Hidden dependencies
# - ❌ Breaks immutability
# - ❌ Reflection-based injection
# Recommendation
# Use constructor injection instead.

# 12. @Component
# Generic stereotype annotation.
@Component
public class EmailService {
}
# Spring automatically registers it as a bean.

# 13. @Service
# Used for the service layer.
@Service
public class PaymentService {
}
# Represents business logic.

# 14. @Repository
# Used for DAO/Data Access layer.
@Repository
public class UserRepository {
}
# Benefits:
# - Exception translation
# - Data access indication

# 15. @Configuration
# Defines configuration class.
@Configuration
public class AppConfig {
}
# Contains bean definitions.

# 16. @Bean
# Creates and returns Spring-managed beans.
@Configuration
public class AppConfig {

    @Bean
    public Engine engine() {
        return new Engine();
    }
}

# 17. @Autowired
# Automatically injects dependencies.
@Autowired
private UserService userService;
# Constructor-based:
@Autowired
public UserController(UserService userService) {
    this.userService = userService;
}

# 18. @Qualifier
# Used when multiple beans of the same type exist.
@Component("upi")
class UpiPayment {
}

@Component("card")
class CardPayment {
}
@Autowired
@Qualifier("upi")
private Payment payment;

# 19. @Primary
# Marks a bean as the default choice.
@Component
@Primary
public class UpiPayment implements Payment {
}
# Now Spring injects this bean automatically.

# 20. Component Scanning
# Automatically detects components.
@SpringBootApplication
# Equivalent to:
@Configuration
@EnableAutoConfiguration
@ComponentScan
# Custom Scan: @ComponentScan("com.company")

# 21. Configuration Properties
# Used to map application properties to Java objects.
# application.properties
app.name=MyApplication
app.version=1.0
# Class:
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {

    private String name;
    private String version;

    // getters and setters
}

# 22. Profiles
# Profiles allow different configurations for different environments.
@Service
@Profile("dev")
public class DevDatabaseService {
}
@Service
@Profile("prod")
public class ProdDatabaseService {
}
# Activate: spring.profiles.active=dev

# 23. Spring Expression Language (SpEL)
# Provides powerful runtime expressions.
@Value("#{10 + 20}")
private int total;
@Value("#{systemProperties['user.name']}")
private String userName;
# Example:
@Value("#{T(java.lang.Math).PI}")
private double pi;

# 24. AOP (Aspect-Oriented Programming)
# Used to separate cross-cutting concerns.
# Examples:
# - Logging
# - Security
# - Transactions
# - Auditing
@Aspect
@Component
public class LoggingAspect {
}

# 25. Proxies
# Spring AOP works using proxies.
# JDK Dynamic Proxy
# Used when interfaces exist.
public interface PaymentService {
    void pay();
}
# CGLIB Proxy
# Used when no interface exists.
public class PaymentService {
}
# Spring creates proxy objects and intercepts method calls.

# 26. Aspects, Advice, Pointcuts
# Aspect
# Cross-cutting module.
@Aspect
@Component
class LoggingAspect {
}

# Pointcut
# Defines where advice should execute.
@Pointcut("execution(* com.example.service.*.*(..))")
public void serviceMethods() {
}

# Advice
# Action executed at pointcut.
# Before Advice
@Before("serviceMethods()")
public void logBefore() {
    System.out.println("Before Method");
}
# Other Advice Types
# @Before
# @After
# @AfterReturning
# @AfterThrowing
# @Around

# 27. Spring Events
# Allows communication between components.
# Custom Event
public class OrderCreatedEvent {

    private String orderId;

    public OrderCreatedEvent(String orderId) {
        this.orderId = orderId;
    }
}
# Publisher:
@Autowired
private ApplicationEventPublisher publisher;

publisher.publishEvent(
        new OrderCreatedEvent("ORD101")
);
# Listener:
@EventListener
public void handleOrderCreated(OrderCreatedEvent event) {
    System.out.println(event.getOrderId());
}

# 28. Application Lifecycle
# Spring Boot Startup Flow
main() 
↓ 
SpringApplication.run() 
↓ 
Create ApplicationContext 
↓ 
Component Scanning 
↓ 
Bean Creation 
↓ 
Dependency Injection 
↓ 
@PostConstruct 
↓ 
Application Ready

# Shutdown Flow
Application Closing 
↓ 
@PreDestroy 
↓ 
Bean Destruction 
↓ 
Context Closed

# Example:
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

# Interview Questions
# Q1: What is the difference between IoC and DI?
# IoC is the principle of transferring control to Spring, while DI is the technique Spring uses to implement IoC.

# Q2: Which injection type is preferred?
# Constructor Injection because it supports immutability and easier testing.

# Q3: Difference between @Component, @Service, and @Repository?
# All create beans, but:
# @Component → Generic
# @Service → Business layer
# @Repository → Data access layer

# Q4: Singleton vs Prototype?
# Singleton → One object per container
# Prototype → New object every request

# Q5: What is AOP used for?
# To handle cross-cutting concerns like logging, security, caching, and transactions.

# Q6: ApplicationContext vs BeanFactory?
# ApplicationContext provides all BeanFactory features plus enterprise features like events, i18n, and AOP support.
# Best Practice: In modern Spring Boot applications, prefer constructor injection, ApplicationContext, @Configuration + @Bean, and avoid field injection.