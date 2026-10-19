# Build Tools and Dependency Management (Java)
# These tools help automate compilation, testing, packaging, dependency management, and deployment of Java applications.

# 1. Maven
# What is Maven?
# Maven is a build automation and dependency management tool for Java projects.
# It helps with:
# - Managing dependencies
# - Compiling code
# - Running tests
# - Packaging applications
# - Deploying artifacts

# Advantages
# - Convention over configuration
# - Automatic dependency management
# - Reproducible builds
# - Large ecosystem of plugins

# Maven Command Examples
mvn compile
mvn test
mvn package
mvn install
mvn clean

# 2. Maven Project Structure
# Standard Maven directory layout:
project-name/
│
├── pom.xml
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │
│   └── test/
│       ├── java/
│       └── resources/
│
└── target/
# Directories
| Directory          | Purpose             |
| ------------------ | ------------------- |
| src/main/java      | Application code    |
| src/main/resources | Configuration files |
| src/test/java      | Test cases          |
| src/test/resources | Test resources      |
| target             | Generated artifacts |

# 3. pom.xml
# POM = Project Object Model
# Main Maven configuration file.
# Example:
<project>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.company</groupId>
    <artifactId>demo-project</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
        </dependency>
    </dependencies>
</project>
# Important Elements
groupId
artifactId
version
packaging
dependencies
build
plugins
properties

# 4. Dependencies and Scopes
# Dependency = External library required by project.
# Example:
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>6.1.0</version>
</dependency>
# Dependency Scopes
# Compile (Default)
# Available everywhere.
<scope>compile</scope>

# Provided
# Used during compile but supplied by server.
<scope>provided</scope>
# Example: javax.servlet-api

# Runtime
# Not needed for compilation.
<scope>runtime</scope>
# Example: mysql-connector-java

# Test
# Only for testing.
<scope>test</scope>
# Example: junit, mockito

# System
# Dependency from local path.
<scope>system</scope>
# Rarely used.

# 5. Transitive Dependencies
# A dependency may bring its own dependencies.
# Example:
Project
  ↓
Spring Core
  ↓
Spring JCL
# Only Spring Core is declared:
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
</dependency>
# Maven automatically downloads:
spring-core
spring-jcl
# Excluding Dependency
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>

    <exclusions>
        <exclusion>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jcl</artifactId>
        </exclusion>
    </exclusions>
</dependency>

# 6. Maven Lifecycle
# Lifecycle = Sequence of build phases.
# Clean Lifecycle
clean
# Deletes: target/
# Command: mvn clean

# Default Lifecycle
validate
compile
test
package
verify
install
deploy
# Example: mvn package
# Runs:
validate
compile
test
package

# Site Lifecycle
# Generates project reports.
mvn site

# 7. Plugins
# Maven functionality comes from plugins.
# Example:
<build>
    <plugins>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
        </plugin>

    </plugins>
</build>

# Common Plugins:
| Plugin      | Purpose          |
| ----------- | ---------------- |
| compiler    | Compile code     |
| surefire    | Run tests        |
| jar         | Create JAR       |
| war         | Create WAR       |
| shade       | Fat JAR          |
| spring-boot | Boot application |

# 8. Profiles
# Profiles allow different configurations.
# Example:
<profiles>

    <profile>
        <id>dev</id>
    </profile>

    <profile>
        <id>prod</id>
    </profile>

</profiles>

# Run: mvn package -Pdev

# Use Cases: Development, Testing, Production, Database variations

# 9. Multi-Module Projects
# Large projects often have modules.
# Structure:
company-project/
├── pom.xml
├── common
├── user-service
├── payment-service
└── notification-service
# Parent POM:
<modules>
    <module>common</module>
    <module>user-service</module>
    <module>payment-service</module>
</modules>
# Benefits:
# - Reusability
# - Centralized dependency management
# - Easier maintenance

# 10. Dependency Conflicts
# Example:
Project
 ├── Library A -> Log4j 1.0
 └── Library B -> Log4j 2.0
