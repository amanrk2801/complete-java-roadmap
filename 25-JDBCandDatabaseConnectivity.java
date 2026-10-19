# JDBC and Database Connectivity (Java)
# JDBC (Java Database Connectivity) is the standard Java API used to connect Java applications with relational databases such as MySQL, PostgreSQL, Oracle, SQL Server, and SQLite.

# 1. JDBC Architecture
# JDBC architecture consists of the following components:
Java Application
       │
       ▼
 JDBC API
       │
       ▼
 JDBC Driver
       │
       ▼
 Database

# Components
# 1. Java Application
# 2. JDBC API (java.sql package)
# 3. JDBC Driver
# 4. Database
# Flow
Application → JDBC API → DriverManager → JDBC Driver → Database

# 2. JDBC Drivers
# A JDBC driver converts Java requests into database-specific commands.
# Types of JDBC Drivers

# Type 1: JDBC-ODBC Bridge Driver
Java → JDBC → ODBC → Database
# - Slow, Requires ODBC, Obsolete

# Type 2: Native Driver
Java → Native API → Database
# Platform dependent

# Type 3: Network Protocol Driver
Java → Middleware Server → Database
# Uses middleware

# Type 4: Thin Driver (Most Used)
Java → Database Protocol → Database
# Example:
MySQL Connector/J
PostgreSQL JDBC Driver
Oracle JDBC Driver

# Advantages: Fast, Platform independent, Pure Java

# 3. Connecting to Databases
# Steps
# Step 1: Load Driver
Class.forName("com.mysql.cj.jdbc.Driver");
# Since JDBC 4.0, explicit loading is usually unnecessary.

# Step 2: Create Connection
Connection con =
DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/company",
    "root",
    "password"
);

# 4. DriverManager
# DriverManager manages JDBC drivers and establishes connections.
# Example
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/test",
    "root",
    "root"
);
# Common methods:
getConnection()
registerDriver()
deregisterDriver()

# 5. Connection
# Represents an active database connection.
Connection con =
DriverManager.getConnection(url,user,password);
# Common methods:
createStatement()
prepareStatement()
prepareCall()

commit()
rollback()

close()
# Example
Connection con = DriverManager.getConnection(url,user,pass);

# 6. Statement
# Used for executing simple SQL statements.
# Create Statement
Statement stmt = con.createStatement();
# Execute Query
ResultSet rs =
stmt.executeQuery(
    "SELECT * FROM employees"
);
# Execute Update
int rows =
stmt.executeUpdate(
    "DELETE FROM employees WHERE id=1"
);
# Problems: SQL Injection risk, Slower for repeated queries

# 7. PreparedStatement
# Precompiled SQL statement.
# Advantages: Faster, Secure, Reusable, Prevents SQL Injection
# Example
String sql =
"INSERT INTO employees(name,salary) VALUES(?,?)";

PreparedStatement ps =
con.prepareStatement(sql);

ps.setString(1, "John");
ps.setDouble(2, 50000);

ps.executeUpdate();

# 8. CallableStatement
# Used to execute stored procedures.
# Example
# Stored Procedure:
CALL getEmployee(1);
# Java:
CallableStatement cs =
con.prepareCall("{call getEmployee(?)}");

cs.setInt(1, 1);

ResultSet rs = cs.executeQuery();

# 9. ResultSet
# Stores query results.
ResultSet rs =
stmt.executeQuery(
    "SELECT * FROM employees"
);
# Reading Data
while(rs.next())
{
    System.out.println(
        rs.getInt("id") + " " +
        rs.getString("name")
    );
}
# Common methods:
next()
previous()
first()
last()

getInt()
getString()
getDouble()

# 10. CRUD Operations
# CRUD = Create, Read, Update, Delete
# Create
INSERT INTO employees(name) VALUES(?)
PreparedStatement ps =
con.prepareStatement(
"INSERT INTO employees(name) VALUES(?)"
);

ps.setString(1, "John");
ps.executeUpdate();

# Read
SELECT * FROM employees
ResultSet rs =
stmt.executeQuery(
"SELECT * FROM employees"
);

# Update
UPDATE employees
SET salary=60000
WHERE id=1;

# Delete
DELETE FROM employees
WHERE id=1;

# 11. SQL Parameterization
# Wrong:
String sql =
"SELECT * FROM users WHERE id="
+ id;
# Correct:
String sql =
"SELECT * FROM users WHERE id=?";

PreparedStatement ps =
con.prepareStatement(sql);

ps.setInt(1, id);
# Benefits: Security, Readability, Performance

# 12. SQL Injection Prevention
# Vulnerable
String sql =
"SELECT * FROM users WHERE name='"
+ userInput + "'";
# Input: '' OR '1'='1'
# Produces:
SELECT * FROM users
WHERE name='' OR '1'='1'
# Returns all users.

# Safe
PreparedStatement ps =
con.prepareStatement(
"SELECT * FROM users WHERE name=?"
);

ps.setString(1, userInput);

