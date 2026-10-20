# Networking and HTTP 
# This section is very important for backend, distributed systems, microservices, and web development.

# 1. Networking Fundamentals
# Networking is the communication between computers over a network.
# Important Terms
# - Host → A device connected to a network.
# - IP Address → Unique address of a device.
# - Port → Logical endpoint of communication.
# - Protocol → Rules for communication.
# - Client → Requests services.
# - Server → Provides services.

# Example
# When you open Google:
Browser (Client) 
│ 
▼ 
Google Server
# The browser sends requests and the server responds.

# 2. TCP/IP
# TCP/IP is the foundation of the Internet.
# TCP (Transmission Control Protocol)
# Features:
# - Reliable
# - Connection-oriented
# - Ordered delivery
# - Error checking
# Example
Client ----TCP Connection---- Server
# Data arrives exactly in order.
# Used by: HTTP, HTTPS, SMTP, FTP

# IP (Internet Protocol)
# Responsible for addressing and routing.
# Example: 192.168.1.10
# IP only delivers packets.
# No guarantee of delivery.

# TCP/IP Layers
Application 
Transport (TCP/UDP) 
Internet (IP) 
Network Access

# 3. DNS (Domain Name System)
# DNS converts domain names into IP addresses.
# Example:
google.com 
↓ 
142.250.190.14
# Without DNS we would need to remember IP addresses.

# DNS Flow
Browser 
↓ 
DNS Server 
↓ 
Returns IP 
↓ 
Connects Server

# 4. HTTP and HTTPS
# HTTP: HyperText Transfer Protocol.
# Works on: Port 80
# HTTP is plaintext.
# Example Request:
GET /users HTTP/1.1
Host: example.com

# HTTPS: HyperText Transfer Protocol Secure.
# Works on: Port 443
# Uses: TLS, Encryption, Authentication
# Benefits: Data confidentiality, Integrity, Protection from attackers

# 5. Sockets
# A socket is an endpoint for communication.
# Think of it as a phone connection between two programs.
Client Socket ⇄ Server Socket

# 6. Socket and ServerSocket
# Java provides:
# Socket
# Used by client.
Socket socket = new Socket("localhost", 5000);

# ServerSocket
# Used by server.
ServerSocket server = new ServerSocket(5000);

# Server Example
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);

        Socket socket = server.accept();

        PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);

        out.println("Hello Client");

        socket.close();
        server.close();
    }
}

# Client Example
import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket socket =
                new Socket("localhost", 5000);

        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

        System.out.println(in.readLine());

        socket.close();
    }
}

# Output: Hello Client

# 7. Java HTTP Client
# Introduced in Java 11.
# Package: java.net.http
# Used for:
# - REST APIs
# - HTTP requests
# - File downloads
# - Microservices communication

# 8. HttpClient
# Represents the HTTP client.
HttpClient client =
        HttpClient.newHttpClient();
# Customized Client:
HttpClient client =
        HttpClient.newBuilder()
                  .version(HttpClient.Version.HTTP_2)
                  .build();

# 9. HttpRequest
# Represents a request.
# Example:
HttpRequest request =
        HttpRequest.newBuilder()
                   .uri(
                     URI.create(
                       "https://jsonplaceholder.typicode.com/posts/1"))
                   .GET()
                   .build();

# POST Request
HttpRequest request =
        HttpRequest.newBuilder()
                .uri(
                  URI.create(
                    "https://example.com/api"))
                .POST(
                  HttpRequest.BodyPublishers
                             .ofString("{\"name\":\"Aman\"}")
                )
                .build();

# 10. HttpResponse
# Represents the response.
HttpResponse<String> response =
        client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

System.out.println(response.statusCode());

System.out.println(response.body());

# Full GET Example
import java.net.URI;
import java.net.http.*;

public class HttpExample {

    public static void main(String[] args)
            throws Exception {

        HttpClient client =
                HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                           .uri(
                             URI.create(
                               "https://jsonplaceholder.typicode.com/posts/1"))
                           .GET()
                           .build();

        HttpResponse<String> response =
                client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                );

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}

# 11. REST Principles
# REST = Representational State Transfer
# Architectural style for web services.

# REST Rules
# 1. Client-Server
# Separate client and server.

# 2. Stateless
# Each request contains all information.
# Server doesn't remember previous requests.

# 3. Uniform Interface
# Consistent URLs.
# Example:
GET /users 
GET /users/1 
POST /users 
PUT /users/1 
DELETE /users/1

# 4. Cacheable
# Responses can be cached.

