# Spring Boot Employee Management System

A comprehensive web application for managing departments and employees built with Spring Boot.

## Description

This Spring Boot application provides a simple yet powerful system for managing departments and employees within an organization. It features a user-friendly web interface built with Thymeleaf and Bootstrap, allowing users to perform CRUD operations on both departments and employees.

## Features

- Department Management
  - Create, read, update, and delete departments
  - View the list of all departments
  
- Employee Management
  - Create, read, update, and delete employees
  - Assign employees to departments
  - Store employee details include:
    - Personal information (name, gender, birthdate)
    - Contact information (email, mobile)
    - Technical skills (programming languages)
    - Authentication credentials (username, password)

## Technologies Used

- **Backend**
  - Java 21
  - Spring Boot 3.4.5
  - Spring Data JPA
  - Spring MVC
  - Hibernate Validation
  
- **Frontend**
  - Thymeleaf
  - Bootstrap 5.3.5
  - HTML/CSS
  
- **Database**
  - PostgreSQL (Production)
  - H2 Database (Testing)
  
- **Build Tool**
  - Maven

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

## Installation and Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd SpringBootApp
   ```

2. **Configure the database**
   
   Update the database configuration in `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=root
   ```

3. **Build the application**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   
   Alternatively, you can run the JAR file directly:
   ```bash
   java -jar target/SpringBootApp-0.0.1-SNAPSHOT.jar
   ```

5. **Access the application**
   
   Open your web browser and navigate to:
   ```
   http://localhost:8080
   ```

## Project Structure

```
SpringBootApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/chintanpatel/springbootapp/
│   │   │       ├── department/
│   │   │       │   ├── Department.java
│   │   │       │   ├── DepartmentController.java
│   │   │       │   ├── DepartmentRepository.java
│   │   │       │   └── DepartmentService.java
│   │   │       ├── employee/
│   │   │       │   ├── Employee.java
│   │   │       │   ├── EmployeeController.java
│   │   │       │   ├── EmployeeRepository.java
│   │   │       │   └── EmployeeService.java
│   │   │       └── SpringBootAppApplication.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── department-form.html
│   │       │   ├── employee-form.html
│   │       │   └── index.html
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── org/chintanpatel/springbootapp/
│               ├── department/
│               │   ├── DepartmentControllerTest.java
│               │   ├── DepartmentRepositoryTest.java
│               │   └── DepartmentServiceTest.java
│               ├── employee/
│               │   ├── EmployeeControllerTest.java
│               │   ├── EmployeeRepositoryTest.java
│               │   └── EmployeeServiceTest.java
│               └── SpringBootAppApplicationTests.java
├── pom.xml
└── README.md
```

## Usage

1. **Department Management**
   - Navigate to the Department page from the home screen
   - Add new departments using the form
   - Edit or delete existing departments using the action buttons

2. **Employee Management**
   - Navigate to the Employee page from the home screen
   - Add new employees using the form, selecting their department
   - Edit or delete existing employees using the action buttons

## Testing

The application includes comprehensive unit tests for controllers, services, and repositories. To run the tests:

```bash
mvn test
```

## License

This project is open source and available under the [MIT License](LICENSE).

## Author

Chintan Patel