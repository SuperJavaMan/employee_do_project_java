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
import java.util.Optional;

@CrossOrigin
@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/project")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/project/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @PostMapping("/project")
    public Project postProject(@RequestBody Project project) {
        if (project != null) {
            return projectRepository.save(project);
        } else throw new IllegalArgumentException();
    }

    @PutMapping("/project/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @RequestBody Project requestProject) {
        return projectRepository.findById(id).map(project -> {
            project.setProjectName(requestProject.getProjectName());
            project.setDescription(requestProject.getDescription());
            project.setEmployees(requestProject.getEmployees());
            return projectRepository.save(project);
        }).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        return projectRepository.findById(id).map(project -> {
            if (project.getEmployees().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("You can not delete active project. " +
                                "Please clean a list of employee before");
            } else {
                projectRepository.delete(project);
                return ResponseEntity.ok().build();
            }
        }).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("/project/employee/{projectId}/{employeeId}")
    public Project addEmployeeToProject(@PathVariable Long projectId,
                                                  @PathVariable Long employeeId) {
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        project.get().getEmployees().add(employee.get());
        return projectRepository.save(project.get());
    }
}
