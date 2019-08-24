package com.testprojects.employee_do_project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testprojects.employee_do_project.models.Admin;
import com.testprojects.employee_do_project.repositories.AdminRepository;
import com.testprojects.employee_do_project.repositories.EmployeeRepository;
import org.junit.After;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminRepository adminRepository;

    private Admin admin;

    private List<Admin> adminList;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        admin = new Admin("Jack", "Harper");
        adminList = Arrays.asList(admin);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllAdmins() throws Exception {
        when(adminRepository.findAll()).thenReturn(adminList);
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(admin.getFirstName()))
                .andExpect(jsonPath("$[0].secondName").value(admin.getSecondName()));
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    public void getAdminByName() throws Exception {
        when(adminRepository.findByFirstName(admin.getFirstName())).thenReturn(admin);
        mockMvc.perform(get("/admin/name/Jack"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(admin.getFirstName()));
        verify(adminRepository, times(1)).findByFirstName(admin.getFirstName());
    }

    @Test
    public void postAdmin() throws Exception {
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);
        mockMvc.perform(post("/admin")
                    .content(objectMapper.writeValueAsString(admin))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jack"))
                .andExpect(jsonPath("$.secondName").value("Harper"));
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    public void putAdmin() throws Exception {
        Long id = 1L;
        when(adminRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(admin));
//        doNothing().when(adminRepository.save(any(Admin.class)));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        mockMvc.perform(put("/admin/1")
                .content(objectMapper.writeValueAsString(admin))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jack"))
                .andExpect(jsonPath("$.secondName").value("Harper"));

        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    public void deleteAdmin() throws Exception {
        Long id = 1L;
        when(adminRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(admin));
        doNothing().when(adminRepository).delete(admin);

        mockMvc.perform(delete("/admin/1"))
                .andExpect(status().isOk());

        verify(adminRepository, times(1)).findById(id);
        verify(adminRepository, times(1)).delete(any(Admin.class));
    }

    @Test
    public void addEmployeeToAdmin() {
    }

    @Test
    public void getSomeOne() {
    }

    @Test
    public void findAdminByName() {
    }
}