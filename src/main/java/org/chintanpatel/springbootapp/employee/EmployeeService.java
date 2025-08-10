package org.chintanpatel.springbootapp.employee;

import java.util.List;

public interface EmployeeService {

    void addEmployee(Employee employee);

    List<Employee> getAllEmployeeList();

    Employee getEmployeeById(Long employeeId);

    void deleteEmployeeById(Long employeeId);

    List<Employee>searchByFirstName(String firstName);

    List<Employee>searchByDepartmentName(String departmentName);
}
