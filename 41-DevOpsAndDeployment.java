# DevOps and Deployment for Java
# DevOps is the combination of Development (Dev) and Operations (Ops) practices that automate building, testing, deployment, and monitoring of Java applications. A Java developer should understand the complete deployment lifecycle from code commit to production.

# 1. Git and GitHub
# What is Git?
# Git is a distributed version control system used to track source code changes.
# Common Commands
git init
git clone https://github.com/user/project.git

git status
git add .
git commit -m "Initial commit"

git push origin main
git pull origin main

# What is GitHub?
# GitHub is a cloud platform for hosting Git repositories.

# Workflow
Developer
    ↓
Git Commit
    ↓
GitHub Repository
    ↓
CI/CD Pipeline
    ↓
Deployment

# 2. Branching and Merging
# Branches allow independent development.
# Create Branch
git checkout -b feature-login
# Switch Branch
git checkout main
# Merge
git merge feature-login

# Popular Strategies
# Git Flow
main
 │
 ├── develop
 │     │
 │     ├── feature-1
 │     ├── feature-2
 │
 └── release
# Feature Branch Workflow
main
 ├── feature-auth
 ├── feature-payment
 └── feature-dashboard

# 3. Maven Build Pipelines
# Maven automates:
# - Compilation
# - Testing
# - Packaging
# - Dependency Management

# pom.xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

# Build Lifecycle
validate
   ↓
compile
   ↓
test
   ↓
package
   ↓
verify
   ↓
install
   ↓
deploy

# Commands
mvn clean
mvn compile
mvn test
mvn package
mvn install

# 4. Docker
# Docker packages an application and its dependencies into a container.
# Why Docker?
# Without Docker: Works on my machine ❌
# With Docker: Runs everywhere ✅
# Architecture
Docker Engine
     │
 ┌───┴───┐
 │Container│
 └───┬───┘
     │
 Spring Boot App

# 5. Dockerfile
# A Dockerfile defines how to build an image.
# Spring Boot Example
FROM eclipse-temurin:21

COPY target/app.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]

# Build image:
docker build -t spring-app .

# Run container:
docker run -p 8080:8080 spring-app

# 6. Multi-Stage Builds
# Reduces image size.
# Example
# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

COPY --from=build /app/target/app.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]

# Benefits
# - Smaller images
# - Faster deployment
# - Improved security

# 7. Docker Compose
# Manages multiple containers.
# Example
# Spring Boot + MySQL
version: '3'

services:

  app:
    build: .
    ports:
      - "8080:8080"

  mysql:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: root
# Run: docker compose up
# Stop: docker compose down

# 8. CI/CD
# CI (Continuous Integration)
# Automatically: Build, Test, Verify code
# - after every commit.
# CD (Continuous Delivery/Deployment)
# - Automatically deploy applications.
# Flow
Code Push
    ↓
Build
    ↓
Test
    ↓
Package
    ↓
Deploy

# 9. GitHub Actions
# Automates CI/CD on GitHub.
# Workflow File
name: Java CI

on:
  push:
    branches:
      - main

jobs:
  build:

    runs-on: ubuntu-latest

    steps:

      - uses: actions/checkout@v4

      - uses: actions/setup-java@v4
        with:
          java-version: 21
          distribution: temurin

      - run: mvn clean test
# Location: .github/workflows/build.yml

# 10. Linux Commands
# File Operations
ls
pwd
mkdir
rm
cp
mv

# View Files
cat file.txt
less file.txt
head file.txt
tail file.txt

# Process Management
ps
top
kill -9 PID

# Networking
ping google.com
netstat
ss
curl

# 11. Shell Scripting Basics
# Script
#!/bin/bash

echo "Deploying Application"

mvn clean package

docker build -t app .

docker run -d app

# Run:
chmod +x deploy.sh

./deploy.sh

# 12. Environment Variables
# Used to store configuration outside source code.
# Linux
export DB_URL=localhost
export DB_USER=root
# Spring Boot
spring.datasource.url=${DB_URL}
# Access in Java
String url = System.getenv("DB_URL");

# 13. AWS Fundamentals
# AWS provides cloud infrastructure.
# Popular Services:
EC2       → Virtual Server
S3        → Storage
RDS       → Database
IAM       → Security
CloudWatch→ Monitoring

