package com.testprojects.employee_do_project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String fistName;

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

    @ManyToMany(fetch = FetchType.EAGER,
                cascade = CascadeType.ALL)
    @JoinTable(name = "current_projects",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(@NotNull @Size(min = 4) String fistName,
                    @NotNull @Size(min = 4) String secondName,
                    @NotNull @Email String email,
                    @NotNull String position,
                    @NotNull String phone) {
        this.fistName = fistName;
        this.secondName = secondName;
        this.email = email;
        this.position = position;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
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
