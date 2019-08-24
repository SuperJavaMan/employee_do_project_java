package com.testprojects.employee_do_project.repositories;

import com.testprojects.employee_do_project.models.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<List<Employee>> findBySecondNameOrderByFirstNameAsc(String secondName);

    Optional<Employee> findFirstByProjectsIsNull();

    Optional<List<Employee>> findByPositionAndProjectsIsNull(String position);

    Long countByPositionIgnoreCase(String position);

    Optional<List<Employee>> findByEmailContainsIgnoreCase(String sequence);

    List<Employee> findByOrderByIdAsc();

    List<Employee> findByOrderBySecondNameAsc();

    List<Employee> findAll(Sort sort);



}
