package com.zql.unit_mocks.service;

import com.zql.unit_mocks.Entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public Student addStudent(Student student);

    public boolean deleteStudent(int id);

    public Student updateStudent(Student student);

    public List<Student> getStudents();

    public Student getStudentById(int id);

}
