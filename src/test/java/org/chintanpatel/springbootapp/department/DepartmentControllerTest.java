package org.chintanpatel.springbootapp.department;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    @DisplayName("GET /departments should render list view with departments")
    void listDepartments() throws Exception {
        Mockito.when(departmentService.getAllDepartmentList()).thenReturn(List.of());

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-list"))
                .andExpect(model().attributeExists("departmentList"));
    }

    @Test
    @DisplayName("GET /departments/showDepartment should render form with empty department")
    void showDepartmentForm() throws Exception {
        mockMvc.perform(get("/departments/showDepartment"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"))
                .andExpect(model().attributeExists("department"));
    }

    @Test
    @DisplayName("POST /departments/insertOrUpdateDepartment with validation errors should return form view")
    void insertOrUpdateDepartment_validationError() throws Exception {
        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                        .param("departmentName", "")) // NotEmpty
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"))
                .andExpect(model().attributeHasFieldErrors("department", "departmentName"));

        Mockito.verify(departmentService, Mockito.never()).addDepartment(Mockito.any(Department.class));
    }

    @Test
    @DisplayName("GET /departments/manageDepartment/{id} should populate form with existing department")
    void manageDepartment() throws Exception {
        Department dep = new Department();
        dep.setDepartmentId(9L);
        dep.setDepartmentName("Finance");
        Mockito.when(departmentService.getDepartmentById(9L)).thenReturn(dep);

        mockMvc.perform(get("/departments/manageDepartment/9"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"))
                .andExpect(model().attribute("department", hasProperty("departmentId", is(9L))));
    }

    @Test
    @DisplayName("GET /departments/deleteDepartment/{id} should redirect and call delete")
    void deleteDepartment() throws Exception {
        mockMvc.perform(get("/departments/deleteDepartment/3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments"));

        Mockito.verify(departmentService).deleteDepartmentById(3L);
    }

    @Test
    @DisplayName("GET /departments/searchDepartment should set list and searchType=departmentName")
    void searchDepartment() throws Exception {
        Mockito.when(departmentService.searchByDepartmentName("Fin")).thenReturn(List.of());

        mockMvc.perform(get("/departments/searchDepartment").param("departmentName", "Fin"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-list"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attribute("departmentName", "Fin"))
                .andExpect(model().attribute("searchType", "departmentName"));
    }
}
