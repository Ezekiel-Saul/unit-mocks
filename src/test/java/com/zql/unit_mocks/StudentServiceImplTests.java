package com.zql.unit_mocks;

import com.zql.unit_mocks.Entity.Course;
import com.zql.unit_mocks.Entity.Student;
import com.zql.unit_mocks.service.StudentService;
import com.zql.unit_mocks.service.StudentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class StudentServiceImplTests {

    private StudentServiceImpl studentService;

    @BeforeEach
    public void initiateAll(){
        studentService = new StudentServiceImpl();
    }

    @Test
    void addStudent_ShouldAddStudentSucessfully(){
        Student student = new Student();
        student.setId(1);
        student.setName("Flavio Tester");
        student.setCourses(Collections.singletonList(new Course(101, "Mathematics", 2.5)));
        Student studentAdded = studentService.addStudent(student);
        Assertions.assertNotNull(studentAdded);
        assertEquals(1, studentAdded.getId());
        assertEquals("Flavio Tester", studentAdded.getName());
    }

    @Test
    void getStudentById_SucessfullyGetAStudentById(){
        Student student = new Student();
        student.setId(1);
        student.setName("Flavio Tester");
        student.setCourses(Collections.singletonList(new Course(101, "Mathematics", 2.5)));
        studentService.addStudent(student);
        Student getStudent = studentService.getStudentById(1);
        Assertions.assertNotNull(getStudent);
        assertEquals(1, getStudent.getId());
        assertEquals("Flavio Tester", getStudent.getName());
    }

    @Test
    void getAllStudents(){
        Student student = new Student();
        student.setId(1);
        student.setName("Flavio Tester");
        student.setCourses(Collections.singletonList(new Course(101, "Mathematics", 2.5)));
        ////
        Student student1 = new Student();
        student1.setId(2);
        student1.setName("Elton Tester");
        student1.setCourses(Collections.singletonList(new Course(101, "Mathematics", 2.5)));

        studentService.addStudent(student);
        studentService.addStudent(student1);

        assertEquals(2,studentService.getStudents().size());
        assertEquals("Elton Tester", studentService.getStudentById(2).getName());
    }

    @Test
    void deleteStudent_SucessfullyDeleteStudentByID(){
        Student student = new Student();
        student.setId(1);
        student.setName("Flavio Tester");
        student.setCourses(Collections.singletonList(new Course(101, "Mathematics", 2.5)));
        studentService.addStudent(student);
        boolean isDeleted = studentService.deleteStudent(1);

        assertTrue(isDeleted);
        assertTrue(studentService.getStudents().isEmpty());
    }
}
