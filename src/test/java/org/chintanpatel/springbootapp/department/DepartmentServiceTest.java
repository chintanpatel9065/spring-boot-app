package org.chintanpatel.springbootapp.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department1;
    private Department department2;

    @BeforeEach
    public void setup() {
        // Create test departments
        department1 = new Department();
        department1.setDepartmentId(1L);
        department1.setDepartmentName("IT");

        department2 = new Department();
        department2.setDepartmentId(2L);
        department2.setDepartmentName("HR");
    }

    @Test
    public void testAddDepartment() {
        // Setup
        when(departmentRepository.save(any(Department.class))).thenReturn(department1);

        // Execute
        departmentService.addDepartment(department1);

        // Verify
        verify(departmentRepository, times(1)).save(department1);
    }

    @Test
    public void testGetAllDepartmentList() {
        // Setup
        List<Department> departments = Arrays.asList(department1, department2);
        when(departmentRepository.findAll()).thenReturn(departments);

        // Execute
        List<Department> result = departmentService.getAllDepartmentList();

        // Verify
        assertEquals(2, result.size());
        assertEquals("IT", result.get(0).getDepartmentName());
        assertEquals("HR", result.get(1).getDepartmentName());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetDepartmentById_Found() {
        // Setup
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department1));

        // Execute
        Department result = departmentService.getDepartmentById(1L);

        // Verify
        assertEquals("IT", result.getDepartmentName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetDepartmentById_NotFound() {
        // Setup
        when(departmentRepository.findById(3L)).thenReturn(Optional.empty());

        // Execute
        Department result = departmentService.getDepartmentById(3L);

        // Verify
        assertNull(result);
        verify(departmentRepository, times(1)).findById(3L);
    }

    @Test
    public void testDeleteDepartmentById() {
        // Setup
        doNothing().when(departmentRepository).deleteById(1L);

        // Execute
        departmentService.deleteDepartmentById(1L);

        // Verify
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}