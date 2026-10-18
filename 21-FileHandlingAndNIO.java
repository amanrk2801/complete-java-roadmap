# Java File Handling and NIO
# 1. File (java.io.File)
# File represents a file or directory path in the file system.
# Common Methods
import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("demo.txt");

        System.out.println(file.exists());
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
    }
}

# Important Methods
file.exists();
file.createNewFile();
file.delete();
file.mkdir();      // create directory
file.mkdirs();     // create nested directories
file.length();
file.listFiles();

# 2. Path (java.nio.file.Path)
# Introduced in NIO. More powerful than File.
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("data.txt");

        System.out.println(path.getFileName());
        System.out.println(path.getParent());
        System.out.println(path.toAbsolutePath());
    }
}

# 3. Paths (java.nio.file.Paths)
# Utility class to obtain Path objects.
import java.nio.file.Path;
import java.nio.file.Paths;

Path path = Paths.get("demo.txt");
# Java 11+: Path path = Path.of("demo.txt");

# 4. Files (java.nio.file.Files)
# Utility class containing file operations.
# Create File
Files.createFile(Path.of("test.txt"));
# Read File
String content = Files.readString(Path.of("test.txt"));
System.out.println(content);
# Write File
Files.writeString(
    Path.of("test.txt"),
    "Hello Java"
);
# Check Existence
Files.exists(Path.of("test.txt"));

# 5. FileReader and FileWriter
# Character-based file handling.
# FileReader
import java.io.FileReader;

FileReader fr = new FileReader("demo.txt");

int ch;
while((ch = fr.read()) != -1){
    System.out.print((char) ch);
}
fr.close();

# FileWriter
import java.io.FileWriter;

FileWriter fw = new FileWriter("demo.txt");

fw.write("Hello Java");
fw.close();
# Append mode:
FileWriter fw = new FileWriter("demo.txt", true);

# 6. BufferedReader and BufferedWriter
# Use buffering for better performance.
# BufferedReader
import java.io.*;

BufferedReader br =
        new BufferedReader(
                new FileReader("demo.txt"));

String line;

while((line = br.readLine()) != null){
    System.out.println(line);
}

br.close();

# BufferedWriter
BufferedWriter bw =
        new BufferedWriter(
                new FileWriter("demo.txt"));

bw.write("Java Programming");
bw.newLine();
bw.write("NIO Example");

bw.close();

# 7. InputStream and OutputStream
# Base classes for byte streams.
# InputStream
InputStream in =
        new FileInputStream("image.jpg");

int data;

while((data = in.read()) != -1){
    System.out.print(data);
}

in.close();

# OutputStream
OutputStream out =
        new FileOutputStream("copy.jpg");

out.write(100);
out.close();

# 8. FileInputStream and FileOutputStream
# Used for binary files.
# Copy Binary File
FileInputStream fis =
        new FileInputStream("a.jpg");

FileOutputStream fos =
        new FileOutputStream("b.jpg");

int data;

while((data = fis.read()) != -1){
    fos.write(data);
}

fis.close();
fos.close();

# 9. PrintWriter
# Provides formatted output.
import java.io.PrintWriter;

PrintWriter pw =
        new PrintWriter("demo.txt");

pw.println("Hello");
pw.println(100);
pw.printf("Value = %d", 50);

pw.close();

# 10. Byte Streams vs Character Streams
# Byte Streams
# - Handle raw binary data
# - 8-bit data
# - Images
# - Videos
# - PDFs
# Examples:
InputStream
OutputStream
FileInputStream
FileOutputStream

# Character Streams
# - Handle text data
# - 16-bit Unicode characters
# Examples:
Reader
Writer
FileReader
FileWriter

# Rule
Text Files  → Character Streams
Binary Files → Byte Streams

# 11. Buffering
# Buffering reduces disk access and improves performance.
# Without buffering:
FileReader reader =
        new FileReader("a.txt");
# With buffering:
BufferedReader reader =
        new BufferedReader(
                new FileReader("a.txt"));
# Benefits:
# - Faster reading
# - Faster writing
# - Fewer I/O operations

# 12. File Encoding
# Encoding converts characters to bytes.
# Common encodings:
ASCII
UTF-8
UTF-16
ISO-8859-1

# Reading UTF-8 File
BufferedReader br =
    new BufferedReader(
      new InputStreamReader(
        new FileInputStream("demo.txt"),
        "UTF-8"
      ));

# Writing UTF-8
BufferedWriter bw =
    new BufferedWriter(
      new OutputStreamWriter(
        new FileOutputStream("demo.txt"),
        "UTF-8"
      ));

# 13. File Copying, Moving and Deletion
# Using Files
# Copy
Files.copy(
    Path.of("a.txt"),
    Path.of("b.txt")
);
# Move
Files.move(
    Path.of("a.txt"),
    Path.of("new/a.txt")
);
# Delete
Files.delete(
    Path.of("a.txt")
);