# Conflict occurs because of two versions.
# Maven Rule
# Nearest dependency wins.
# Check Dependencies
# mvn dependency:tree
# Force Version
<dependencyManagement>
    <dependencies>

        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>2.22.0</version>

        </dependency>

    </dependencies>
</dependencyManagement>

# 11. Gradle Fundamentals
# Gradle is a modern build tool.
# Features:
# - Faster than Maven
# - Uses DSL
# - Incremental builds
# - Build caching
# - Highly customizable
# Build languages:
Groovy DSL
Kotlin DSL
# Commands:
gradle build
gradle test
gradle clean

# 12. Gradle Build Files
# Groovy DSL
plugins {
    id 'java'
}

group = 'com.company'
version = '1.0.0'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
}

# Kotlin DSL
plugins {
    java
}

group = "com.company"
version = "1.0.0"

repositories {
    mavenCentral()
}
# Files:
build.gradle
build.gradle.kts
settings.gradle

# 13. Gradle Tasks
# Task = Unit of work.
# View tasks: gradle tasks
# Create custom task:
task hello {
    doLast {
        println "Hello World"
    }
}
# Run: gradle hello
# Common Tasks:
build
clean
compileJava
test
jar
publish

# 14. Dependency Locking
# Locks dependency versions.
# Benefits:
# - Consistent builds
# - Prevent accidental upgrades
# Enable:
dependencyLocking {
    lockAllConfigurations()
}
# Generate locks:
gradle dependencies --write-locks
# Produces lock file containing exact versions.

# 15. Semantic Versioning (SemVer)
# Format: MAJOR.MINOR.PATCH
# Example: 2.5.1
# Meaning:
2 = MAJOR
5 = MINOR
1 = PATCH
# Rules:
# MAJOR
# Breaking changes
1.0.0 → 2.0.0

# MINOR
# New backward-compatible features
1.3.0 → 1.4.0

# PATCH
# Bug fixes
1.4.2 → 1.4.3

# 16. JAR, WAR, and Executable JAR
# JAR (Java Archive)
# Contains:
Classes
Resources
Libraries

# Create:
mvn package

# Produces:
app.jar

# WAR (Web Archive)
# For web applications.
# Contains:
Servlets
JSP
Web resources
WEB-INF

# Configuration: <packaging>war</packaging>
# Output: app.war

# Executable JAR
# Self-running JAR.
# Run:
java -jar app.jar
# Spring Boot:
mvn spring-boot:repackage
# Creates fat/executable JAR.

# 17. Publishing Artifacts
# Artifact = Output generated by build.
# Examples:
JAR
WAR
ZIP

# Maven Publish
# Local Repository:
mvn install
# Location:
~/.m2/repository
# Remote Repository:
mvn deploy
# Common repositories:
Maven Central
Nexus
Artifactory
GitHub Packages

# Maven vs Gradle
| Feature               | Maven     | Gradle    |
| --------------------- | --------- | --------- |
| XML Configuration     | Yes       | No        |
| Performance           | Good      | Faster    |
| Learning Curve        | Easier    | Moderate  |
| Flexibility           | Moderate  | High      |
| DSL Support           | No        | Yes       |
| Multi-module Support  | Yes       | Yes       |
| Dependency Management | Excellent | Excellent |

# Interview Questions
# Q1: What is Maven?
# A build and dependency management tool used for Java projects.

# Q2: What is pom.xml?
# The main configuration file of a Maven project.

# Q3: What are dependency scopes?
# Compile, Provided, Runtime, Test, and System.

# Q4: What are transitive dependencies?
# Dependencies automatically downloaded because another dependency requires them.

# Q5: What is the Maven lifecycle?
# A predefined sequence of phases such as compile, test, package, install, and deploy.

# Q6: What is the difference between JAR and WAR?
# JAR → General Java application
# WAR → Web application deployment package

# Q7: Why is Gradle faster than Maven?
# Because of incremental builds, task optimization, and build caching.

# Q8: What is semantic versioning?
# Versioning format:
MAJOR.MINOR.PATCH
# used to indicate compatibility and changes in software releases.