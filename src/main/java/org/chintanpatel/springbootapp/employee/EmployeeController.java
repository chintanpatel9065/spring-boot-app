package org.chintanpatel.springbootapp.employee;

import jakarta.validation.Valid;
import org.chintanpatel.springbootapp.department.Department;
import org.chintanpatel.springbootapp.department.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/employees")
    public String listEmployee(Model model) {
        List<Employee> employeeList = employeeService.getAllEmployeeList();
        model.addAttribute("employeeList",employeeList);
        return "employee/employee-list";
    }

    @GetMapping("/employees/showEmployee")
    public String showEmployeeForm(Model model) {
        Employee employee = new Employee();
        List<Department> departmentList = departmentService.getAllDepartmentList();
        model.addAttribute("employee",employee);
        model.addAttribute("departmentList",departmentList);
        return "employee/employee-form";
    }

    @PostMapping("/employees/insertOrUpdateEmployee")
    public String insertOrUpdateEmployee(@Valid @ModelAttribute("employee")Employee employee, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<Department> departmentList = departmentService.getAllDepartmentList();
            model.addAttribute("departmentList",departmentList);
            return "employee/employee-form";
        }
        employeeService.addEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/employees/manageEmployee/{employeeId}")
    public String manageEmployee(@PathVariable Long employeeId, Model model) {
        if (employeeId != null) {
            Employee employee = employeeService.getEmployeeById(employeeId);
            List<Department> departmentList = departmentService.getAllDepartmentList();
            model.addAttribute("employee", employee);
            model.addAttribute("departmentList",departmentList);
        }
        return "employee/employee-form";
    }

    @GetMapping("/employees/deleteEmployee/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
        if (employeeId != null) {
            employeeService.deleteEmployeeById(employeeId);
        }
        return "redirect:/employees";
    }

    @GetMapping("/employees/searchEmployee")
    public String searchByFirstName(@RequestParam("firstName") String firstName, Model model) {
        List<Employee> employeeList = employeeService.searchByFirstName(firstName);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("firstName", firstName);
        model.addAttribute("searchType","firstName");
        return "employee/employee-list";
    }

    @GetMapping("/employees/searchEmployeeByDepartment")
    public String searchByDepartmentName(@RequestParam("departmentName") String departmentName, Model model) {
        List<Employee> employeeList = employeeService.searchByDepartmentName(departmentName);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("departmentName", departmentName);
        model.addAttribute("searchType","departmentName");
        return "employee/employee-list";
    }
}
