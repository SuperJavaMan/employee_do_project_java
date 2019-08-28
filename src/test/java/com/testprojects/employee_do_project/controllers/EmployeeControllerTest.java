package com.testprojects.employee_do_project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testprojects.employee_do_project.models.Employee;
import com.testprojects.employee_do_project.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Employee employeeFirst;
    private Employee employeeSecond;
    private List<Employee> employeeList;

    @Before
    public void setUp() {
        employeeFirst = new Employee(
                1L,
                "Oleg",
                "Pavlyukov",
                "cpabox777@gmail.com",
                "Junior developer",
                "+380507857063"
        );
        employeeSecond = new Employee(
                2L,
                "Alex",
                "Pupkin",
                "mail@gmail.com",
                "Middle developer",
                "+780507851234"
        );
        employeeList = Arrays.asList(employeeFirst, employeeSecond);
    }

    @Test
    public void getAllEmployee() throws Exception {
        when(employeeRepository.findAll()).thenReturn(employeeList);

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Oleg"))
                .andExpect(jsonPath("$[0].secondName").value("Pavlyukov"))
                .andExpect(jsonPath("$[1].firstName").value("Alex"))
                .andExpect(jsonPath("$[1].secondName").value("Pupkin"));

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void getEmployeeById() throws Exception {
        when(employeeRepository.findById(employeeFirst.getId())).thenReturn(Optional.of(employeeFirst));

        mockMvc.perform(get("/employee/" + employeeFirst.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Oleg"))
                .andExpect(jsonPath("$.secondName").value("Pavlyukov"));

        verify(employeeRepository, times(1)).findById(employeeFirst.getId());
    }

    @Test
    public void postEmployee() throws Exception {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeFirst);

        mockMvc.perform(post("/employee")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employeeFirst)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Oleg"))
                .andExpect(jsonPath("$.secondName").value("Pavlyukov"));

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void putEmployee() throws Exception {
        Employee employeeUpdated = Stream.of(employeeFirst)
                .peek(employee -> employee.setPosition("Middle developer"))
                .findAny().get();

        when(employeeRepository.findById(employeeFirst.getId())).thenReturn(Optional.of(employeeFirst));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeUpdated);

        mockMvc.perform(put("/employee/" + employeeFirst.getId())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employeeFirst)))
                .andExpect(jsonPath("$.firstName").value("Oleg"))
                .andExpect(jsonPath("$.secondName").value("Pavlyukov"))
                .andExpect(jsonPath("$.position").value("Middle developer"));

        verify(employeeRepository, times(1)).findById(employeeFirst.getId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void deleteEmployee() throws Exception {
        when(employeeRepository.findById(employeeSecond.getId())).thenReturn(Optional.of(employeeSecond));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        mockMvc.perform(delete("/employee/" + employeeSecond.getId()))
                .andExpect(status().isOk());

        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }
}