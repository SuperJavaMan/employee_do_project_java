package com.testprojects.employee_do_project.repositories;

import com.testprojects.employee_do_project.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
