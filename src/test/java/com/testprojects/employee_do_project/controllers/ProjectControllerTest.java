package com.testprojects.employee_do_project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testprojects.employee_do_project.models.Project;
import com.testprojects.employee_do_project.repositories.ProjectRepository;
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
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectRepository projectRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Project projectFirst;
    private Project projectSecond;
    private List<Project> projectList;

    @Before
    public void setUp() throws Exception {
        projectFirst = new Project(
                1L,
                "First project",
                "Awesome project");
        projectSecond = new Project(
                2L,
                "Second project",
                "Not bad project");
        projectList = Arrays.asList(projectFirst, projectSecond);
    }

    @Test
    public void getAllProjects() throws Exception {
        when(projectRepository.findAll()).thenReturn(projectList);

        mockMvc.perform(get("/project"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].projectName").value("First project"))
                .andExpect(jsonPath("$[1].projectName").value("Second project"));

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void getProjectById() throws Exception {
        when(projectRepository.findById(projectFirst.getId())).thenReturn(Optional.of(projectFirst));

        mockMvc.perform(get("/project/" + projectFirst.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName").value("First project"));

        verify(projectRepository, times(1)).findById(projectFirst.getId());
    }

    @Test
    public void postProject() throws Exception {
        when(projectRepository.save(any(Project.class))).thenReturn(projectFirst);

        mockMvc.perform(post("/project")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(projectFirst)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName").value("First project"));

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void updateProject() throws Exception {
        Project updated = Stream.of(projectFirst)
                .peek(project -> project.setDescription("New description"))
                .findAny().get();

        when(projectRepository.findById(projectFirst.getId())).thenReturn(Optional.of(projectFirst));
        when(projectRepository.save(any(Project.class))).thenReturn(updated);

        mockMvc.perform(put("/project/1")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(projectFirst)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName").value("First project"))
                .andExpect(jsonPath("$.description").value("New description"));

        verify(projectRepository, times(1)).findById(projectFirst.getId());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void deleteProject() throws Exception {
        when(projectRepository.findById(projectSecond.getId())).thenReturn(Optional.of(projectSecond));
        doNothing().when(projectRepository).delete(any(Project.class));

        mockMvc.perform(delete("/project/" + projectSecond.getId()))
                .andExpect(status().isOk());

        verify(projectRepository, times(1)).findById(projectSecond.getId());
        verify(projectRepository, times(1)).delete(any(Project.class));
    }
}