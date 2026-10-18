# Java Date and Time API (java.time)
# The Java Date and Time API (java.time package) was introduced in Java 8 to replace the older Date and Calendar classes. It is immutable, thread-safe, and much easier to use.

# 1. Legacy Date and Calendar
# Before Java 8, date and time handling was done using Date and Calendar.
# Date
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
    }
}
# Problems
# - Mutable
# - Not thread-safe
# - Confusing API
# - Deprecated methods

# Calendar
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();

        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH));
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
    }
}

# 2. LocalDate
# Represents date only (year, month, day).
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        LocalDate today = LocalDate.now();

        System.out.println(today);

        LocalDate date =
                LocalDate.of(2026, 10, 18);

        System.out.println(date);
    }
}
# Output:
2026-10-18 
2026-10-18

# Useful Methods
LocalDate date = LocalDate.now();

System.out.println(date.getYear());
System.out.println(date.getMonth());
System.out.println(date.getDayOfMonth());
System.out.println(date.getDayOfWeek());

# 3. LocalTime
# Represents time only.
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        LocalTime time = LocalTime.now();

        System.out.println(time);
    }
}
# Example: 14:45:35.123456

# Create Specific Time
LocalTime time =
        LocalTime.of(10, 30, 15);

System.out.println(time);
# Output: 10:30:15

# 4. LocalDateTime
# Represents date + time without timezone.
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        LocalDateTime dt =
                LocalDateTime.now();

        System.out.println(dt);
    }
}
# Example: 2026-10-18T14:50:10.123

# Create Custom DateTime
LocalDateTime dt =
        LocalDateTime.of(
                2026, 10, 18,
                10, 30, 0);

System.out.println(dt);

# 5. ZonedDateTime
# Represents date-time with a time zone.
import java.time.*;

public class Main {
    public static void main(String[] args) {

        ZonedDateTime zdt =
                ZonedDateTime.now();

        System.out.println(zdt);
    }
}
# Example: 2026-10-18T10:30:25+05:30[Asia/Kolkata]

# Specific Zone
ZonedDateTime usa =
        ZonedDateTime.now(
                ZoneId.of("America/New_York"));

System.out.println(usa);

# 6. OffsetDateTime
# Stores date-time with UTC offset only.
import java.time.*;

public class Main {
    public static void main(String[] args) {

        OffsetDateTime dt =
                OffsetDateTime.now();

        System.out.println(dt);
    }
}
# Example: 2026-10-18T10:30:25+05:30
# Difference:
# - ZonedDateTime → Zone + DST rules
# - OffsetDateTime → Offset only

# 7. Instant
# Represents a moment on the timeline in UTC.
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        Instant instant =
                Instant.now();

        System.out.println(instant);
    }
}
# Output: 2026-10-18T05:00:00Z

# Epoch Time
long epoch =
        Instant.now().toEpochMilli();

System.out.println(epoch);

# 8. Duration
# Represents time-based amount.
# Used for:
# - Hours
# - Minutes
# - Seconds
# - Milliseconds
import java.time.*;

public class Main {
    public static void main(String[] args) {

        LocalTime start =
                LocalTime.of(10, 0);

        LocalTime end =
                LocalTime.of(12, 30);

        Duration duration =
                Duration.between(start, end);

        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes());
    }
}

# Output:
2 
150

# 9. Period
# Represents date-based difference.
# Used for: Years, Months, Days
import java.time.*;

public class Main {
    public static void main(String[] args) {

        LocalDate birth =
                LocalDate.of(2000, 1, 1);

        LocalDate today =
                LocalDate.now();

        Period age =
                Period.between(birth, today);

        System.out.println(age.getYears());
    }
}

# 10. ZoneId
# Represents a region-based timezone.
import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {

        ZoneId zone =
                ZoneId.of("Asia/Kolkata");

        System.out.println(zone);
    }
}
# List all zones:
ZoneId.getAvailableZoneIds()
      .forEach(System.out::println);

# 11. ZoneOffset
# Represents offset from UTC.
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args) {

        ZoneOffset offset =
                ZoneOffset.of("+05:30");

        System.out.println(offset);
    }
}
# Output: +05:30

