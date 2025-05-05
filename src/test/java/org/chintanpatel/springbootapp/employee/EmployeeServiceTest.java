package org.chintanpatel.springbootapp.employee;

import org.chintanpatel.springbootapp.department.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private Department department;

    @BeforeEach
    public void setup() {
        // Create a test department
        department = new Department();
        department.setDepartmentId(1L);
        department.setDepartmentName("IT");

        // Create test employees
        employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setFirstName("John");
        employee1.setMiddleName("A");
        employee1.setLastName("Doe");
        employee1.setGender("Male");
        employee1.setProgrammingLanguage(new String[]{"Java", "Python"});
        employee1.setEmail("john.doe@example.com");
        employee1.setMobile("1234567890");
        employee1.setBirthDate(LocalDate.of(1990, 1, 1));
        employee1.setUserName("johndoe");
        employee1.setPassword("password");
        employee1.setDepartment(department);

        employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setFirstName("Jane");
        employee2.setMiddleName("B");
        employee2.setLastName("Doe");
        employee2.setGender("Female");
        employee2.setProgrammingLanguage(new String[]{"JavaScript", "Ruby"});
        employee2.setEmail("jane.doe@example.com");
        employee2.setMobile("0987654321");
        employee2.setBirthDate(LocalDate.of(1992, 2, 2));
        employee2.setUserName("janedoe");
        employee2.setPassword("password");
        employee2.setDepartment(department);
    }

    @Test
    public void testAddEmployee() {
        // Setup
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        // Execute
        employeeService.addEmployee(employee1);

        // Verify
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    public void testGetAllEmployeeList() {
        // Setup
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        // Execute
        List<Employee> result = employeeService.getAllEmployeeList();

        // Verify
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testGetEmployeeById_Found() {
        // Setup
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));

        // Execute
        Employee result = employeeService.getEmployeeById(1L);

        // Verify
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        // Setup
        when(employeeRepository.findById(3L)).thenReturn(Optional.empty());

        // Execute
        Employee result = employeeService.getEmployeeById(3L);

        // Verify
        assertNull(result);
        verify(employeeRepository, times(1)).findById(3L);
    }

    @Test
    public void testDeleteEmployeeById() {
        // Setup
        doNothing().when(employeeRepository).deleteById(1L);

        // Execute
        employeeService.deleteEmployeeById(1L);

        // Verify
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}