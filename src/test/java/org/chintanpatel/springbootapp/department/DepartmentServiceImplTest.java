package org.chintanpatel.springbootapp.department;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void addDepartment_delegatesToRepository() {
        Department d = new Department();
        departmentService.addDepartment(d);
        verify(departmentRepository).save(d);
    }

    @Test
    void getAllDepartmentList_returnsAll() {
        when(departmentRepository.findAll()).thenReturn(List.of(new Department()));
        assertThat(departmentService.getAllDepartmentList()).hasSize(1);
        verify(departmentRepository).findAll();
    }

    @Test
    void getDepartmentById_returnsFoundOrNull() {
        Department d = new Department();
        d.setDepartmentId(7L);
        when(departmentRepository.findById(7L)).thenReturn(Optional.of(d));
        assertThat(departmentService.getDepartmentById(7L)).isSameAs(d);

        when(departmentRepository.findById(8L)).thenReturn(Optional.empty());
        assertThat(departmentService.getDepartmentById(8L)).isNull();
    }

    @Test
    void deleteDepartmentById_delegates() {
        departmentService.deleteDepartmentById(4L);
        verify(departmentRepository).deleteById(4L);
    }

    @Test
    void searchByDepartmentName_usesRepository() {
        when(departmentRepository.findByDepartmentNameContainingIgnoreCase("fi"))
                .thenReturn(List.of());
        assertThat(departmentService.searchByDepartmentName("fi")).isEmpty();
        verify(departmentRepository).findByDepartmentNameContainingIgnoreCase("fi");
    }
}
