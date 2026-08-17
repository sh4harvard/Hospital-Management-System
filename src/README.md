# Hospital Management System

A desktop-based Hospital Management System developed in Java using Java Swing and SQLite.

The application provides a graphical interface for managing patients, doctors, wards, appointments, medical services, bills, and hospital income.

---

## Requirements

Before running the project, make sure the following software is installed:

* **Java JDK:** 17 or later
* **IDE:** IntelliJ IDEA
* **Database:** SQLite
* **Database Driver:** SQLite JDBC

No separate database server is required because the project uses a local SQLite database.

---

## Libraries

The project uses the following main libraries:

### Java Swing / AWT

Used to create the graphical user interface.

These libraries are included in the Java standard library and do not require separate installation.

### SQLite JDBC Driver

Used to connect the Java application to the SQLite database.

The SQLite JDBC `.jar` file is included in the project's `lib` directory.

---

## Project Structure

The main source code is organized into separate packages according to their responsibilities:

```text
src/
├── Hospital/
│   ├── Core/
│   │   └── Main hospital classes and business logic
│   │
│   └── GUI/
│       └── Java Swing user interface
│
└── Database/
    └── Database loading and saving
```

The project also contains the local SQLite database:

```text
hospital.db
```

---

## Setup and Installation

### 1. Open the Project

Open the project directory in **IntelliJ IDEA**.

### 2. Configure Java

Make sure the project uses **JDK 17 or later**.

In IntelliJ IDEA:

```text
File
→ Project Structure
→ Project
→ SDK
```

Select the installed JDK.

### 3. Configure SQLite JDBC

Make sure the SQLite JDBC `.jar` file located in the `lib` directory is included in the project dependencies.

If IntelliJ IDEA does not automatically recognize it:

```text
File
→ Project Structure
→ Libraries
→ +
→ Java
```

Then select the SQLite JDBC `.jar` file from the `lib` directory.

### 4. Database

The project uses the local SQLite database:

```text
hospital.db
```

No database server or additional database installation is required.

### 5. Run the Application

Open the project's main class in IntelliJ IDEA and run it.

The Hospital Management System graphical interface will open after successful execution.

---

## Database

The application uses **SQLite** for data persistence.

The database is stored locally in:

```text
hospital.db
```

The application loads existing data from the database and saves the current hospital data when the application is closed.

---

## Default Administrator Login

The current version of the project **does not contain an administrator login system**.

Therefore:

```text
Username: Not applicable
Password: Not applicable
```

No username or password is required to start and use the application.

---

## Main Features

The system currently supports:

* Patient management
* Doctor management
* Ward management
* Patient admission and discharge
* Patient transfer between wards
* Doctor assignment and transfer between wards
* Appointment management
* Medical service management
* Patient billing
* Hospital income management
* SQLite database persistence
* Graphical user interface using Java Swing

---

## Running the Project

The basic execution process is:

```text
Open Project
     ↓
Configure JDK 17+
     ↓
Configure SQLite JDBC
     ↓
Run Main Class
     ↓
Hospital Management System
```

---

## Author

**ShMonfared**

**Course:** Advanced Programming

**Project:** Hospital Management System
