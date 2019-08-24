package com.testprojects.employee_do_project.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
//@NamedQuery(name = "Admin.findSomeOne",
//        query = "SELECT a FROM Admin a WHERE a.id = :id")
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
    @Size(min = 4)
    private String secondName;

    @Column
    @Email
    private String email;

    @Column
    private String position;

    @Column
    private String phone;

    @OneToMany(mappedBy = "admin",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<Employee> employees = new HashSet<>();

    public Admin() {
    }

    public Admin(@NotNull @Size(min = 4) String firstName, @Size(min = 4) String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Admin(Long id, @NotNull @Size(min = 4) String firstName, @Size(min = 4) String secondName, @Email String email, String position, String phone, Set<Employee> employees) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.position = position;
        this.phone = phone;
        this.employees = employees;
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
