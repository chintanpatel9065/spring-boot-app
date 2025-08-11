# SpringBootApp — Employee Management System

A simple Spring Boot MVC application to manage Employees and Departments with CRUD operations and search. The UI is built with Thymeleaf and Bootstrap (via WebJars), and data is persisted in PostgresSQL using Spring Data JPA.

## Features
- Department management: create, list, update, delete, search by name
- Employee management: create, list, update, delete
- Search employees by first name and by department name
- Server-side validation (Jakarta Validation)
- Thymeleaf views styled with Bootstrap

## Tech Stack
- Java 21
- Spring Boot 3.5.4
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Thymeleaf
- PostgresSQL (JDBC driver)
- WebJars Bootstrap 5.3.7
- Maven

## Prerequisites
- JDK 21+
- Maven 3.9+
- PostgresSQL running locally
  - Default connection (configure as needed in `src/main/resources/application.properties`):
    - URL: `jdbc:postgresql://localhost:5432/postgres`
    - Username: `postgres`
    - Password: `postgres`

The application uses Hibernate `hbm2ddl.auto=update` to create/update tables automatically in the configured database.

## Getting Started

1. Clone the repository
2. Adjust database settings if needed in `src/main/resources/application.properties`
3. Build and run

Using Maven (recommended during development):

```bash
mvn spring-boot:run
```

Or build a runnable jar:

```bash
mvn clean package
java -jar target/SpringBootApp-0.0.1-SNAPSHOT.jar
```

Application will start on http://localhost:8080

## Application URLs
- Departments list: `GET /departments`
- Add Department form: `GET /departments/showDepartment`
- Create/Update Department: `POST /departments/insertOrUpdateDepartment`
- Edit Department: `GET /departments/manageDepartment/{departmentId}`
- Delete Department: `GET /departments/deleteDepartment/{departmentId}`
- Search Departments by name: `GET /departments/searchDepartment?departmentName=...`

- Employees list: `GET /employees`
- Add Employee form: `GET /employees/showEmployee`
- Create/Update Employee: `POST /employees/insertOrUpdateEmployee`
- Edit Employee: `GET /employees/manageEmployee/{employeeId}`
- Delete Employee: `GET /employees/deleteEmployee/{employeeId}`
- Search Employees by first name: `GET /employees/searchEmployee?firstName=...`
- Search Employees by department name: `GET /employees/searchEmployeeByDepartment?departmentName=...`

Note: There is no explicit mapping for `/` defined; you can directly visit the endpoints above. The project also includes an `index.html` template that can be wired up to a root mapping if desired.

## Validation
Forms use Jakarta Bean Validation. On validation errors, the form page is re-rendered to display issues and preserve inputs.

## Project Structure
```
SpringBootApp/
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/org/chintanpatel/springbootapp/
│  │  │  ├─ SpringBootAppApplication.java
│  │  │  ├─ department/
│  │  │  │  ├─ Department.java, DepartmentController.java, DepartmentRepository.java,
│  │  │  │  │  DepartmentService.java, DepartmentServiceImpl.java
│  │  │  └─ employee/
│  │  │     ├─ Employee.java, EmployeeController.java, EmployeeRepository.java,
│  │  │     │  EmployeeService.java, EmployeeServiceImpl.java
│  │  └─ resources/
│  │     ├─ application.properties
│  │     └─ templates/
│  │        ├─ index.html
│  │        ├─ department/department-form.html, department/department-list.html
│  │        └─ employee/employee-form.html, employee/employee-list.html
│  └─ test/java/org/chintanpatel/springbootapp/SpringBootAppApplicationTests.java
└─ README.md
```

## Configuration
Key properties found in `src/main/resources/application.properties`:

```
spring.application.name=SpringBootApp

# PostgreSQL
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA/Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgresPlusDialect
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.hbm2ddl.auto=update
```

Tip: If you prefer to run with a different database (e.g., a dedicated DB/schema or an in-memory DB), update the properties accordingly and add the required driver dependency.

## Running Tests
```bash
mvn test
```

## Troubleshooting
- Database connection errors: ensure PostgresSQL is running and the credentials/URL match your environment.
- Port conflicts on 8080: set `server.port=xxxx` in `application.properties` to change the port.
- Static resources from WebJars: the project includes `webjars-locator`; Bootstrap assets are referenced like `/webjars/bootstrap/...` in templates.

## License
This project is provided as-is without a specified license in `pom.xml`. Add your preferred OSS license if you intend to distribute it.

## Author
Created by Chintan Patel. README generated on 2025-08-11.