# 12. JSON Processing
# JSON = JavaScript Object Notation
# Example:
{
  "id": 1,
  "name": "Aman",
  "city": "Pune"
}

# 13. Jackson
# Popular JSON library.
# Maven:
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.20.0</version>
</dependency>

# Java Object
public class User {
    private int id;
    private String name;

    public User() {}

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters setters
}

# Object → JSON
ObjectMapper mapper = new ObjectMapper();

User user = new User(1, "Aman");

String json =
        mapper.writeValueAsString(user);

System.out.println(json);
# Output: {"id":1,"name":"Aman"}

# JSON → Object
String json =
    "{\"id\":1,\"name\":\"Aman\"}";

User user =
        mapper.readValue(
            json,
            User.class);

System.out.println(user.getName());
# Output: Aman

# 14. Gson
# Google JSON library.
# Maven:
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.13.2</version>
</dependency>

# Serialization
Gson gson = new Gson();

User user =
        new User(1, "Aman");

String json =
        gson.toJson(user);

System.out.println(json);

# Deserialization
String json =
        "{\"id\":1,\"name\":\"Aman\"}";

User user =
        gson.fromJson(json,
                      User.class);

System.out.println(user.getName());

# 15. Serialization and Deserialization
# Serialization
# Convert Java Object → JSON
User user = new User(1, "Aman");

String json =
        mapper.writeValueAsString(user);

# Deserialization
# Convert JSON → Java Object
User user =
        mapper.readValue(json,
                         User.class);

# 16. WebSocket Basics
# HTTP: Request → Response
# Connection closes.

# WebSocket: Client ⇄ Server

# Connection remains open.
# Real-time communication.
# Used in:
# - Chat applications
# - Stock market apps
# - Multiplayer games
# - Notifications

# WebSocket Lifecycle
Handshake 
↓ 
Connection Open 
↓ 
Send / Receive Messages 
↓ 
Close Connection

# 17. TLS and Certificates
# TLS = Transport Layer Security
# Used by HTTPS.
# Provides:
# - Encryption
# - Authentication
# - Integrity

# Certificate
# Issued by CA (Certificate Authority).
# Example:
Browser 
↓ 
Checks Certificate 
↓ 
Trusts Website

# Certificate contains:
# - Domain name
# - Public key
# - Expiration date
# - CA signature

# 18. Timeouts and Retries
# Timeout
# Prevent application from waiting forever.
HttpClient client =
        HttpClient.newBuilder()
                  .connectTimeout(
                    Duration.ofSeconds(5))
                  .build();

# Request Timeout
HttpRequest request =
        HttpRequest.newBuilder()
          .timeout(Duration.ofSeconds(10))
          .uri(URI.create("https://example.com"))
          .build();

# Retry Logic
int retries = 3;

for (int i = 1; i <= retries; i++) {
    try {
        client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        break;
    } catch (Exception e) {
        System.out.println("Retry " + i);
    }
}

# 19. Connection Pooling
# Creating connections repeatedly is expensive.
# Connection Pool:
Pool 
├── Connection 1 
├── Connection 2 
├── Connection 3

# Connections are reused.
# Benefits:
# - Faster requests
# - Less CPU usage
# - Better scalability

# Interview Questions
# What is the difference between TCP and UDP?
| TCP                 | UDP            |
| ------------------- | -------------- |
| Reliable            | Unreliable     |
| Connection-oriented | Connectionless |
| Ordered delivery    | No ordering    |
| Slower              | Faster         |

# HTTP vs HTTPS?
| HTTP          | HTTPS     |
| ------------- | --------- |
| Not encrypted | Encrypted |
| Port 80       | Port 443  |
| Less secure   | Secure    |
| Uses no TLS   | Uses TLS  |

# Socket vs ServerSocket?
| Socket         | ServerSocket        |
| -------------- | ------------------- |
| Client side    | Server side         |
| Sends requests | Accepts connections |

# Serialization vs Deserialization?
| Serialization | Deserialization |
| ------------- | --------------- |
| Object → JSON | JSON → Object   |

# What is REST?
REST is a stateless architectural style where resources are identified by URLs and manipulated using HTTP methods such as GET, POST, PUT, and DELETE.

# Java Networking Classes to Remember
Socket
ServerSocket
InetAddress

HttpClient
HttpRequest
HttpResponse

ObjectMapper   // Jackson
Gson

SSLContext
URI
URL

# These topics form the foundation before learning Servlets, JSP, Spring MVC, Spring Boot REST APIs, Microservices, and Reactive Programming.