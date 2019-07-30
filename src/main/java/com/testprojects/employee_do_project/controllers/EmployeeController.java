package com.testprojects.employee_do_project.controllers;

import com.testprojects.employee_do_project.models.Employee;
import com.testprojects.employee_do_project.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(name = "/employee")
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @GetMapping(name = "/employee/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @PostMapping(name = "/employee")
    public Employee postEmployee(@RequestBody Employee employee) {
        if (employee != null) {
            return employeeRepository.save(employee);
        } else {
            throw new NoSuchElementException();
        }
    }

    @PutMapping(name = "/employee/{id}")
    public Employee putEmployee(@PathVariable(value = "id") Long id,
                                @RequestBody Employee requestEmployee) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFistName(requestEmployee.getFistName());
            employee.setSecondName(requestEmployee.getSecondName());
            employee.setPosition(requestEmployee.getPosition());
            employee.setEmail(requestEmployee.getEmail());
            employee.setPhone(requestEmployee.getPhone());
            return employeeRepository.save(employee);
        }).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping(name = "/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long id) {
       return employeeRepository.findById(id).map(employee -> {
           employeeRepository.delete(employee);
           return ResponseEntity.ok().build();
       }).orElseThrow(NoSuchElementException::new);
    }
}
