package com.testprojects.employee_do_project.controllers;

import com.testprojects.employee_do_project.models.Employee;
import com.testprojects.employee_do_project.models.Project;
import com.testprojects.employee_do_project.repositories.EmployeeRepository;
import com.testprojects.employee_do_project.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@CrossOrigin
@RestController
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    private final ProjectRepository projectRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository,
                              ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

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
            Set<Project> projectList = employee.getProjects();
            Set<Project> oldProjectList = new HashSet<>();
            for (Project project : projectList) {
                Project oldProject = projectRepository.findById(project.getId()).get();
                oldProjectList.add(oldProject);
            }
            employee.setProjects(oldProjectList);
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

    @GetMapping("/employee/orderedByName/{secondName")
    public List<Employee> getBySecondNameAndOrderedByName(@PathVariable String secondName) {
        return employeeRepository.findBySecondNameOrderByFirstNameAsc(secondName).get();
    }

    @GetMapping("/employee/countByPosition/{position}")
    public Long countPositions(@PathVariable String position) {
        return employeeRepository.countByPositionIgnoreCase(position);
    }

    @GetMapping("/employee/available/{position}")
    public List<Employee> getAvailableByPosition(@PathVariable String position) {
        return employeeRepository.findByPositionAndProjectsIsNull(position).get();
    }

    @GetMapping("/employee/availableOne")
    public Employee getAvailableOne() {
        return employeeRepository.findFirstByProjectsIsNull().get();
    }

    @GetMapping("/employee/emailContains/{sequence}")
    public List<Employee> getAllThatContainsEmail(@PathVariable String sequence) {
        return employeeRepository.findByEmailContainsIgnoreCase(sequence).get();
    }

    @GetMapping("/employee/sorted")
    public List<Employee> getEmployeeSorted() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "email"));
    }
}
