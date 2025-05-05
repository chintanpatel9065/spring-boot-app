package org.chintanpatel.springbootapp.employee;

import org.chintanpatel.springbootapp.department.Department;
import org.chintanpatel.springbootapp.department.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @BeforeEach
    public void setup() {
        // Create and save a department for testing
        department = new Department();
        department.setDepartmentName("IT");
        department = departmentRepository.save(department);
    }

    @Test
    public void testSaveEmployee() {
        // Create an employee
        Employee employee = createTestEmployee();

        // Save the employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Verify the employee was saved
        assertNotNull(savedEmployee.getEmployeeId());
        assertEquals("John", savedEmployee.getFirstName());
        assertEquals("Doe", savedEmployee.getLastName());
        assertEquals("john.doe@example.com", savedEmployee.getEmail());
        assertEquals(department.getDepartmentId(), savedEmployee.getDepartment().getDepartmentId());
    }

    @Test
    public void testFindAllEmployees() {
        // Create and save employees
        Employee employee1 = createTestEmployee();
        employeeRepository.save(employee1);

        Employee employee2 = createTestEmployee();
        employee2.setFirstName("Jane");
        employee2.setEmail("jane.doe@example.com");
        employeeRepository.save(employee2);

        // Retrieve all employees
        List<Employee> employees = employeeRepository.findAll();

        // Verify the employees were retrieved
        assertEquals(2, employees.size());
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals("John")));
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals("Jane")));
    }

    @Test
    public void testFindEmployeeById() {
        // Create and save an employee
        Employee employee = createTestEmployee();
        Employee savedEmployee = employeeRepository.save(employee);

        // Retrieve the employee by ID
        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getEmployeeId());

        // Verify the employee was retrieved
        assertTrue(foundEmployee.isPresent());
        assertEquals("John", foundEmployee.get().getFirstName());
        assertEquals("Doe", foundEmployee.get().getLastName());
    }

    @Test
    public void testDeleteEmployee() {
        // Create and save an employee
        Employee employee = createTestEmployee();
        Employee savedEmployee = employeeRepository.save(employee);

        // Delete the employee
        employeeRepository.deleteById(savedEmployee.getEmployeeId());

        // Verify the employee was deleted
        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getEmployeeId());
        assertFalse(foundEmployee.isPresent());
    }

    @Test
    public void testUpdateEmployee() {
        // Create and save an employee
        Employee employee = createTestEmployee();
        Employee savedEmployee = employeeRepository.save(employee);

        // Update the employee
        savedEmployee.setFirstName("Johnny");
        savedEmployee.setEmail("johnny.doe@example.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // Verify the employee was updated
        assertEquals("Johnny", updatedEmployee.getFirstName());
        assertEquals("johnny.doe@example.com", updatedEmployee.getEmail());
        assertEquals(savedEmployee.getEmployeeId(), updatedEmployee.getEmployeeId());
    }

    private Employee createTestEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setMiddleName("A");
        employee.setLastName("Doe");
        employee.setGender("Male");
        employee.setProgrammingLanguage(new String[]{"Java", "Python"});
        employee.setEmail("john.doe@example.com");
        employee.setMobile("1234567890");
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        employee.setUserName("johndoe");
        employee.setPassword("password");
        employee.setDepartment(department);
        return employee;
    }
}