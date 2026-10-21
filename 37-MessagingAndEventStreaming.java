# Messaging and Event Streaming
# 1. Messaging Fundamentals
# Messaging is a communication mechanism where applications exchange data through messages instead of directly calling each other.
# Benefits:
# - Loose coupling
# - Scalability
# - Reliability
# - Fault tolerance
# - Asynchronous processing
# Example
# Order Service sends an order event:
{
    "orderId": 101,
    "status": "CREATED"
}
# Inventory Service consumes the message and updates stock.

# 2. Synchronous vs Asynchronous Communication
# Synchronous Communication
# The sender waits for a response.
Client ---> Service <--- Response
# Example
# REST API
Order order = restTemplate.getForObject(
    "/orders/101",
    Order.class
);
# Advantages
# - Simple
# - Immediate response
# Disadvantages
# - Tight coupling
# - Higher latency
# - Service dependency

# Asynchronous Communication
# Sender doesn't wait.
Producer ---> Broker ---> Consumer
# Example
kafkaTemplate.send("orders", order);
# Advantages
# - Decoupling
# - Better scalability
# - Resilience
# Disadvantages
# - More complexity
# - Eventual consistency

# 3. Message Brokers
# A Message Broker acts as an intermediary between producers and consumers.
# Popular brokers:
# - Apache Kafka
# - RabbitMQ
# - ActiveMQ
# - Amazon SQS
# - Azure Service Bus
Producer
    |
    V
 Message Broker
    |
    V
 Consumer

# Responsibilities:
# - Message storage
# - Routing
# - Retry handling
# - Delivery guarantees

# 4. Apache Kafka
# Kafka is a distributed event streaming platform designed for high throughput and fault tolerance.
# Features
# - Distributed
# - Persistent storage
# - Horizontal scalability
# - High availability
# Kafka Architecture
Producer
   |
   V
 Topic
   |
   V
Partition
   |
   V
Consumer Group

# 5. Topics and Partitions
# Topic
# - Logical category of messages.
# Example:
orders
payments
shipments

# Partition
# - Each topic is divided into partitions.
orders topic

Partition 0
Partition 1
Partition 2

# Benefits:
# - Parallel processing
# - Scalability
# - Fault tolerance

# Key-based Partitioning
kafkaTemplate.send(
    "orders",
    customerId,
    order
);
# Messages with the same key go to the same partition.

# 6. Producers and Consumers
# Producer
# - Sends messages to Kafka.
@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String message) {
        kafkaTemplate.send("orders", message);
    }
}

# Consumer
# - Reads messages from Kafka.
@KafkaListener(topics = "orders")
public void consume(String message) {
    System.out.println(message);
}

# 7. Consumer Groups
# A consumer group is a collection of consumers working together.
orders topic

Partition 0 --> Consumer 1
Partition 1 --> Consumer 2
Partition 2 --> Consumer 3
# Rules
# - One partition is consumed by only one consumer in a group.
# - Different groups can consume the same message.
# Example:
@KafkaListener(
    topics = "orders",
    groupId = "billing-group"
)

# 8. Offsets
# Offset = Position of a message in a partition.
Partition 0

Offset 0
Offset 1
Offset 2
Offset 3
# Kafka tracks which messages have been consumed.

# Commit Types
# Auto Commit
enable.auto.commit=true
# Manual Commit
ack.acknowledge();
# Manual commit provides more reliability.

# 9. Delivery Semantics
# At Most Once
# Message may be lost.
Process -> Commit
# No duplicates.

# At Least Once
# Message may be delivered multiple times.
Read -> Process -> Commit
# Most common approach.

# Exactly Once
# - Message processed once and only once.
# - Kafka supports EOS (Exactly Once Semantics).
enable.idempotence=true

# 10. Retries and Dead-Letter Queues (DLQ)
# Retry
# - Consumer processing fails.
Message
   |
 Retry #1
 Retry #2
 Retry #3

# Spring Kafka:
@RetryableTopic(
    attempts = "3"
)

# Dead-Letter Queue (DLQ)
# - Failed messages move to a special topic.
orders
   |
   V
orders.DLT
# Example:
@DltHandler
public void dlt(String msg) {
    System.out.println("Dead Letter: " + msg);
}
# Benefits:
# - No message loss
# - Later analysis
# - Easier debugging

# 11. Ordering Guarantees
# Ordering is guaranteed only within a partition.
Partition 0

Msg1
Msg2
Msg3
# Order preserved.

# Across partitions:
Partition 0 : Msg1
Partition 1 : Msg2
# Order is not guaranteed.

