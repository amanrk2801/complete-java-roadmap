# Software Engineering Principles 
# 1. SOLID Principles
# S - Single Responsibility Principle (SRP)
# A class should have only one responsibility and only one reason to change.
# ❌ Bad
class Employee {
    public void calculateSalary() {
        // salary logic
    }

    public void generateReport() {
        // report logic
    }
}
# ✅ Good
class SalaryCalculator {
    public double calculateSalary(Employee employee) {
        return 50000;
    }
}

class ReportGenerator {
    public void generateReport(Employee employee) {
        System.out.println("Generating report");
    }
}

# O - Open/Closed Principle (OCP)
# Classes should be open for extension but closed for modification.
# ✅ Good
interface Shape {
    double area();
}

class Circle implements Shape {
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements Shape {
    private double length;
    private double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }
}

class AreaCalculator {
    public double calculate(Shape shape) {
        return shape.area();
    }
}
# New shapes can be added without changing AreaCalculator.

# L - Liskov Substitution Principle (LSP)
# A subclass should be able to replace its parent class without breaking functionality.
# ❌ Bad
class Bird {
    public void fly() {
        System.out.println("Flying");
    }
}

class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException();
    }
}
# ✅ Good
interface Bird {
}

interface FlyingBird extends Bird {
    void fly();
}

class Sparrow implements FlyingBird {
    @Override
    public void fly() {
        System.out.println("Flying");
    }
}

class Penguin implements Bird {
}

# I - Interface Segregation Principle (ISP)
# Clients should not be forced to implement methods they don't use.
# ❌ Bad
interface Worker {
    void work();
    void eat();
}
# ✅ Good
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class HumanWorker implements Workable, Eatable {
    public void work() {
        System.out.println("Working");
    }

    public void eat() {
        System.out.println("Eating");
    }
}

# D - Dependency Inversion Principle (DIP)
# Depend on abstractions, not concrete classes.
# ✅ Good
interface Database {
    void save(String data);
}

class MySQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saved to MySQL");
    }
}

class UserService {
    private Database database;

    UserService(Database database) {
        this.database = database;
    }

    public void saveUser(String user) {
        database.save(user);
    }
}
# Usage:
Database db = new MySQLDatabase();
UserService service = new UserService(db);

# 2. DRY (Don't Repeat Yourself)
# Avoid duplicating code.
# ❌ Bad
int studentSalary = hours * rate;
int teacherSalary = hours * rate;
# ✅ Good
class SalaryUtil {

    public static int calculateSalary(int hours, int rate) {
        return hours * rate;
    }
}

# Usage:
int salary = SalaryUtil.calculateSalary(10, 100);

# 3. KISS (Keep It Simple, Stupid)
# Keep solutions simple.
# ❌ Complex
if (isLoggedIn == true) {
    System.out.println("Welcome");
}
# ✅ Simple
if (isLoggedIn) {
    System.out.println("Welcome");
}

# Another Example:
❌
int result = (a > b) ? a : b;
✅
int result = Math.max(a, b);

# 4. YAGNI (You Aren't Gonna Need It)
# Do not build features until they are required.
# ❌ Bad
class UserService {

    public void saveUser() {}

    public void exportToPdf() {}

    public void exportToExcel() {}

    public void exportToXml() {}
}
# ✅ Good
class UserService {

    public void saveUser() {
        System.out.println("Saving user");
    }
}
# Implement export features only when needed.

# 5. Separation of Concerns (SoC)
# Each layer should have a distinct responsibility.
# Model
class Student {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
# Service
class StudentService {

    public void saveStudent(Student student) {
        System.out.println("Student saved");
    }
}
# Controller
class StudentController {

    private StudentService service =
            new StudentService();

    public void createStudent() {
        Student student =
                new Student(1, "Aman");

        service.saveStudent(student);
    }
}

# 6. Dependency Inversion
# Without DIP
class MySQLDatabase {

    public void save() {
        System.out.println("Saved");
    }
}

class UserService {

    private MySQLDatabase database =
            new MySQLDatabase();

