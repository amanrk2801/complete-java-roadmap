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