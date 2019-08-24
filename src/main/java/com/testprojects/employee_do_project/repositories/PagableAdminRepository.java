package com.testprojects.employee_do_project.repositories;

import com.testprojects.employee_do_project.models.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagableAdminRepository extends PagingAndSortingRepository<Admin, Long> {

    Page<Admin> findByFirstName(String name, Pageable pageable);
}
