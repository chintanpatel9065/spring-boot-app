package org.chintanpatel.springbootapp.employee;

import jakarta.validation.Valid;
import org.chintanpatel.springbootapp.department.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/employees/listEmployee")
    public String listEmployee(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("departmentList", departmentService.getAllDepartmentList());
        model.addAttribute("employeeList", employeeService.getAllEmployeeList());
        return "employee-form";
    }

    @PostMapping("/employees/insertOrUpdateEmployee")
    public String insertOrUpdateEmployee(@Valid @ModelAttribute("employee")Employee employee, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("departmentList", departmentService.getAllDepartmentList());
            model.addAttribute("employeeList", employeeService.getAllEmployeeList());
            return "employee-form";
        }
        employeeService.addEmployee(employee);
        return "redirect:/employees/listEmployee";
    }

    @GetMapping("/employees/manageEmployee/{employeeId}")
    public String manageEmployee(@PathVariable Long employeeId, Model model){
        if (employeeId != null) {
            model.addAttribute("employee", employeeService.getEmployeeById(employeeId));
            model.addAttribute("departmentList", departmentService.getAllDepartmentList());
            model.addAttribute("employeeList", employeeService.getAllEmployeeList());
        }
        return "employee-form";
    }

    @GetMapping("/employees/deleteEmployee/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId){
        if (employeeId != null) {
            employeeService.deleteEmployeeById(employeeId);
        }
        return "redirect:/employees/listEmployee";
    }
}
