package com.testprojects.employee_do_project.controllers;

import com.testprojects.employee_do_project.models.Employee;
import com.testprojects.employee_do_project.models.Project;
import com.testprojects.employee_do_project.repositories.EmployeeRepository;
import com.testprojects.employee_do_project.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

//    public EmployeeController(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    @GetMapping("/employee")
    public List<Employee> getAllEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList;
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @PostMapping("/employee")
    public Employee postEmployee(@RequestBody Employee employee) {
        if (employee != null) {
            return employeeRepository.save(employee);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @PutMapping("/employee/{id}")
    public Employee putEmployee(@PathVariable Long id,
                                @RequestBody Employee requestEmployee) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(requestEmployee.getFirstName());
            employee.setSecondName(requestEmployee.getSecondName());
            employee.setPosition(requestEmployee.getPosition());
            employee.setEmail(requestEmployee.getEmail());
            employee.setPhone(requestEmployee.getPhone());
            employee.setProjects(requestEmployee.getProjects());
            return employeeRepository.save(employee);
        }).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
       return employeeRepository.findById(id).map(employee -> {
           employeeRepository.delete(employee);
           return ResponseEntity.ok().build();
       }).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("/employee/project/{employeeId}/{projectId}")
    public ResponseEntity<?> addProjectToEmployee(@PathVariable Long employeeId,
                                                  @PathVariable Long projectId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        Project project = projectRepository.findById(projectId).get();
        employee.getProjects().add(project);
        employeeRepository.save(employee);
        return ResponseEntity.ok().body("Ok");
    }
}
