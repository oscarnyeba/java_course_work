# Student Registration App

JavaFX desktop app for registering students with auto-generated IDs and data persistence.

## What It Does

- Register students with personal details
- Validate all inputs (email, password, age, etc.)
- Auto-generate unique student IDs
- Save data to CSV file
- Load previous registrations on startup

## Requirements

- Java 11+
- JavaFX 11+
- Maven

## How to Run

```bash
# Using Maven
mvn javafx:run

# Or build and run JAR
mvn clean package
java -jar target/student-registration-app-1.0-SNAPSHOT.jar
```

## Form Fields

- **Name**: First and last name
- **Email**: Must match confirmation
- **Password**: 8-20 chars, needs letter + digit
- **Date of Birth**: Age must be 16-60
- **Gender**: Male or Female
- **Department**: Civil, CSE, Electrical, E&C, or Mechanical

## Student ID Format

`YYYY-NNNNN` (e.g., 2026-00001)

Auto-increments for each new student.

## Data Storage

Saves to `students.csv`:
```
ID,LastName,FirstName,Gender,Department,DOB,Email
2026-00001,Doe,John,M,CSE,2005-03-15,john@email.com
```

## Authors

- **Ogwang Gift Gideon** - VU-BCS-2503-0706-EVE
- **Suubi Deborah** - VU-DIT-2503-1213-EVE
- **Nyeba Oscar Mathew** - VU-BCS-2503-1204-EVE
- **Nalwoga Madrine** - VU-BIT-2503-2460-EVE
- **Namagambe Precious** - VU-BSC-2503-0355-EVE
- **KINTU BRIAN** - VU-DIT-2503-0306-EVE

Victoria University - Object Oriented Programming