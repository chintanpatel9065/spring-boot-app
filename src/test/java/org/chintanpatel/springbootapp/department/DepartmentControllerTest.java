package org.chintanpatel.springbootapp.department;

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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
@Import(DepartmentControllerTest.TestConfig.class)
public class DepartmentControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public DepartmentService departmentService() {
            return Mockito.mock(DepartmentService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    private Department department1;
    private Department department2;
    private List<Department> departments;

    @BeforeEach
    public void setup() {
        // Create test departments
        department1 = new Department();
        department1.setDepartmentId(1L);
        department1.setDepartmentName("IT");

        department2 = new Department();
        department2.setDepartmentId(2L);
        department2.setDepartmentName("HR");

        departments = Arrays.asList(department1, department2);
    }

    @Test
    public void testListDepartment() throws Exception {
        // Setup
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // Execute and Verify
        mockMvc.perform(get("/departments/listDepartment"))
                .andExpect(status().isOk())
                .andExpect(view().name("department-form"))
                .andExpect(model().attributeExists("department"))
                .andExpect(model().attributeExists("departmentList"));

        verify(departmentService, times(1)).getAllDepartmentList();
    }

    @Test
    public void testInsertOrUpdateDepartment_Success() throws Exception {
        // Setup
        doNothing().when(departmentService).addDepartment(any(Department.class));

        // Execute and Verify
        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                        .param("departmentName", "Finance")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments/listDepartment"));

        verify(departmentService, times(1)).addDepartment(any(Department.class));
    }

    @Test
    public void testInsertOrUpdateDepartment_ValidationError() throws Exception {
        // Setup
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // Execute and Verify
        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                        .param("departmentName", "") // Empty name to trigger validation error
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("department-form"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().hasErrors());

        verify(departmentService, times(1)).getAllDepartmentList();
        verify(departmentService, never()).addDepartment(any(Department.class));
    }

    @Test
    public void testManageDepartment() throws Exception {
        // Setup
        when(departmentService.getDepartmentById(1L)).thenReturn(department1);
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // Execute and Verify
        mockMvc.perform(get("/departments/manageDepartment/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("department-form"))
                .andExpect(model().attributeExists("department"))
                .andExpect(model().attributeExists("departmentList"));

        verify(departmentService, times(1)).getDepartmentById(1L);
        verify(departmentService, times(1)).getAllDepartmentList();
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        // Setup
        doNothing().when(departmentService).deleteDepartmentById(anyLong());

        // Execute and Verify
        mockMvc.perform(get("/departments/deleteDepartment/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments/listDepartment"));

        verify(departmentService, times(1)).deleteDepartmentById(1L);
    }
}
