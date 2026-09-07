# University-Management-System
A GUI based University Management System created using Java, MySQL, Spring.
**University Management System** is a desktop Java Swing application for managing university students, professors, and grades, backed by MySQL.

### Overview
A role-based desktop application built with Java Swing and MySQL. It provides separate interfaces for:

- **Admin** – full CRUD for students and professors
- **Professor** – assign/edit grades and view student grade snapshots
- **Student** – view personal details and own grades

The database schema is created automatically on first launch.

### Features

**Administration**
- Add / Edit / Delete Student
- Add / Edit / Delete Professor
- Automatic user account creation when adding students/professors
- Admin-only signup for additional Admin accounts

**Professor**
- Assign Grade
- Edit Grade
- View Student (semester-wise grade snapshot)

**Student**
- View Detail (personal information)
- View Grade

**Other**
- Role-based login (Admin / Professor / Student)
- Default admin credentials created on first run
- Auto-creation of required tables if they do not exist

### Tech Stack
- **Language**: Java 8
- **GUI**: Java Swing
- **Database**: MySQL 8 (via `mysql-connector-java-8.0.11`)
- **IDE / Build**: NetBeans (Ant-based project)
- **Main class**: `guiapplicationpack.LoginMain`

### Database
- **Host**: `localhost:3306`
- **Database name**: `unisys`
- **Username**: `root`
- **Password**: `4321` (hard-coded in the application)

**Tables (auto-created on first run):**

| Table              | Purpose                                      |
|--------------------|----------------------------------------------|
| `USER`             | Login credentials + role                     |
| `STUDENT_MASTER`   | Student personal & academic details          |
| `PROFESSOR_MASTER` | Professor personal details                   |
| `PROFESSOR_DEGREE` | Professor degrees (multi-value)              |
| `STUDENT_GRADE`    | Semester-wise grades                         |

Default admin account (created automatically):
- **User ID**: `admin`
- **Password**: `admin`
- **Role**: Admin

### Prerequisites
1. Java JDK 8 or higher
2. MySQL Server running on `localhost:3306`
3. MySQL user `root` with password `4321` (or change the connection strings in the source)
4. Database `unisys` must exist (the application does **not** create the database itself, only the tables)

```sql
CREATE DATABASE unisys;
```

### How to Run

**Option 1 – From NetBeans**
1. Open the project in NetBeans.
2. Ensure MySQL is running and the `unisys` database exists.
3. Run the project (main class is already set to `guiapplicationpack.LoginMain`).

**Option 2 – From the distributed JAR**
```bash
cd dist
java -jar UniversityManagementSystem.jar
```

**Option 3 – Build from source**
```bash
# Using the included Ant build
ant clean jar
java -jar dist/UniversityManagementSystem.jar
```

### Project Structure
```
UniversityManagementSystem/
├── src/guiapplicationpack/
│   ├── LoginMain.java          # Entry point + DB initialization
│   ├── MainFrame.java          # Role-based main menu
│   ├── SignupFrame.java
│   ├── StudentAddFrame.java / StudentEditFrame.java / StudentDeleteFrame.java
│   ├── ProfessorAddFrame.java / ProfessorEditFrame.java / ProfessorDeleteFrame.java
│   ├── GradeAssign.java
│   ├── StudentSnapshot.java
│   ├── ViewDetailByStudent.java
│   └── ViewGradeByStudent.java
├── dist/
│   ├── UniversityManagementSystem.jar
│   └── lib/mysql-connector-java-8.0.11.jar
├── mysql-connector-java-8.0.11.jar
├── build.xml
└── nbproject/
```

### Important Notes
- Connection details (`jdbc:mysql://localhost:3306/unisys?...`, user `root`, password `4321`) are hard-coded throughout the source. Change them in every class that uses `DriverManager.getConnection` if your MySQL setup differs.
- The application expects the MySQL Connector/J JAR on the classpath (already configured in the NetBeans project and distributed with the JAR).
- Passwords are stored in plain text.
- The UI uses fixed absolute layouts and custom colors.

### Default Login
| Role      | User ID | Password |
|-----------|---------|----------|
| Admin     | admin   | admin    |

After logging in as Admin you can create additional Admin accounts via **Sign Up**, and create Professor/Student accounts (which also create corresponding `USER` entries) through the Administration menu.

---

This README is ready to be placed at the root of the repository as `README.md`.
