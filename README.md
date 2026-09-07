# University-Management-System
A GUI based Menu driven University Management System created using Java, MySQL, Spring.
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
<img width="638" height="615" alt="Screenshot 2026-09-07 202519" src="https://github.com/user-attachments/assets/8f29a4f2-4bff-4d0c-ab63-7352ce628920" />
<img width="642" height="677" alt="Screenshot 2026-09-07 202511" src="https://github.com/user-attachments/assets/613b8e34-e862-4177-bc76-fa05114ea4ba" />
<img width="1106" height="487" alt="Screenshot 2026-09-07 202350" src="https://github.com/user-attachments/assets/d96a4dfd-23af-4568-a32b-4169d74b18f3" />
<img width="668" height="328" alt="Screenshot 2026-09-07 202334" src="https://github.com/user-attachments/assets/cf618aa1-6ccd-446f-8f21-41fa3c7a5fa5" />
<img width="648" height="680" alt="Screenshot 2026-09-07 202309" src="https://github.com/user-attachments/assets/510a2536-01ec-4c36-a5b7-a8c9e34ec65c" />
<img width="647" height="626" alt="Screenshot 2026-09-07 202258" src="https://github.com/user-attachments/assets/cfba22c6-65d8-4ab5-82b2-41fc18a971b9" />
<img width="646" height="680" alt="Screenshot 2026-09-07 202249" src="https://github.com/user-attachments/assets/856afba4-3e8c-4d8e-b8db-89ce77d06e5b" />
<img width="655" height="682" alt="Screenshot 2026-09-07 202240" src="https://github.com/user-attachments/assets/ff73dfa0-c579-4e6f-b0cd-afa4cc1ca6b5" />
<img width="652" height="686" alt="Screenshot 2026-09-07 202225" src="https://github.com/user-attachments/assets/6f2a9941-393b-4ada-944e-01774431b4c5" />
<img width="1000" height="743" alt="Screenshot 2026-09-07 202209" src="https://github.com/user-attachments/assets/5cab6feb-367d-49a8-a019-deba6eecdfcc" />
<img width="1000" height="745" alt="Screenshot 2026-09-07 202201" src="https://github.com/user-attachments/assets/30b0b35a-11a5-4028-95cc-d3103f6aafe3" />
<img width="1005" height="748" alt="Screenshot 2026-09-07 202151" src="https://github.com/user-attachments/assets/93d473a4-0e88-4c5b-b736-5d25963f187d" />
<img width="637" height="341" alt="Screenshot 2026-09-07 202137" src="https://github.com/user-attachments/assets/906b9980-d0f4-487b-93c0-16519dabe393" />

