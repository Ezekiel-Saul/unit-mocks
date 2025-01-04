package com.zql.unit_mocks;


import com.zql.unit_mocks.Entity.Course;
import com.zql.unit_mocks.rest.CourseRestController;
import com.zql.unit_mocks.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseRestControllerTest2{

    @MockitoBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course(101, "Mathematics", 2.5);
    }

    @Test
    void getAllCourses_ShouldReturnAllCourses() throws Exception {
        when(courseService.getCourses()).thenReturn(Arrays.asList(
                course,
                new Course(102, "Physics", 3.0)
        ));

        mockMvc.perform(get("/course")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].name").value("Mathematics"))
                .andExpect(jsonPath("$[0].level").value(2.5))
                .andExpect(jsonPath("$[1].id").value(102))
                .andExpect(jsonPath("$[1].name").value("Physics"))
                .andExpect(jsonPath("$[1].level").value(3.0));

        verify(courseService, times(1)).getCourses();
    }

    @Test
    void getCourseById_ShouldReturnCourse() throws Exception {
        when(courseService.getCourseById(101)).thenReturn(course);

        mockMvc.perform(get("/course/{id}", 101)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.level").value(2.5));

        verify(courseService, times(1)).getCourseById(101);
    }

    @Test
    void addCourse_ShouldAddCourseSuccessfully() throws Exception {
        when(courseService.addCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":101,\"name\":\"Mathematics\",\"level\":2.5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.level").value(2.5));

        verify(courseService, times(1)).addCourse(any(Course.class));
    }

    @Test
    void updateCourse_ShouldUpdateCourseSuccessfully() throws Exception {
        Course updatedCourse = new Course(101, "Advanced Mathematics", 3.0);

        when(courseService.updateCourse(any(Course.class))).thenReturn(updatedCourse);

        mockMvc.perform(put("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":101,\"name\":\"Advanced Mathematics\",\"level\":3.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.name").value("Advanced Mathematics"))
                .andExpect(jsonPath("$.level").value(3.0));

        verify(courseService, times(1)).updateCourse(any(Course.class));
    }

    @Test
    void deleteCourse_ShouldDeleteCourseSuccessfully() throws Exception {
        when(courseService.deleteCourse(101)).thenReturn(true);

        mockMvc.perform(delete("/course/{id}", 101)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(courseService, times(1)).deleteCourse(101);
    }

    @Test
    void getCourseById_ShouldReturn404WhenCourseNotFound() throws Exception {
        when(courseService.getCourseById(999)).thenReturn(null);

        mockMvc.perform(get("/course/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCourse_ShouldReturn400WhenInvalidData() throws Exception {
        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"not a number\",\"name\":\"Invalid Course\",\"level\":-1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCourse_ShouldReturn404WhenCourseIdDoesNotExist() throws Exception {
        when(courseService.updateCourse(any(Course.class))).thenReturn(null);

        mockMvc.perform(put("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":999,\"name\":\"Non-existing Course\",\"level\":2.5}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCourse_ShouldReturn404WhenCourseIdDoesNotExist() throws Exception {
        when(courseService.deleteCourse(999)).thenReturn(false);

        mockMvc.perform(delete("/course/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}