# 14. EC2
# Elastic Compute Cloud provides virtual machines.
# Steps
Create EC2 Instance
        ↓
Install Java
        ↓
Deploy JAR
        ↓
Run Application

# Run app: java -jar app.jar

# 15. S3
# Simple Storage Service.
# Used for:
# - Images
# - Videos
# - Backups
# - Logs
# Upload using AWS CLI:
aws s3 cp app.jar s3://my-bucket/

# 16. IAM
# Identity and Access Management.
# Controls: Users, Roles, Permissions
# Example Policy
{
  "Effect": "Allow",
  "Action": "s3:*",
  "Resource": "*"
}
# Principle: Least Privilege Access

# 17. RDS
# Managed database service.
# Supported DBs:
# - MySQL
# - PostgreSQL
# - MariaDB
# - Oracle
# - SQL Server
# Spring Boot URL:
spring.datasource.url=
jdbc:mysql://rds-endpoint:3306/mydb

# 18. CloudWatch
# Monitoring and logging service.
# Tracks:
# - CPU usage
# - Memory metrics
# - Application logs
# - Alarms
# Example:
CPU > 80%
      ↓
CloudWatch Alarm
      ↓
Email Notification

# 19. Reverse Proxies
# A reverse proxy sits between clients and servers.
User
 ↓
Reverse Proxy
 ↓
Java Application
# Benefits: Load balancing, SSL termination, Security, Caching

# 20. Nginx
# Popular reverse proxy and web server.
# Configuration
server {

    listen 80;

    location / {

        proxy_pass http://localhost:8080;

        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}

# Start: sudo systemctl start nginx

# 21. Kubernetes Fundamentals
# - Kubernetes manages containerized applications.
# Important Components
# Pod
# Smallest deployable unit.
Pod
 └─ Spring Boot Container
# Deployment
# - Manages Pods.
# Service
# - Exposes Pods.
# Cluster
# - Collection of Nodes.

# Deployment YAML
apiVersion: apps/v1

kind: Deployment

metadata:
  name: spring-app

spec:
  replicas: 3

  selector:
    matchLabels:
      app: spring-app

  template:

    metadata:
      labels:
        app: spring-app

    spec:

      containers:

      - name: spring-app

        image: spring-app:1.0

        ports:
        - containerPort: 8080
# Apply: kubectl apply -f deployment.yml

# 22. Secrets Management
# Never store passwords in code.
# Bad: String password = "admin123";
# Good: DB_PASSWORD=secret
# Access: System.getenv("DB_PASSWORD");
# Kubernetes Secret:
kubectl create secret generic db-secret \
--from-literal=password=secret

# 23. Rolling Deployments
# Updates application gradually without downtime.
# Example
Old Version: 3 Pods

Pod1 v1
Pod2 v1
Pod3 v1

      ↓

Pod1 v2
Pod2 v1
Pod3 v1

      ↓

Pod1 v2
Pod2 v2
Pod3 v1

      ↓

Pod1 v2
Pod2 v2
Pod3 v2

# Benefits: Zero downtime, Easy monitoring, Minimal risk

# 24. Rollback Strategies
# If deployment fails:
Version 2 ❌

Rollback

Version 1 ✅

# Kubernetes
# View history:
kubectl rollout history deployment/spring-app

# Rollback:
kubectl rollout undo deployment/spring-app

# Complete Java DevOps Deployment Flow
Developer
   ↓
Git Commit
   ↓
GitHub
   ↓
GitHub Actions
   ↓
Maven Build
   ↓
JUnit Tests
   ↓
Docker Image
   ↓
Docker Registry
   ↓
AWS EC2 / Kubernetes
   ↓
Nginx Reverse Proxy
   ↓
Users

# Interview Questions
Difference between Git Merge and Rebase?
What is CI/CD?
Why use Docker for Java applications?
Difference between Docker Image and Container?
What is a Kubernetes Pod?
What is the purpose of Nginx?
What are multi-stage Docker builds?
Difference between EC2 and ECS?
What are Kubernetes Secrets?
How does rolling deployment work?

# This roadmap covers the essential DevOps and deployment skills expected from a Java backend developer working with Spring Boot applications.