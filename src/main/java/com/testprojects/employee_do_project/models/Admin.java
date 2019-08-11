package com.testprojects.employee_do_project.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Size(min = 4)
    private String firstName;

    @Column
    @NotNull
    @Size(min = 4)
    private String secondName;

    @Column
    @NotNull
    @Email
    private String email;

    @Column
    @NotNull
    private String position;

    @Column
    @NotNull
    private String phone;

    @OneToMany(mappedBy = "admin",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<Employee> employees = new HashSet<>();

    public Admin() {
    }

    public Admin(@NotNull @Size(min = 4) String firstName,
                 @NotNull @Size(min = 4) String secondName,
                 @NotNull @Email String email,
                 @NotNull String position,
                 @NotNull String phone) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.position = position;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
