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

# 