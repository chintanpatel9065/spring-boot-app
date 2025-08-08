package org.chintanpatel.springbootapp.department;

import java.util.List;

public interface DepartmentService {

    void addDepartment(Department department);

    List<Department>getAllDepartmentList();

    Department getDepartmentById(Long departmentId);

    void deleteDepartmentById(Long departmentId);

    List<Department>searchByDepartmentName(String departmentName);
}
