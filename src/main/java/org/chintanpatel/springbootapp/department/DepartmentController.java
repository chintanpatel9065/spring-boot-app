package org.chintanpatel.springbootapp.department;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments/listDepartment")
    public String listDepartment(Model model){
        model.addAttribute("department", new Department());
        model.addAttribute("departmentList", departmentService.getAllDepartmentList());
        return "department-form";
    }

    @PostMapping("/departments/insertOrUpdateDepartment")
    public String insertOrUpdateDepartment(@Valid @ModelAttribute("department")Department department, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("departmentList", departmentService.getAllDepartmentList());
            return "department-form";
        }
        departmentService.addDepartment(department);
        return "redirect:/departments/listDepartment";
    }

    @GetMapping("/departments/manageDepartment/{departmentId}")
    public String manageDepartment(@PathVariable Long departmentId, Model model){
        if (departmentId != null) {
            model.addAttribute("department", departmentService.getDepartmentById(departmentId));
            model.addAttribute("departmentList", departmentService.getAllDepartmentList());
        }
        return "department-form";
    }

    @GetMapping("/departments/deleteDepartment/{departmentId}")
    public String deleteDepartment(@PathVariable Long departmentId){
        if (departmentId != null) {
            departmentService.deleteDepartmentById(departmentId);
        }
        return "redirect:/departments/listDepartment";
    }
}
