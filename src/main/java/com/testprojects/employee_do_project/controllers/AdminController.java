package com.testprojects.employee_do_project.controllers;

import com.testprojects.employee_do_project.models.Admin;
import com.testprojects.employee_do_project.models.Employee;
import com.testprojects.employee_do_project.repositories.AdminRepository;
import com.testprojects.employee_do_project.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/admin")
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @GetMapping("/admin/{id}")
    public Admin getAdminById(@PathVariable Long id) {
        return adminRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @PostMapping("/admin")
    public Admin postAdmin(@RequestBody Admin adminRequest) {
        if (adminRequest != null) {
            Set<Employee> employeeListRequest = adminRequest.getEmployees();
            Set<Employee> employeeSetDB = new HashSet<>();
            for (Employee employee : employeeListRequest) {
                Employee employeeSaved = employeeRepository.findById(employee.getId())
                        .orElseThrow(NoSuchElementException::new);
                employeeSetDB.add(employeeSaved);
            }
            adminRequest.setEmployees(employeeSetDB);
        }
        return adminRepository.save(adminRequest);
    }

    @PutMapping("/admin/{id}")
    public Admin putAdmin(@PathVariable Long id,
                          @RequestBody Admin adminRequest) {
        return adminRepository.findById(id).map(admin -> {
            admin.setFirstName(adminRequest.getFirstName());
            admin.setSecondName(adminRequest.getSecondName());
            admin.setEmail(adminRequest.getEmail());
            admin.setPhone(adminRequest.getPhone());
            admin.setPosition(adminRequest.getPosition());
            admin.setEmployees(adminRequest.getEmployees());
            return adminRepository.save(admin);
        }).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        return adminRepository.findById(id).map(admin -> {
            adminRepository.delete(admin);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.badRequest().body("This admin does not exist in DB"));
    }

    @GetMapping("/admin/employee/{adminId}/{employeeId}")
    public ResponseEntity<?> addEmployeeToAdmin(@PathVariable Long adminId,
                                                @PathVariable Long employeeId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(NoSuchElementException::new);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(NoSuchElementException::new);
        admin.getEmployees().add(employee);
        adminRepository.save(admin);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/find/{id}")
    public Admin getSomeOne(@PathVariable Long id) {
        return adminRepository.findSomeOne(id);
    }
}