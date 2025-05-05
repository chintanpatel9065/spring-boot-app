package org.chintanpatel.springbootapp;

import org.chintanpatel.springbootapp.department.Department;
import org.chintanpatel.springbootapp.department.DepartmentRepository;
import org.chintanpatel.springbootapp.employee.Employee;
import org.chintanpatel.springbootapp.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SpringBootAppApplicationTests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void contextLoads() {
        // Verify that the application context loads successfully
    }

    @Test
    void testCreateAndRetrieveDepartment() {
        // Create a department
        Department department = new Department();
        department.setDepartmentName("Engineering");

        // Save the department
        Department savedDepartment = departmentRepository.save(department);

        // Retrieve the department
        Optional<Department> retrievedDepartment = departmentRepository.findById(savedDepartment.getDepartmentId());

        // Verify
        assertTrue(retrievedDepartment.isPresent());
        assertEquals("Engineering", retrievedDepartment.get().getDepartmentName());
    }

    @Test
    void testCreateAndRetrieveEmployee() {
        // Create a department
        Department department = new Department();
        department.setDepartmentName("Marketing");
        Department savedDepartment = departmentRepository.save(department);

        // Create an employee
        Employee employee = new Employee();
        employee.setFirstName("Alice");
        employee.setMiddleName("M");
        employee.setLastName("Smith");
        employee.setGender("Female");
        employee.setProgrammingLanguage(new String[]{"Java", "C++"});
        employee.setEmail("alice.smith@example.com");
        employee.setMobile("1234567890");
        employee.setBirthDate(LocalDate.of(1995, 5, 15));
        employee.setUserName("alicesmith");
        employee.setPassword("password");
        employee.setDepartment(savedDepartment);

        // Save the employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Retrieve the employee
        Optional<Employee> retrievedEmployee = employeeRepository.findById(savedEmployee.getEmployeeId());

        // Verify
        assertTrue(retrievedEmployee.isPresent());
        assertEquals("Alice", retrievedEmployee.get().getFirstName());
        assertEquals("Smith", retrievedEmployee.get().getLastName());
        assertEquals("Marketing", retrievedEmployee.get().getDepartment().getDepartmentName());
    }

    @Test
    void testFindAllDepartments() {
        // Create departments
        Department department1 = new Department();
        department1.setDepartmentName("Sales");
        departmentRepository.save(department1);

        Department department2 = new Department();
        department2.setDepartmentName("Finance");
        departmentRepository.save(department2);

        // Retrieve all departments
        List<Department> departments = departmentRepository.findAll();

        // Verify
        assertTrue(departments.size() >= 2);
        assertTrue(departments.stream().anyMatch(d -> d.getDepartmentName().equals("Sales")));
        assertTrue(departments.stream().anyMatch(d -> d.getDepartmentName().equals("Finance")));
    }

    @Test
    void testFindAllEmployees() {
        // Create a department
        Department department = new Department();
        department.setDepartmentName("Research");
        Department savedDepartment = departmentRepository.save(department);

        // Create employees
        Employee employee1 = new Employee();
        employee1.setFirstName("Bob");
        employee1.setMiddleName("R");
        employee1.setLastName("Johnson");
        employee1.setGender("Male");
        employee1.setProgrammingLanguage(new String[]{"Python", "R"});
        employee1.setEmail("bob.johnson@example.com");
        employee1.setMobile("9876543210");
        employee1.setBirthDate(LocalDate.of(1988, 8, 20));
        employee1.setUserName("bobjohnson");
        employee1.setPassword("password");
        employee1.setDepartment(savedDepartment);
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setFirstName("Carol");
        employee2.setMiddleName("T");
        employee2.setLastName("Williams");
        employee2.setGender("Female");
        employee2.setProgrammingLanguage(new String[]{"JavaScript", "TypeScript"});
        employee2.setEmail("carol.williams@example.com");
        employee2.setMobile("5555555555");
        employee2.setBirthDate(LocalDate.of(1992, 3, 10));
        employee2.setUserName("carolwilliams");
        employee2.setPassword("password");
        employee2.setDepartment(savedDepartment);
        employeeRepository.save(employee2);

        // Retrieve all employees
        List<Employee> employees = employeeRepository.findAll();

        // Verify
        assertTrue(employees.size() >= 2);
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals("Bob")));
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals("Carol")));
    }
}
