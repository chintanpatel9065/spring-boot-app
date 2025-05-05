package org.chintanpatel.springbootapp.employee;

import org.chintanpatel.springbootapp.department.Department;
import org.chintanpatel.springbootapp.department.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@Import(EmployeeControllerTest.TestConfig.class)
public class EmployeeControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public EmployeeService employeeService() {
            return Mockito.mock(EmployeeService.class);
        }

        @Bean
        public DepartmentService departmentService() {
            return Mockito.mock(DepartmentService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    private Employee employee1;
    private Employee employee2;
    private Department department;
    private List<Employee> employees;
    private List<Department> departments;

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

        employees = Arrays.asList(employee1, employee2);
        departments = Arrays.asList(department);
    }

    @Test
    public void testListEmployee() throws Exception {
        // Setup
        when(employeeService.getAllEmployeeList()).thenReturn(employees);
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // Execute and Verify
        mockMvc.perform(get("/employees/listEmployee"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attributeExists("employeeList"));

        verify(employeeService, times(1)).getAllEmployeeList();
        verify(departmentService, times(1)).getAllDepartmentList();
    }

    @Test
    public void testInsertOrUpdateEmployee_Success() throws Exception {
        // Setup
        doNothing().when(employeeService).addEmployee(any(Employee.class));
        when(departmentService.getDepartmentById(1L)).thenReturn(department);

        // Execute and Verify
        mockMvc.perform(post("/employees/insertOrUpdateEmployee")
                        .param("firstName", "John")
                        .param("middleName", "A")
                        .param("lastName", "Doe")
                        .param("gender", "Male")
                        .param("programmingLanguage", "Java", "Python")
                        .param("email", "john.doe@example.com")
                        .param("mobile", "1234567890")
                        .param("birthDate", "1990-01-01")
                        .param("userName", "johndoe")
                        .param("password", "password")
                        .param("department.departmentId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/listEmployee"));

        verify(employeeService, times(1)).addEmployee(any(Employee.class));
    }

    @Test
    public void testInsertOrUpdateEmployee_ValidationError() throws Exception {
        // Setup
        when(employeeService.getAllEmployeeList()).thenReturn(employees);
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // Execute and Verify
        mockMvc.perform(post("/employees/insertOrUpdateEmployee")
                        .param("firstName", "") // Empty name to trigger validation error
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attributeExists("employeeList"))
                .andExpect(model().hasErrors());

        verify(employeeService, times(1)).getAllEmployeeList();
        verify(departmentService, times(1)).getAllDepartmentList();
        verify(employeeService, never()).addEmployee(any(Employee.class));
    }

    @Test
    public void testManageEmployee() throws Exception {
        // Setup
        when(employeeService.getEmployeeById(1L)).thenReturn(employee1);
        when(employeeService.getAllEmployeeList()).thenReturn(employees);
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // Execute and Verify
        mockMvc.perform(get("/employees/manageEmployee/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attributeExists("employeeList"));

        verify(employeeService, times(1)).getEmployeeById(1L);
        verify(employeeService, times(1)).getAllEmployeeList();
        verify(departmentService, times(1)).getAllDepartmentList();
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        // Setup
        doNothing().when(employeeService).deleteEmployeeById(anyLong());

        // Execute and Verify
        mockMvc.perform(get("/employees/deleteEmployee/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/listEmployee"));

        verify(employeeService, times(1)).deleteEmployeeById(1L);
    }
}