# 14. Directory Traversal
# List all files.
Files.list(Path.of("."))
     .forEach(System.out::println);
# Recursive traversal:
Files.walk(Path.of("."))
     .forEach(System.out::println);

# 15. Serialization
# Process of converting an object into bytes.
class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    String name;
}

# 16. ObjectInputStream and ObjectOutputStream
# Serialization
ObjectOutputStream oos =
    new ObjectOutputStream(
       new FileOutputStream("student.dat"));

Student s =
    new Student();

s.id = 1;
s.name = "John";

oos.writeObject(s);
oos.close();

# Deserialization
ObjectInputStream ois =
    new ObjectInputStream(
        new FileInputStream("student.dat"));

Student s =
        (Student) ois.readObject();

System.out.println(s.name);

ois.close();

# 17. Serialization Risks
# Problems
# - Security vulnerabilities
# - Version mismatch errors
# - Large file size
# - Corrupted data risk
# Best Practices
private static final long serialVersionUID = 1L;
# Use: transient
# for sensitive fields.
transient String password;

# 18. NIO Channels and Buffers
# Channel
# Moves data between source and destination.
# FileChannel Example
FileInputStream fis =
        new FileInputStream("demo.txt");

FileChannel channel =
        fis.getChannel();
# Buffer
# Temporary memory storage.
ByteBuffer buffer =
        ByteBuffer.allocate(1024);
# Read Using Buffer
while(channel.read(buffer) > 0){
    buffer.flip();

    while(buffer.hasRemaining()){
        System.out.print(
            (char) buffer.get()
        );
    }

    buffer.clear();
}

# 19. File Locks
# Prevent multiple processes from modifying a file simultaneously.
FileChannel channel =
    new RandomAccessFile(
       "demo.txt","rw"
    ).getChannel();

FileLock lock =
        channel.lock();

System.out.println("Locked");

lock.release();
channel.close();

# 20. WatchService
# Monitors file system changes.
WatchService watchService =
    FileSystems.getDefault()
               .newWatchService();

Path path = Path.of("data");

path.register(
        watchService,
        StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_DELETE,
        StandardWatchEventKinds.ENTRY_MODIFY
);

# Processing events:
while(true){
    WatchKey key =
         watchService.take();

    for(WatchEvent<?> event :
            key.pollEvents()){
        System.out.println(event.kind());
    }

    key.reset();
}

# 21. ZIP Files
# Create ZIP file.
ZipOutputStream zos =
    new ZipOutputStream(
       new FileOutputStream("data.zip")
    );

ZipEntry entry =
    new ZipEntry("demo.txt");

zos.putNextEntry(entry);

Files.copy(
    Path.of("demo.txt"),
    zos
);

zos.closeEntry();
zos.close();

# Read ZIP:
ZipInputStream zis =
    new ZipInputStream(
       new FileInputStream("data.zip")
    );

# 22. CSV and JSON Processing
# CSV Example
String csvLine =
        "1,Aman,50000";

String[] parts =
        csvLine.split(",");

System.out.println(parts[1]);
# Reading CSV:
BufferedReader br =
        new BufferedReader(
                new FileReader("data.csv"));

String line;

while((line = br.readLine()) != null){
    String[] data = line.split(",");
}
# JSON Example (Jackson)
# Maven Dependency
<dependency>
   <groupId>com.fasterxml.jackson.core</groupId>
   <artifactId>jackson-databind</artifactId>
   <version>2.17.2</version>
</dependency>

# Java Object
class Student {
    public int id;
    public String name;
}

# Convert Object → JSON
ObjectMapper mapper =
        new ObjectMapper();

Student s = new Student();
s.id = 1;
s.name = "Aman";

String json =
        mapper.writeValueAsString(s);

System.out.println(json);

# Convert JSON → Object
Student s =
      mapper.readValue(
         json,
         Student.class
      );

# Interview Questions
# Q1. Difference between File and Path?
# File belongs to old IO API.
# Path belongs to NIO API.
# Path is more flexible and powerful.

# Q2. Difference between FileReader and FileInputStream?
# FileReader reads characters.
# FileInputStream reads bytes.

# Q3. Why BufferedReader is faster?
# Reads data in chunks using an internal buffer.
# Reduces disk access.

# Q4. What is Serialization?
# Conversion of object into byte stream for storage or transmission.

# Q5. Difference between Files.copy() and manual stream copying?
# Files.copy() is concise and optimized.
# Manual copy gives fine-grained control.

# Q6. What is NIO?
# NIO (New I/O) provides:
# - Non-blocking operations
# - Channels
# - Buffers
# - Path API
# - Better performance than traditional IO

# Q7. What is WatchService?
# API that monitors file system changes such as:
# - Create, Modify, Delete

# Q8. What is FileLock?
# Mechanism to lock files and prevent concurrent modifications.