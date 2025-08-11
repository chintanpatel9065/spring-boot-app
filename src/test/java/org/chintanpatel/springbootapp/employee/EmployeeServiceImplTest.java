package org.chintanpatel.springbootapp.employee;

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
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void addEmployee_delegatesToRepository() {
        Employee e = new Employee();
        employeeService.addEmployee(e);
        verify(employeeRepository).save(e);
    }

    @Test
    void getAllEmployeeList_returnsAll() {
        when(employeeRepository.findAll()).thenReturn(List.of(new Employee()));
        assertThat(employeeService.getAllEmployeeList()).hasSize(1);
        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeById_returnsFoundOrNull() {
        Employee e = new Employee();
        e.setEmployeeId(42L);
        when(employeeRepository.findById(42L)).thenReturn(Optional.of(e));
        assertThat(employeeService.getEmployeeById(42L)).isSameAs(e);

        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThat(employeeService.getEmployeeById(99L)).isNull();
    }

    @Test
    void deleteEmployeeById_delegates() {
        employeeService.deleteEmployeeById(10L);
        verify(employeeRepository).deleteById(10L);
    }

    @Test
    void searchByFirstName_usesRepository() {
        when(employeeRepository.findByFirstNameContainingIgnoreCase("jo"))
                .thenReturn(List.of());
        assertThat(employeeService.searchByFirstName("jo")).isEmpty();
        verify(employeeRepository).findByFirstNameContainingIgnoreCase("jo");
    }

    @Test
    void searchByDepartmentName_usesRepository() {
        when(employeeRepository.findByDepartmentNameContainingIgnoreCase("it"))
                .thenReturn(List.of());
        assertThat(employeeService.searchByDepartmentName("it")).isEmpty();
        verify(employeeRepository).findByDepartmentNameContainingIgnoreCase("it");
    }
}
