package com.testprojects.employee_do_project.repositories;

import com.testprojects.employee_do_project.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE LOWER(p.description)" +
            "LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.projectName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Project> findByDescriptionOrProjectName(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM projects ORDER BY id DESC", nativeQuery = true)
    List<Project> getInInverseOrder();

    @Query("SELECT p FROM Project p WHERE p.id < :id")
    List<Project> getOldest(@Param("id") Long id);
}