# Maintain Ordering
# - Use the same key.
kafkaTemplate.send(
    "orders",
    orderId,
    order
);

# 12. Schema Evolution
# Message formats change over time.
# Version 1:
{
  "id": 1,
  "name": "John"
}
# Version 2:
{
  "id": 1,
  "name": "John",
  "email": "john@gmail.com"
}
# Problems:
# - Consumer compatibility
# - Producer compatibility
# Solutions:
# - Apache Avro
# - JSON Schema
# - Protobuf
# - Schema Registry
# Example Avro:
Customer customer = Customer.newBuilder()
    .setName("John")
    .build();

# 13. Spring for Apache Kafka
# Spring Boot integration for Kafka.
# Dependency:
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
# Configuration:
spring.kafka.bootstrap-servers=localhost:9092
# Producer:
@Autowired
private KafkaTemplate<String, String> kafkaTemplate;
# Consumer:
@KafkaListener(
    topics = "orders",
    groupId = "orders-group"
)
public void listen(String message) {
    System.out.println(message);
}

# 14. RabbitMQ
# RabbitMQ is a message broker implementing AMQP.
# Components
Producer
   |
Exchange
   |
 Queue
   |
Consumer

# Exchange Types
# Direct Exchange
# - Routes by exact key.
Key = order.created

# Topic Exchange
# - Pattern-based routing.
order.*

# Fanout Exchange
# - Broadcasts to all queues.

# Headers Exchange
# - Routes using headers.

# Spring RabbitMQ Example
# Producer:
rabbitTemplate.convertAndSend(
    "exchange",
    "routingKey",
    message
);

# Consumer:
@RabbitListener(queues = "orders")
public void receive(String msg) {
    System.out.println(msg);
}

# 15. JMS (Java Message Service)
# JMS is a Java API specification for messaging.
# Supported Brokers:
# - ActiveMQ
# - IBM MQ
# - HornetQ

# JMS Architecture
Producer
   |
 JMS Provider
   |
Consumer

# JMS Models
# Point-to-Point
Producer --> Queue --> Consumer
# Only one consumer receives the message.

# Publish-Subscribe
Producer --> Topic
             |
        -------------
        |           |
 Subscriber1 Subscriber2
# All subscribers receive messages.

# JMS Example
@Autowired
private JmsTemplate jmsTemplate;

jmsTemplate.convertAndSend(
    "orders",
    "Order Created"
);

# Consumer:
@JmsListener(destination = "orders")
public void receive(String msg) {
    System.out.println(msg);
}

# 16. Transactional Messaging
# Ensures database updates and message publishing occur reliably.
# Problem
saveOrder();
publishMessage();
# If DB save succeeds but publish fails:
Database Updated
Message Missing
# Data inconsistency occurs.

# Solution 1: Kafka Transactions
@Transactional
public void createOrder() {
    repository.save(order);
    kafkaTemplate.send("orders", order);
}

# Solution 2: Transactional Outbox Pattern
# Step 1
# Save business data.
Orders Table

# Step 2
# Save event in outbox table.
Outbox Table

# Step 3
# Background publisher sends event to Kafka.
Outbox -> Kafka

# Benefits
# - No lost events
# - Reliable publishing
# - Microservice friendly

# Kafka vs RabbitMQ vs JMS
Feature             | Kafka                     | RabbitMQ      | JMS
Type                | Event Streaming Platform  | Message Broker| Java API Specification
Persistence         | Excellent                 | Good          | Depends on Provider
Throughput          | Very High                 | High          | Medium
Ordering            | Per Partition             | Per Queue     | Provider Dependent
Replay Messages     | Yes                       | Limited       | Depends
Event Sourcing      | Excellent                 | Poor          | Poor
Microservices       | Excellent                 | Good          | Good
Real-time Analytics | Excellent                 | Limited       | Limited

# Interview Questions
# 1. Difference between Kafka and RabbitMQ?
# Kafka is log-based and optimized for event streaming, while RabbitMQ is queue-based and optimized for message routing.

# 2. What is a partition in Kafka?
# A partition is an ordered, immutable sequence of records within a topic that enables parallel processing.

# 3. What is a consumer group?
# A group of consumers that share the workload of consuming records from a topic.

# 4. What is an offset?
# A unique position number of a message within a Kafka partition.

# 5. What is a Dead Letter Queue?
# A queue/topic where messages are moved after repeated processing failures.

# 6. What is the Outbox Pattern?
# A pattern that stores events in a database table and publishes them later to guarantee reliable message delivery.