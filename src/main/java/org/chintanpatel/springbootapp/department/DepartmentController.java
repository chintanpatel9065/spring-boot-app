package org.chintanpatel.springbootapp.department;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public String listDepartment(Model model) {
        List<Department>departmentList = departmentService.getAllDepartmentList();
        model.addAttribute("departmentList",departmentList);
        return "department/department-list";
    }

    @GetMapping("/departments/showDepartment")
    public String showDepartmentForm(Model model) {
        Department department = new Department();
        model.addAttribute("department",department);
        return "department/department-form";
    }

    @PostMapping("/departments/insertOrUpdateDepartment")
    public String insertOrUpdateDepartment(@Valid @ModelAttribute("department")Department department, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "department/department-form";
        }
        departmentService.addDepartment(department);
        return "redirect:/departments";
    }

    @GetMapping("/departments/manageDepartment/{departmentId}")
    public String manageDepartment(@PathVariable Long departmentId, Model model) {
        if (departmentId != null) {
            Department department = departmentService.getDepartmentById(departmentId);
            model.addAttribute("department", department);
        }
        return "department/department-form";
    }

    @GetMapping("/departments/deleteDepartment/{departmentId}")
    public String deleteDepartment(@PathVariable Long departmentId) {
        if (departmentId != null) {
            departmentService.deleteDepartmentById(departmentId);
        }
        return "redirect:/departments";
    }

    @GetMapping("/departments/searchDepartment")
    public String searchByDepartmentName(@RequestParam("departmentName") String departmentName, Model model) {
        List<Department> departmentList = departmentService.searchByDepartmentName(departmentName);
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("departmentName", departmentName);
        model.addAttribute("searchType","departmentName");
        return "department/department-list";
    }
 }
