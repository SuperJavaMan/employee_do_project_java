package com.testprojects.employee_do_project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @ManyToMany
    @JoinTable(name = "current_projects",
    joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("employees")
    private Set<Project> projects = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Employee() {
    }

    public Employee(@NotNull @Size(min = 4) String firstName,
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
    public Employee(Long id,
                    @NotNull @Size(min = 4) String firstName,
                    @NotNull @Size(min = 4) String secondName,
                    @NotNull @Email String email,
                    @NotNull String position,
                    @NotNull String phone) {
        this.id = id;
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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
