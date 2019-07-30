package com.testprojects.employee_do_project.repositories;

import com.testprojects.employee_do_project.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