# 12. DateTimeFormatter
# Used for formatting and parsing dates.
import java.time.*;
import java.time.format.*;

public class Main {
    public static void main(String[] args) {

        LocalDate date =
                LocalDate.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String formatted =
                date.format(formatter);

        System.out.println(formatted);
    }
}
# Output: 18-10-2026

# 13. Parsing and Formatting
# Formatting
LocalDate date =
        LocalDate.now();

DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

System.out.println(
        date.format(formatter));
# Output: 18/10/2026

# Parsing
String str = "18/10/2026";

DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

LocalDate date =
        LocalDate.parse(str, formatter);

System.out.println(date);
# Output: 2026-10-18

# 14. Time-Zone Conversions
# Convert IST to New York time.
import java.time.*;

public class Main {
    public static void main(String[] args) {

        ZonedDateTime india =
                ZonedDateTime.now(
                        ZoneId.of("Asia/Kolkata"));

        ZonedDateTime usa =
                india.withZoneSameInstant(
                        ZoneId.of("America/New_York"));

        System.out.println(india);
        System.out.println(usa);
    }
}

# 15. UTC and Epoch Timestamps
# UTC
Instant now = Instant.now();

System.out.println(now);
# Example: 2026-10-18T05:30:00Z

# Epoch Milliseconds
long epoch =
        Instant.now()
               .toEpochMilli();

System.out.println(epoch);

# 16. Date Arithmetic
# Add Days
LocalDate today =
        LocalDate.now();

LocalDate nextWeek =
        today.plusDays(7);

System.out.println(nextWeek);

# Subtract Months
LocalDate date =
        LocalDate.now();

System.out.println(
        date.minusMonths(1));

# Chaining
LocalDate result =
        LocalDate.now()
                .plusYears(1)
                .minusMonths(2)
                .plusDays(10);

System.out.println(result);

# 17. Handling Time Zones in Distributed Applications
# Best Practices
# ✅ Store timestamps in UTC
Instant timestamp =
        Instant.now();
# ✅ Convert to user's timezone when displaying
ZoneId userZone =
        ZoneId.of("Asia/Kolkata");

ZonedDateTime display =
        timestamp.atZone(userZone);
# ✅ Use Instant for database storage
# ✅ Use ZonedDateTime for user-facing times
# ✅ Avoid legacy Date and Calendar
# ✅ Never hardcode UTC offsets
// Bad
ZoneOffset.of("+05:30");

// Better
ZoneId.of("Asia/Kolkata");
# because timezone rules and DST changes can occur.

# Interview Questions
# Q1. Difference between LocalDate and LocalDateTime?
# LocalDate
# - Date only
# - No time
# LocalDateTime
# - Date + Time
# - No timezone

# Q2. Difference between Duration and Period?
# Duration
# - Time-based
# - Hours, Minutes, Seconds
# Period
# - Date-based
# - Years, Months, Days

# Q3. Difference between ZoneId and ZoneOffset?
# ZoneId
# - Region-based
# - DST aware
# - Example: Asia/Kolkata
# ZoneOffset
# - Fixed offset
# - Example: +05:30

# Q4. Difference between Instant and LocalDateTime?
# Instant
# - UTC timestamp
# - Global timeline
# LocalDateTime
# - Date and time only
# - No timezone information

# Q5. Why is java.time preferred over Date and Calendar?
# - Immutable, Thread-safe, Cleaner API, Better timezone support, Easier formatting and parsing

# Quick Summary
| Class             | Purpose              |
| ----------------- | -------------------- |
| LocalDate         | Date only            |
| LocalTime         | Time only            |
| LocalDateTime     | Date + Time          |
| ZonedDateTime     | Date + Time + Zone   |
| OffsetDateTime    | Date + Time + Offset |
| Instant           | UTC timestamp        |
| Duration          | Time difference      |
| Period            | Date difference      |
| ZoneId            | Timezone             |
| ZoneOffset        | UTC offset           |
| DateTimeFormatter | Format/Parse dates   |

# Remember: Modern Java applications should use the java.time API (LocalDate, LocalDateTime, Instant, ZonedDateTime) instead of legacy Date and Calendar.