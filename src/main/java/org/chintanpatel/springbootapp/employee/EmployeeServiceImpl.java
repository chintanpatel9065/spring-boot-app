package org.chintanpatel.springbootapp.employee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployeeList() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public List<Employee> searchByFirstName(String firstName) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    @Override
    public List<Employee> searchByDepartmentName(String departmentName) {
        return employeeRepository.findByDepartmentNameContainingIgnoreCase(departmentName);
    }
}
