# Control Flow in Java
# Control Flow determines the order in which statements are executed in a program.

# 1. if Statement
# - Used to execute a block of code only when a condition is true.
int age = 20;

if (age >= 18) {
    System.out.println("Adult");
}

# Output: Adult

# 2. if-else Statement
# - Executes one block if the condition is true, otherwise another block.
int age = 16;

if (age >= 18) {
    System.out.println("Adult");
} else {
    System.out.println("Minor");
}

# Output: Minor

# 3. Nested if
# - An if statement inside another if.
int age = 20;
boolean hasLicense = true;

if (age >= 18) {
    if (hasLicense) {
        System.out.println("Can drive");
    }
}

# Output: Can drive

# 4. else-if Ladder
# - Used when multiple conditions need to be checked.
int marks = 75;

if (marks >= 90) {
    System.out.println("Grade A");
} else if (marks >= 75) {
    System.out.println("Grade B");
} else if (marks >= 60) {
    System.out.println("Grade C");
} else {
    System.out.println("Fail");
}

# Output: Grade B

# 5. Traditional switch
# Used to select one block from many alternatives.
int day = 3;

switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    case 3:
        System.out.println("Wednesday");
        break;
    default:
        System.out.println("Invalid Day");
}

# Output: Wednesday

# 6. Switch Expressions (Java 14+)
# - Modern switch syntax that returns values.
int day = 2;

String result = switch(day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    default -> "Invalid";
};

System.out.println(result);

# Output: Tuesday

# 7. for Loop
# - Used when the number of iterations is known.
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
# Output: 1 2 3 4 5 

# 8. Enhanced for Loop (For-Each)
# - Used to iterate through arrays or collections.
int[] nums = {10, 20, 30};

for (int n : nums) {
    System.out.println(n);
}

# Output: 10 20 30 

# 9. while Loop
# - Executes while the condition remains true.
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
}

# Output: 1 2 3 4 5

# 10. do-while Loop
# - Executes at least once before checking the condition.
int i = 1;

do {
    System.out.println(i);
    i++;
} while (i <= 5);

# Output: 1 2 3 4 5 

# 11. Nested Loops
# - A loop inside another loop.
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 2; j++) {
        System.out.println(i + " " + j);
    }
}

# Output:
1 1 
1 2 
2 1 
2 2 
3 1 
3 2

# 12. break
# - Terminates the loop immediately.
for (int i = 1; i <= 10; i++) {
    if (i == 5) {
        break;
    }
    System.out.println(i);
}

# Output: 1 2 3 4

# 13. continue
# - Skips the current iteration.
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue;
    }
    System.out.println(i);
}

# Output: 1 2 4 5

# 14. return
# - Exits a method and optionally returns a value.
public static int add(int a, int b) {
    return a + b;
}

public static void main(String[] args) {
    System.out.println(add(5, 3));
}

# Output: 8

# 15. Labeled Statements
# - Used with nested loops to control flow.
outer:
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (i == 2 && j == 2) {
            break outer;
        }
        System.out.println(i + " " + j);
    }
}

# Output:
1 1 
1 2 
1 3 
2 1

# Quick Summary
1. if → Single condition
2. if-else → Two choices
3. else-if → Multiple choices
4. switch → Alternative to multiple if-else
5. for → Known iterations
6. while → Condition-based loop
7. do-while → Executes at least once
8. break → Exit loop
9. continue → Skip iteration
10. return → Exit method
11. Labeled statements → Control nested loops
12. Nested loops → Loop inside a loop
13. Enhanced for → Iterate arrays/collections easily
14. Switch expressions → Modern switch with returned values
