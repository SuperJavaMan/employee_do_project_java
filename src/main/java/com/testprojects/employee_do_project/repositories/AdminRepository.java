package com.testprojects.employee_do_project.repositories;

import com.testprojects.employee_do_project.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
