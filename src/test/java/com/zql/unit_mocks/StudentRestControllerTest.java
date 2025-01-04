package com.zql.unit_mocks;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zql.unit_mocks.Entity.Course;
import com.zql.unit_mocks.Entity.Student;
import com.zql.unit_mocks.rest.StudentRestController;
import com.zql.unit_mocks.service.StudentService;
import com.zql.unit_mocks.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class StudentRestControllerTest {

    private StudentService studentServiceMock;
    private StudentRestController studentRestControllerMock;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        studentServiceMock = mock(StudentService.class);
        studentRestControllerMock = new StudentRestController(studentServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(studentRestControllerMock).build();
    }

    @Test
    void addStudent_AddAStudentSuccessfully() throws Exception {
        Student student = new Student(1,"Maria Borges", Collections.singletonList(new Course(1,"Model", 5.0)));
        when(studentServiceMock.addStudent(any(Student.class))).thenReturn(student);
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"Maria Borges\",\"courses\":[{\"id\":1,\"name\":\"Model\",\"level\":5.0}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Borges"))
                .andExpect(jsonPath("$.courses[0].name").value("Model"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student(1,"Maria Borge", Collections.singletonList(new Course(1,"Engineer", 9.0)));
        when(studentServiceMock.updateStudent(any(Student.class))).thenReturn(student);
        ObjectMapper studentObjMapper = new ObjectMapper();
        String studentJson = studentObjMapper.writeValueAsString(student);
        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Borge"))
                .andExpect(jsonPath("$.courses[0].name").value("Engineer"))
                .andExpect(jsonPath("$.courses[0].level").value(9.0));

        verify(studentServiceMock, times(1)).updateStudent(any(Student.class));
    }

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student(1, "John Doe", List.of(new Course(101, "Math", 3.5)));
        when(studentServiceMock.getStudentById(1)).thenReturn(student);
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.courses[0].name").value("Math"));

        verify(studentServiceMock, times(1)).getStudentById(1);
    }
    @Test
    void testGetAllStudents() throws Exception {
        when(studentServiceMock.getStudents()).thenReturn(List.of(
                new Student(1, "John Doe", List.of(new Course(101, "Math", 3.5))),
                new Student(2, "Jane Doe", List.of())
        ));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));

        verify(studentServiceMock, times(1)).getStudents();
    }

    @Test
    void testDeleteStudentById() throws Exception {
        Student student = new Student(2, "John Doe", List.of(new Course(101, "Math", 3.5)));
        when(studentServiceMock.deleteStudent(2)).thenReturn(true);
        mockMvc.perform(delete("/students/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        verify(studentServiceMock, times(1)).deleteStudent(2);
    }

/*


@GetMapping
    public List<Student> getAllStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id){
        return studentService.getStudentById(id);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStudent(@PathVariable int id){
        return studentService.deleteStudent(id);
    }


 */

}
