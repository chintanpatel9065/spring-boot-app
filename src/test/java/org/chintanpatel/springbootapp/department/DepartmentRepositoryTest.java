package org.chintanpatel.springbootapp.department;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSaveDepartment() {
        // Create a department
        Department department = new Department();
        department.setDepartmentName("IT");

        // Save the department
        Department savedDepartment = departmentRepository.save(department);

        // Verify the department was saved
        assertNotNull(savedDepartment.getDepartmentId());
        assertEquals("IT", savedDepartment.getDepartmentName());
    }

    @Test
    public void testFindAllDepartments() {
        // Create and save departments
        Department department1 = new Department();
        department1.setDepartmentName("IT");
        departmentRepository.save(department1);

        Department department2 = new Department();
        department2.setDepartmentName("HR");
        departmentRepository.save(department2);

        // Retrieve all departments
        List<Department> departments = departmentRepository.findAll();

        // Verify the departments were retrieved
        assertEquals(2, departments.size());
        assertTrue(departments.stream().anyMatch(d -> d.getDepartmentName().equals("IT")));
        assertTrue(departments.stream().anyMatch(d -> d.getDepartmentName().equals("HR")));
    }

    @Test
    public void testFindDepartmentById() {
        // Create and save a department
        Department department = new Department();
        department.setDepartmentName("Finance");
        Department savedDepartment = departmentRepository.save(department);

        // Retrieve the department by ID
        Optional<Department> foundDepartment = departmentRepository.findById(savedDepartment.getDepartmentId());

        // Verify the department was retrieved
        assertTrue(foundDepartment.isPresent());
        assertEquals("Finance", foundDepartment.get().getDepartmentName());
    }

    @Test
    public void testDeleteDepartment() {
        // Create and save a department
        Department department = new Department();
        department.setDepartmentName("Marketing");
        Department savedDepartment = departmentRepository.save(department);

        // Delete the department
        departmentRepository.deleteById(savedDepartment.getDepartmentId());

        // Verify the department was deleted
        Optional<Department> foundDepartment = departmentRepository.findById(savedDepartment.getDepartmentId());
        assertFalse(foundDepartment.isPresent());
    }

    @Test
    public void testUpdateDepartment() {
        // Create and save a department
        Department department = new Department();
        department.setDepartmentName("Sales");
        Department savedDepartment = departmentRepository.save(department);

        // Update the department
        savedDepartment.setDepartmentName("Sales and Marketing");
        Department updatedDepartment = departmentRepository.save(savedDepartment);

        // Verify the department was updated
        assertEquals("Sales and Marketing", updatedDepartment.getDepartmentName());
        assertEquals(savedDepartment.getDepartmentId(), updatedDepartment.getDepartmentId());
    }
}