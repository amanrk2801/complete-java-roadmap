# Security Fundamentals 
# 1. Secure Coding
# Secure coding means writing software that resists attacks by following security best practices.
# Key Practices
# - Validate all inputs
# - Use parameterized queries
# - Implement proper authentication and authorization
# - Encrypt sensitive data
# - Handle exceptions securely
# - Keep dependencies updated
# - Follow secure design principles
# Example
// Bad
Statement st = conn.createStatement();
ResultSet rs = st.executeQuery(
    "SELECT * FROM users WHERE id=" + userId);

// Good
PreparedStatement ps = conn.prepareStatement(
    "SELECT * FROM users WHERE id=?");
ps.setInt(1, userId);

# 2. Input Validation
# Input validation ensures only acceptable data enters the system.
# Types
# - Whitelisting (Recommended)
# - Blacklisting
# Example
String username = request.getParameter("username");

if (!username.matches("[A-Za-z0-9]{3,20}")) {
    throw new IllegalArgumentException("Invalid username");
}

# Benefits
# - Prevents SQL Injection
# - Prevents XSS
# - Prevents buffer overflows
# - Improves data quality

# 3. Output Encoding
# Output encoding converts special characters into safe representations before displaying data.
# Example
# User Input: <script>alert('Hacked')</script>
# Encoded Output: &lt;script&gt;alert('Hacked')&lt;/script&gt;

# Java Example
String safeOutput =
    StringEscapeUtils.escapeHtml4(userInput);

# Why?
# Prevents Cross-Site Scripting (XSS).

# 4. SQL Injection
# A vulnerability where attacker-controlled input modifies SQL queries.
# Vulnerable Code
String query =
    "SELECT * FROM users WHERE username='"
    + username + "'";
# If input: '' OR '1'='1'
# Query becomes: SELECT * FROM users
WHERE username='' OR '1'='1'

# Prevention
# ✅ Prepared Statements
PreparedStatement ps =
    conn.prepareStatement(
        "SELECT * FROM users WHERE username=?");

ps.setString(1, username);
# ✅ Input validation
# ✅ Least privilege database accounts

# 5. Cross-Site Scripting (XSS)
# Occurs when malicious JavaScript is executed in a user's browser.
# Types
# Stored XSS
# - Malicious script stored in database.
# Reflected XSS
# - Script reflected from request.
# DOM-Based XSS
# - Client-side JavaScript manipulation.
# Example: Welcome, <script>alert('XSS')</script>

# Prevention
# - Output encoding
# - Input validation
# - Content Security Policy (CSP)
# - Avoid unsafe DOM manipulation

# 6. CSRF (Cross-Site Request Forgery)
# Tricks authenticated users into performing unwanted actions.
# Example
# User logged into banking site.
# Attacker creates: <img src=
"https://bank.com/transfer?amount=10000">

# Browser automatically sends session cookie.
# Prevention
✅ CSRF Tokens
<input type="hidden"
       name="csrfToken"
       value="${token}">
✅ SameSite cookies
✅ Re-authentication for sensitive operations

# 7. Broken Access Control
# Users gain access to resources they should not access.
# Example
GET /admin/dashboard
# - Normal user changes URL and gains admin access.

# Prevention
# - Always check authorization on server.
if (!currentUser.isAdmin()) {
    throw new AccessDeniedException();
}
# Principle
# - Never trust the client-side UI.

# 8. SSRF (Server-Side Request Forgery)
# Allows attacker to force the server to make requests on their behalf.
# Vulnerable Example
URL url =
    new URL(request.getParameter("target"));

url.openConnection();
# Attacker: http://localhost:8080/admin
# Prevention
# - Whitelist allowed hosts
# - Block internal IP ranges
# - Validate URLs
# - Restrict outbound network access

# 9. Path Traversal
# - Accessing files outside intended directories.
# Attack Example
../../../etc/passwd
# Vulnerable Code
File file =
    new File("/uploads/" + userFileName);
# Secure Code
Path base =
    Paths.get("/uploads");

Path requested =
    base.resolve(userFileName)
        .normalize();

