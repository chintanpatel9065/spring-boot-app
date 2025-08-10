package org.chintanpatel.springbootapp.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee>findByFirstNameContainingIgnoreCase(String firstName);

    @Query("select e from Employee e where lower(e.department.departmentName) like lower(concat('%',:departmentName,'%'))")
    List<Employee>findByDepartmentNameContainingIgnoreCase(String departmentName);
}