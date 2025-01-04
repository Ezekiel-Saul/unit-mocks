package com.zql.unit_mocks.service;

import com.zql.unit_mocks.Entity.Course;
import com.zql.unit_mocks.Entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private List<Student> students;

    public StudentServiceImpl() {
        this.students = new ArrayList<>();
    }

    public Student addStudent(Student student){
        students.add(student);
        return student;
    }

    public boolean deleteStudent(int id){
        return students.removeIf(s->s.getId() == id);

    }

    public Student updateStudent(Student student){
        Optional<Student> stud = students.stream().filter(student1 -> student1.getId() == student.getId()).findFirst();
        if(stud.isPresent()){
            stud.get().setName(student.getName());
            stud.get().setCourses(student.getCourses());

            return student;
        }
        throw new RuntimeException("Student not found");
    }

    public List<Student> getStudents(){
        return students;
    }

    public Student getStudentById(int id){
        Optional<Student> first = students.stream().filter(student -> student.getId() == id).findFirst();
        if (first.isPresent()) return first.get();
        throw new RuntimeException("student not found");
    }

}