# 13. Transactions
# A transaction is a group of SQL operations executed as one unit.
# ACID Properties
# Atomicity
# - All or nothing.
# Consistency
# - Database remains valid.
# Isolation
# - Transactions don't interfere.
# Durability
# - Data survives failures.

# Example
# Money Transfer
Withdraw ₹1000
Deposit ₹1000
# Both operations must succeed together.

# 14. Commit and Rollback
# Disable Auto Commit
con.setAutoCommit(false);
# Transaction Example
try
{
    con.setAutoCommit(false);

    stmt.executeUpdate(sql1);
    stmt.executeUpdate(sql2);

    con.commit();
}
catch(Exception e)
{
    con.rollback();
}

# 15. Transaction Isolation
# Controls concurrent transaction behavior.
# Levels
# READ_UNCOMMITTED
# Allows dirty reads.
Connection.TRANSACTION_READ_UNCOMMITTED

# READ_COMMITTED
# Prevents dirty reads.
Connection.TRANSACTION_READ_COMMITTED

# REPEATABLE_READ
# Prevents non-repeatable reads.
Connection.TRANSACTION_REPEATABLE_READ

# SERIALIZABLE
# Highest isolation.
Connection.TRANSACTION_SERIALIZABLE

# 16. Batch Processing
# Execute multiple SQL statements together.
# Benefits:
# - Improved performance
# - Reduced network calls
# Example
PreparedStatement ps =
con.prepareStatement(
"INSERT INTO employee(name) VALUES(?)"
);

ps.setString(1,"John");
ps.addBatch();

ps.setString(1,"Mike");
ps.addBatch();

ps.setString(1,"David");
ps.addBatch();

int[] results =
ps.executeBatch();

# 17. Generated Keys
# Retrieve auto-generated IDs.
PreparedStatement ps =
con.prepareStatement(
"INSERT INTO employee(name) VALUES(?)",
Statement.RETURN_GENERATED_KEYS
);

ps.setString(1,"John");
ps.executeUpdate();

ResultSet rs =
ps.getGeneratedKeys();

if(rs.next())
{
    System.out.println(
        rs.getInt(1)
    );
}

# 18. Connection Pooling
# Creating connections repeatedly is expensive.
# Connection Pool stores reusable connections.
Application
    ↓
Connection Pool
    ↓
Database
# Benefits: Faster, Scalable, Less overhead
# Popular pools: HikariCP, Apache DBCP, C3P0

# 19. Database Metadata
# Provides database information.
DatabaseMetaData meta =
con.getMetaData();
# Examples
System.out.println(
meta.getDatabaseProductName()
);

System.out.println(
meta.getDriverName()
);

# 20. HikariCP
# One of the fastest JDBC connection pools.
# Maven Dependency
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>6.3.0</version>
</dependency>
# Configuration
HikariConfig config =
new HikariConfig();

config.setJdbcUrl(
"jdbc:mysql://localhost:3306/company"
);

config.setUsername("root");
config.setPassword("root");

HikariDataSource ds =
new HikariDataSource(config);

Connection con =
ds.getConnection();
# Benefits:
# - Very fast
# - Lightweight
# - Production-ready
# - Automatic pooling

# 21. Resource Management with Try-With-Resources
# Automatically closes resources.
# Before Java 7:
finally
{
    if(rs!=null) rs.close();
    if(stmt!=null) stmt.close();
    if(con!=null) con.close();
}

# Modern Approach
try(
    Connection con =
        DriverManager.getConnection(url,user,pass);

    PreparedStatement ps =
        con.prepareStatement(
            "SELECT * FROM employee"
        );

    ResultSet rs =
        ps.executeQuery();
)
{
    while(rs.next())
    {
        System.out.println(
            rs.getString("name")
        );
    }
}
catch(SQLException e)
{
    e.printStackTrace();
}
# Resources are automatically closed.

# JDBC Interview Questions
# Q1. Difference between Statement and PreparedStatement?
| Statement               | PreparedStatement   |
| ----------------------- | ------------------- |
| SQL compiled every time | Precompiled         |
| Slower                  | Faster              |
| SQL Injection risk      | Secure              |
| No parameters           | Supports parameters |

# Q2. Why use PreparedStatement?
# - Better performance
# - Prevents SQL Injection
# - Reusable SQL

# Q3. What is JDBC?
# JDBC (Java Database Connectivity) is a Java API used to connect and interact with relational databases.

# Q4. What is Connection Pooling?
# Maintaining a pool of reusable database connections to improve performance and scalability.

# Q5. What is the purpose of ResultSet?
# It stores data returned by a SQL query and allows row-by-row access.

# JDBC Workflow Summary
Load Driver
     ↓
Get Connection
     ↓
Create Statement /
PreparedStatement
     ↓
Execute SQL
     ↓
Receive ResultSet
     ↓
Process Data
     ↓
Commit/Rollback
     ↓
Close Resources
# This covers the complete JDBC and Database Connectivity module from beginner to interview level.