if (!requested.startsWith(base)) {
    throw new SecurityException();
}

# 10. Deserialization Vulnerabilities
# Happen when untrusted serialized objects are deserialized.
# Example
ObjectInputStream ois =
    new ObjectInputStream(input);

Object obj = ois.readObject();
# Attacker may execute malicious code.
# Prevention
# - Avoid Java native serialization
# - Use JSON/XML safely
# - Restrict allowed classes
# - Validate data before deserialization

# 11. Dependency Vulnerability Scanning
# Third-party libraries may contain known vulnerabilities.
# Tools
# - OWASP Dependency Check
# - Snyk
# - Dependabot
# - Maven Enforcer
# Maven Example
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
</plugin>
# Best Practice
# Regularly update dependencies.

# 12. TLS (Transport Layer Security)
# Encrypts communication between client and server.
# Provides
# - Confidentiality
# - Integrity
# - Authentication
# HTTPS
https://example.com
# Best Practices
# - Use TLS 1.2 or higher
# - Disable insecure protocols
# - Use trusted certificates
# - Enable HSTS

# 13. Password Storage
# Never Store
password123
# Never Use
MD5
SHA1
# Use: BCrypt, PBKDF2, Argon2
# Example (BCrypt)
String hash =
    BCrypt.hashpw(password,
                  BCrypt.gensalt());

boolean valid =
    BCrypt.checkpw(password, hash);

# 14. Secrets Management
# Secrets include:
# - API Keys
# - Database Passwords
# - Tokens
# - Certificates
# Bad
String password =
    "admin123";
# Good
String password =
    System.getenv("DB_PASSWORD");
# Tools
# - HashiCorp Vault
# - AWS Secrets Manager
# - Azure Key Vault
# - Kubernetes Secrets

# 15. OWASP Top 10
# Most critical web application security risks.
# Categories
1. Broken Access Control
2. Cryptographic Failures
3. Injection
4. Insecure Design
5. Security Misconfiguration
6. Vulnerable Components
7. Identification & Authentication Failures
8. Software and Data Integrity Failures
9. Security Logging & Monitoring Failures
10. SSRF

# Importance
# - Widely used security checklist during development and audits.

# 16. Principle of Least Privilege (PoLP)
# Users, applications, and services should have only the minimum permissions required.
# Example
# Database user only needs:
SELECT
INSERT
# Not:
DROP DATABASE
ALTER SYSTEM
# Java Example
if(user.hasRole("EMPLOYEE")) {
    accessEmployeePortal();
}
# Benefits
# - Reduces attack surface
# - Limits damage after compromise
# - Improves compliance
# - Simplifies auditing

# Interview Questions
# Q1. What is SQL Injection?
# An attack where malicious input modifies SQL queries to access or manipulate data.

# Q2. Difference between XSS and CSRF?
# XSS executes malicious scripts in the victim's browser.
# CSRF tricks authenticated users into performing unintended actions.

# Q3. Why are Prepared Statements secure?
# They separate SQL code from user input, preventing SQL Injection.

# Q4. What is SSRF?
# A vulnerability allowing attackers to make the server send requests to internal or external systems.

# Q5. Why should passwords be hashed?
# So actual passwords are not stored and cannot be easily recovered if the database is compromised.

# Q6. What is the Principle of Least Privilege?
# Grant only the minimum permissions necessary to perform required tasks.

# Quick Revision
Input Validation      -> Validate incoming data
Output Encoding       -> Prevent XSS
Prepared Statements   -> Prevent SQL Injection
CSRF Token            -> Prevent CSRF
Authorization Checks  -> Prevent Broken Access Control
URL Validation        -> Prevent SSRF
Path Normalization    -> Prevent Path Traversal
Safe Serialization    -> Prevent Deserialization Attacks
Dependency Scanning   -> Detect vulnerable libraries
TLS                   -> Secure network traffic
BCrypt/Argon2         -> Secure password storage
Secrets Manager       -> Secure credential storage
OWASP Top 10          -> Security risk checklist
Least Privilege       -> Minimum required permissions