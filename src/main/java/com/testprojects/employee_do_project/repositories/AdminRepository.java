package com.testprojects.employee_do_project.repositories;

import com.testprojects.employee_do_project.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {

//    Admin findSomeOne(@Param("id") Long id);

    Admin findByFirstName(String fistName);
}