    public void saveUser() {
        database.save();
    }
}
# With DIP
interface Database {
    void save();
}

class MySQLDatabase implements Database {

    @Override
    public void save() {
        System.out.println("Saved");
    }
}

class UserService {

    private Database database;

    UserService(Database database) {
        this.database = database;
    }

    public void saveUser() {
        database.save();
    }
}

# 7. Composition Over Inheritance
# Prefer has-a relationship over is-a when possible.
# Inheritance
class Engine {

    public void start() {
        System.out.println("Engine Started");
    }
}

class Car extends Engine {
}
# Problem:
# - Car is not an Engine.
# - Wrong relationship.

# Composition
class Engine {

    public void start() {
        System.out.println("Engine Started");
    }
}

class Car {

    private Engine engine;

    Car() {
        engine = new Engine();
    }

    public void startCar() {
        engine.start();
    }
}
# Usage:
Car car = new Car();
car.startCar();

# 8. Clean Code
# Use Meaningful Names
❌ 
int x = 10;
✅
int studentCount = 10;

# Keep Methods Small
✅
class OrderService {

    public void placeOrder() {
        validateOrder();
        saveOrder();
        sendEmail();
    }

    private void validateOrder() {}

    private void saveOrder() {}

    private void sendEmail() {}
}

# Avoid Magic Numbers
❌
if (age >= 18) {
    System.out.println("Eligible");
}
✅
class Constants {
    public static final int VOTING_AGE = 18;
}

if (age >= Constants.VOTING_AGE) {
    System.out.println("Eligible");
}

# Follow Consistent Formatting
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }
}

# 9. Clean Architecture Fundamentals
# Layer Structure
Frameworks & Drivers 
↓ 
Interface Adapters 
↓ 
Use Cases 
↓ 
Entities

# Entity Layer
class Product {

    private int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

# Use Case Layer
interface ProductRepository {
    void save(Product product);
}

class CreateProductUseCase {

    private ProductRepository repository;

    CreateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public void execute(Product product) {
        repository.save(product);
    }
}

# Interface Adapter Layer
class ProductController {

    private CreateProductUseCase useCase;

    ProductController(CreateProductUseCase useCase) {
        this.useCase = useCase;
    }

    public void create() {
        Product product =
                new Product(1, "Laptop");

        useCase.execute(product);
    }
}

# Framework Layer
class MySQLProductRepository
        implements ProductRepository {

    @Override
    public void save(Product product) {
        System.out.println("Saved in MySQL");
    }
}

# Interview Revision
# What problem does each principle solve?
| Principle          | Solves                                 |
| ------------------ | -------------------------------------- |
| SRP                | Too many responsibilities in one class |
| OCP                | Frequent modification of existing code |
| LSP                | Incorrect inheritance hierarchies      |
| ISP                | Fat interfaces                         |
| DIP                | Tight coupling                         |
| DRY                | Duplicate code                         |
| KISS               | Over-engineering                       |
| YAGNI              | Unnecessary features                   |
| SoC                | Mixed responsibilities                 |
| Composition        | Rigid inheritance                      |
| Clean Code         | Poor readability                       |
| Clean Architecture | Framework-dependent business logic     |

# Easy Formula to Remember
KISS → DRY → SOLID → Composition → Clean Code → Clean Architecture

# This is the typical progression followed by professional Java developers while designing maintainable enterprise applications such as Spring Boot projects.

# ------------------------------------------- #

# Design Patterns in Java
# Design Patterns are proven solutions to recurring software design problems. They are categorized into:
# 1. Creational Patterns → Object creation mechanisms.
# 2. Structural Patterns → Object composition and relationships.
# 3. Behavioral Patterns → Communication and responsibility between objects.

# 1. Creational Design Patterns
# 1. Singleton Pattern
# Purpose
# Ensures only one instance of a class exists and provides a global access point.
# Use Cases
# - Logger
# - Database Connection
# - Configuration Manager

# Java Example
class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

public class Main {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        System.out.println(s1 == s2);
    }
}

# Output: true

# 2. Factory Method Pattern
# Purpose
# Creates objects without exposing creation logic.
# Example
interface Vehicle {
    void drive();
}

