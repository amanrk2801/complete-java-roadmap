# Spring Boot 
# 1. Spring Boot Architecture
# Spring Boot is built on top of the Spring Framework and follows a layered architecture.
# Architecture Flow
Client Request 
| 
v 
+------------------+ 
| Controller Layer | 
+------------------+ 
| 
v 
+------------------+ 
| Service Layer    | 
+------------------+ 
| 
v 
+------------------+ 
| Repository Layer | 
+------------------+ 
| 
v 
+------------------+ 
| Database         | 
+------------------+

# Main Components
# 1. Spring Boot Application
# 2. Auto Configuration
# 3. Starter Dependencies
# 4. Embedded Server
# 5. Spring Container (IoC)
# Main Class
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
# @SpringBootApplication combines:
@Configuration
@EnableAutoConfiguration
@ComponentScan

# 2. Spring Initializr
# Spring Initializr creates a Spring Boot project automatically.
# Ways to Create Project
# - https://start.spring.io
# - IntelliJ IDEA
# - Spring Tool Suite (STS)

# Steps
# 1. Select Project (Maven/Gradle)
# 2. Select Spring Boot Version
# 3. Enter Group ID
# 4. Enter Artifact ID
# 5. Add Dependencies
# 6. Generate Project

# Example
Group: com.example 
Artifact: student-app

# Generated structure:
src 
└── main 
    ├── java 
    └── resources

# 3. Auto-Configuration
# Automatically configures beans based on dependencies available in the classpath.
# Example
# Dependency:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
# Spring Boot automatically configures:
# - DispatcherServlet
# - Tomcat
# - Jackson
# - MVC Components
# Without manual XML configuration.

# 4. Starter Dependencies
# Pre-configured dependency bundles.
# Common Starters
# Web
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
# JPA
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
# Security
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
# Test
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>

# 5. Embedded Servers
# No need to install a separate server.
# Supported servers:
# - Tomcat (Default)
# - Jetty
# - Undertow
# Run Application
mvn spring-boot:run
# Tomcat starts automatically:
Tomcat started on port(s): 8080
# Change port:
server.port=9090

# 6. Application Properties
# Stored inside:
src/main/resources/application.properties
# Example
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=root

# 7. application.yml
# Alternative to properties file.
# application.yml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: root

# Difference
# Properties: server.port=8080
# YAML:
server:
  port: 8080
# YAML is more readable for nested configurations.

# 8. Profiles
# Used for different environments.
# Examples:
# - dev
# - test
# - prod
# application-dev.properties
server.port=8081
# application-prod.properties
server.port=9090
# Activate Profile
spring.profiles.active=dev
# Or:
java -jar app.jar --spring.profiles.active=prod

# 9. Configuration Properties
# Maps external configuration to Java Objects.
# application.properties
app.name=Student Management System
app.version=1.0
# Java Class
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

# 10. Externalized Configuration
# Configuration can come from:
# - Properties File
# - YAML File
# - Environment Variables
# - JVM Arguments
# - Command Line Arguments

# Example
java -jar app.jar --server.port=9090

# Environment Variable: SERVER_PORT=9090

# 11. Conditional Beans
# Create beans only if a condition is satisfied.
# Example
@Bean
@ConditionalOnProperty(
    name = "cache.enabled",
    havingValue = "true")
public CacheManager cacheManager() {
    return new CacheManager();
}
# Property: cache.enabled=true

# 12. Custom Auto-Configuration
# Create your own auto-configured library.
# Configuration Class
@Configuration
@ConditionalOnClass(MyService.class)
public class MyAutoConfiguration {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
# Used when building reusable frameworks.

# 13. Actuator
# Provides production-ready monitoring features.
# Dependency:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
# Endpoints:
/actuator 
/actuator/health 
/actuator/metrics

# Enable: management.endpoints.web.exposure.include=*

# 14. Health Endpoints
# Check application health.
# URL: http://localhost:8080/actuator/health
# Response: 
{
  "status": "UP"
}
# Custom Health Indicator:
@Component
public class DatabaseHealthIndicator
        implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().build();
    }
}

# 15. Metrics
# Actuator collects metrics.
# Examples:
# - JVM Memory
# - CPU Usage
# - Request Count
# - Response Time
# URL: /actuator/metrics
# Specific metric: /actuator/metrics/jvm.memory.used

# 16. Logging Configuration
# Default Logging Framework:
Logback
# Usage
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentService {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    public void saveStudent() {
        logger.info("Student saved");
    }
}

# Set Log Level
logging.level.root=INFO
logging.level.com.example=DEBUG
# Log File: logging.file.name=application.log

# 17. Graceful Shutdown
# Allows running requests to complete before shutdown.

# Enable
server.shutdown=graceful
# Timeout
spring.lifecycle.timeout-per-shutdown-phase=30s

# 18. Packaging and Deployment
# Build JAR
mvn clean package
# Generated: target/student-app.jar
# Run: java -jar student-app.jar

# Build WAR
<packaging>war</packaging>
# Deploy to: Tomcat, Jetty, WebLogic

# Docker Deployment
# Dockerfile:
FROM eclipse-temurin:21-jdk

COPY target/student-app.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
# Build: docker build -t student-app .
# Run: docker run -p 8080:8080 student-app

# 19. Spring Boot Version Compatibility
| Spring Boot | Java Version |
| ----------- | ------------ |
| 1.x         | Java 7, 8    |
| 2.x         | Java 8+      |
| 3.0         | Java 17+     |
| 3.1         | Java 17+     |
| 3.2         | Java 17+     |
| 3.3         | Java 17+     |

# Current Recommendation
# ✅ Use:
# - Java 21 (LTS)
# - Spring Boot 3.x
# - Spring Framework 6.x

# Interview Questions
# 1. What is Spring Boot?
# Spring Boot is an extension of Spring that provides auto-configuration, embedded servers, and starter dependencies to rapidly develop Java applications.

# 2. What is the use of @SpringBootApplication?
# Combines:
# - @Configuration
# - @EnableAutoConfiguration
# - @ComponentScan

# 3. Difference between application.properties and application.yml?
# application.properties
server.port=8080
# application.yml
server:
  port: 8080
# YAML is more readable for hierarchical data.

# 4. What is Actuator?
# A production-ready monitoring and management module providing health, metrics, and runtime information.

# 5. What are starter dependencies?
# Predefined dependency bundles that simplify project setup.

# 6. What is auto-configuration?
# Spring Boot automatically configures application components based on dependencies available in the classpath.

# 7. What is an embedded server?
# A server packaged inside the application (Tomcat, Jetty, Undertow), eliminating separate deployment.

# 8. What are profiles?
# Profiles allow environment-specific configurations such as dev, test, and prod.