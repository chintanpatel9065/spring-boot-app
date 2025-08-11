package org.chintanpatel.springbootapp.employee;

import org.chintanpatel.springbootapp.department.Department;
import org.chintanpatel.springbootapp.department.DepartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentService departmentService;

    @Test
    @DisplayName("GET /employees should render list view with employees")
    void listEmployees() throws Exception {
        Mockito.when(employeeService.getAllEmployeeList())
                .thenReturn(List.of());

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-list"))
                .andExpect(model().attributeExists("employeeList"));
    }

    @Test
    @DisplayName("GET /employees/showEmployee should render form with empty employee and departments")
    void showEmployeeForm() throws Exception {
        Mockito.when(departmentService.getAllDepartmentList())
                .thenReturn(List.of(new Department()));

        mockMvc.perform(get("/employees/showEmployee"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attributeExists("departmentList"));
    }

    @Test
    @DisplayName("POST /employees/insertOrUpdateEmployee with validation errors should return form view")
    void insertOrUpdateEmployee_validationError() throws Exception {
        Mockito.when(departmentService.getAllDepartmentList())
                .thenReturn(List.of(new Department()));

        // send with missing required fields to trigger @Valid errors
        mockMvc.perform(post("/employees/insertOrUpdateEmployee")
                        .param("firstName", "") // NotEmpty
                        .param("middleName", "")
                        .param("lastName", "")
                        .param("address", "")
                        .param("email", "")
                        .param("phoneNumber", "")
                        // omit dateOfBirth
                        .param("userName", "")
                        .param("password", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeHasFieldErrors("employee",
                        "firstName", "middleName", "lastName", "address", "email", "phoneNumber", "dateOfBirth", "userName", "password", "department"))
                .andExpect(model().attributeExists("departmentList"));

        Mockito.verify(employeeService, Mockito.never()).addEmployee(any(Employee.class));
    }

    @Test
    @DisplayName("GET /employees/manageEmployee/{id} should populate form with existing employee")
    void manageEmployee() throws Exception {
        Department dep = new Department();
        dep.setDepartmentId(1L);
        dep.setDepartmentName("IT");
        Employee emp = new Employee(1L, "John", "M", "Doe", "Addr", "john@ex.com", "123",
                LocalDate.of(1990,1,1), "jdoe", "pwd", dep);
        Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(emp);
        Mockito.when(departmentService.getAllDepartmentList()).thenReturn(List.of(dep));

        mockMvc.perform(get("/employees/manageEmployee/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attribute("employee", hasProperty("employeeId", is(1L))))
                .andExpect(model().attributeExists("departmentList"));
    }

    @Test
    @DisplayName("GET /employees/deleteEmployee/{id} should redirect to list and call delete")
    void deleteEmployee() throws Exception {
        mockMvc.perform(get("/employees/deleteEmployee/5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));

        Mockito.verify(employeeService).deleteEmployeeById(5L);
    }

    @Test
    @DisplayName("GET /employees/searchEmployee should set list and searchType=firstName")
    void searchByFirstName() throws Exception {
        Mockito.when(employeeService.searchByFirstName("jo")).thenReturn(List.of());

        mockMvc.perform(get("/employees/searchEmployee").param("firstName", "jo"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-list"))
                .andExpect(model().attributeExists("employeeList"))
                .andExpect(model().attribute("firstName", "jo"))
                .andExpect(model().attribute("searchType", "firstName"));
    }

    @Test
    @DisplayName("GET /employees/searchEmployeeByDepartment should set list and searchType=departmentName")
    void searchByDepartmentName() throws Exception {
        Mockito.when(employeeService.searchByDepartmentName("IT")).thenReturn(List.of());

        mockMvc.perform(get("/employees/searchEmployeeByDepartment").param("departmentName", "IT"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-list"))
                .andExpect(model().attributeExists("employeeList"))
                .andExpect(model().attribute("departmentName", "IT"))
                .andExpect(model().attribute("searchType", "departmentName"));
    }
}