class Car implements Vehicle {
    public void drive() {
        System.out.println("Driving Car");
    }
}

class Bike implements Vehicle {
    public void drive() {
        System.out.println("Driving Bike");
    }
}

class VehicleFactory {
    public static Vehicle getVehicle(String type) {
        if(type.equalsIgnoreCase("car"))
            return new Car();
        return new Bike();
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v = VehicleFactory.getVehicle("car");
        v.drive();
    }
}

# 3. Abstract Factory Pattern
# Purpose
# Creates families of related objects.
# Example
interface Button {
    void click();
}

interface Checkbox {
    void check();
}

class WindowsButton implements Button {
    public void click() {
        System.out.println("Windows Button");
    }
}

class WindowsCheckbox implements Checkbox {
    public void check() {
        System.out.println("Windows Checkbox");
    }
}

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WindowsButton();
    }

    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

# 4. Builder Pattern
# Purpose
# Constructs complex objects step by step.
# Example
class Computer {
    private String cpu;
    private int ram;

    private Computer(Builder builder) {
        cpu = builder.cpu;
        ram = builder.ram;
    }

    static class Builder {
        private String cpu;
        private int ram;

        public Builder setCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder setRam(int ram) {
            this.ram = ram;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Computer pc = new Computer.Builder()
                .setCpu("Intel i9")
                .setRam(16)
                .build();
    }
}

# 5. Prototype Pattern
# Purpose
# Creates new objects by cloning existing objects.
# Example
class Student implements Cloneable {
    String name;

    Student(String name) {
        this.name = name;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

# 2. Structural Design Patterns
# 1. Adapter Pattern
# Purpose
# Converts one interface into another.
# Example
interface MediaPlayer {
    void play(String file);
}

class Mp4Player {
    void playMp4(String file) {
        System.out.println("Playing MP4: " + file);
    }
}

class Adapter implements MediaPlayer {
    private Mp4Player player = new Mp4Player();

    public void play(String file) {
        player.playMp4(file);
    }
}

# 2. Decorator Pattern
# Purpose
# Adds new behavior dynamically.
# Example
interface Coffee {
    String getDescription();
}

class SimpleCoffee implements Coffee {
    public String getDescription() {
        return "Coffee";
    }
}

class MilkDecorator implements Coffee {
    private Coffee coffee;

    MilkDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    public String getDescription() {
        return coffee.getDescription() + " + Milk";
    }
}

# 3. Facade Pattern
# Purpose
# Provides a simplified interface to a complex system.
# Example
class CPU {
    void start() {
        System.out.println("CPU Started");
    }
}

class Memory {
    void load() {
        System.out.println("Memory Loaded");
    }
}

class ComputerFacade {
    private CPU cpu = new CPU();
    private Memory memory = new Memory();

    void startComputer() {
        cpu.start();
        memory.load();
    }
}

# 4. Proxy Pattern
# Purpose
# Controls access to an object.
# Example
interface Internet {
    void connect(String site);
}

class RealInternet implements Internet {
    public void connect(String site) {
        System.out.println("Connected to " + site);
    }
}

class ProxyInternet implements Internet {
    private RealInternet internet = new RealInternet();

    public void connect(String site) {
        if(site.equals("facebook.com"))
            System.out.println("Blocked");
        else
            internet.connect(site);
    }
}

# 5. Composite Pattern
# Purpose
# Treat individual objects and groups uniformly.
# Example
interface Employee {
    void showDetails();
}

class Developer implements Employee {
    public void showDetails() {
        System.out.println("Developer");
    }
}

class Manager implements Employee {
    private List<Employee> employees = new ArrayList<>();

    void add(Employee emp) {
        employees.add(emp);
    }

    public void showDetails() {
        for(Employee emp : employees)
            emp.showDetails();
    }
}

# 6. Bridge Pattern
# Purpose
# Separates abstraction from implementation.
# Example
interface Device {
    void on();
}

class TV implements Device {
    public void on() {
        System.out.println("TV On");
    }
}

class Remote {
    private Device device;

    Remote(Device device) {
        this.device = device;
    }

    void turnOn() {
        device.on();
    }
}

# 7. Flyweight Pattern
# Purpose
# Shares common object state to save memory.
# Example
class Character {
    private String symbol;

    Character(String symbol) {
        this.symbol = symbol;
    }
}
# Only one object can be shared among many users.

# 3. Behavioral Design Patterns
# 1. Strategy Pattern
# Purpose
# Defines interchangeable algorithms.
# Example
interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid by Credit Card");
    }
}

class UpiPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid by UPI");
    }
}

