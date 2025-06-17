package com.example.crud.employees.service;

import com.example.crud.employees.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteById(long id);
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
