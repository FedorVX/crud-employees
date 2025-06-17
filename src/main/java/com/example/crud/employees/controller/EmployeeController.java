package com.example.crud.employees.controller;

import com.example.crud.employees.model.Employee;
import com.example.crud.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/")
    public String viewHomePage(Model model){
        return findPaginated(1, "firstName", "asc", model); //default start app
    }


    @GetMapping("/showNewEmployeeForm")
    public String addNewEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }


    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        //save emp in DB
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/showFormUpdate/{id}")
    public String updateEmployee(Model model, @PathVariable( value="id" ) long id){
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update_employee";
    }


    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("id") long id){
        employeeService.deleteById(id);
        return "redirect:/";
    }


    // /page/1?sortField=name&sortDir=asc


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value= "sortField", defaultValue = "firstName", required=false) String sortField,
                                @RequestParam(value= "sortDirection", defaultValue = "asc", required=false) String sortDirection,
                                Model model){
        int pageSize = 5;

        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<Employee> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEmployees", listEmployees);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        return "index";
    }

}