# 2. Observer Pattern
# Purpose
# Notifies multiple objects about state changes.
# Example
interface Observer {
    void update(String msg);
}

class User implements Observer {
    public void update(String msg) {
        System.out.println(msg);
    }
}

# 3. Command Pattern
# Purpose
# Encapsulates a request as an object.
# Example
interface Command {
    void execute();
}

class LightOnCommand implements Command {
    public void execute() {
        System.out.println("Light ON");
    }
}

# 4. Template Method Pattern
# Purpose
# Defines skeleton of an algorithm.
# Example
abstract class Game {

    abstract void start();
    abstract void play();

    final void run() {
        start();
        play();
    }
}

# 5. State Pattern
# Purpose
# Changes behavior when object's state changes.
# Example
interface State {
    void handle();
}

class OpenState implements State {
    public void handle() {
        System.out.println("Door Open");
    }
}

# 6. Chain of Responsibility
# Purpose
# Pass request through chain of handlers.
# Example
abstract class Handler {
    protected Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    abstract void process(int amount);
}

# 7. Iterator Pattern
# Purpose
# Sequentially access elements without exposing structure.
# Example
List<String> names = Arrays.asList("A", "B", "C");

Iterator<String> it = names.iterator();

while(it.hasNext()) {
    System.out.println(it.next());
}

# 8. Mediator Pattern
# Purpose
# Reduces direct communication between objects.
# Example
interface Mediator {
    void send(String message);
}
# Chat applications frequently use Mediator.

# 9. Visitor Pattern
# Purpose
# Adds operations without modifying classes.
# Example
interface Visitor {
    void visit(Book book);
}

# 10. Specification Pattern
# Purpose
# Encapsulates business rules into reusable specifications.
# Example
interface Specification<T> {
    boolean isSatisfiedBy(T item);
}

class AgeSpecification implements Specification<Person> {
    public boolean isSatisfiedBy(Person p) {
        return p.getAge() >= 18;
    }
}

# Quick Revision Table
| Pattern                 | Category   | Purpose                    |
| ----------------------- | ---------- | -------------------------- |
| Singleton               | Creational | Single Instance            |
| Factory Method          | Creational | Object Creation            |
| Abstract Factory        | Creational | Family of Objects          |
| Builder                 | Creational | Complex Object Creation    |
| Prototype               | Creational | Clone Objects              |
| Adapter                 | Structural | Interface Conversion       |
| Decorator               | Structural | Add Features               |
| Facade                  | Structural | Simplify Complex System    |
| Proxy                   | Structural | Control Access             |
| Composite               | Structural | Tree Structure             |
| Bridge                  | Structural | Separate Abstraction       |
| Flyweight               | Structural | Memory Optimization        |
| Strategy                | Behavioral | Interchangeable Algorithms |
| Observer                | Behavioral | Publish/Subscribe          |
| Command                 | Behavioral | Encapsulate Requests       |
| Template Method         | Behavioral | Algorithm Skeleton         |
| State                   | Behavioral | State-based Behavior       |
| Chain of Responsibility | Behavioral | Request Pipeline           |
| Iterator                | Behavioral | Sequential Traversal       |
| Mediator                | Behavioral | Central Communication      |
| Visitor                 | Behavioral | Add Operations             |
| Specification           | Behavioral | Business Rules             |

// Most Important for Interviews
✅ Singleton
✅ Factory Method
✅ Abstract Factory
✅ Builder
✅ Adapter
✅ Decorator
✅ Proxy
✅ Strategy
✅ Observer
✅ Command
✅ Template Method
✅ State

# These are the patterns most frequently asked in Java and Spring Boot interviews.