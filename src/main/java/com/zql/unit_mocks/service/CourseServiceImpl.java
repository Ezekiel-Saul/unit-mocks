package com.zql.unit_mocks.service;

import com.zql.unit_mocks.Entity.Course;
import com.zql.unit_mocks.Entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private List<Course> courses;

    public CourseServiceImpl() {
        this.courses = new ArrayList<>();
    }

    public Course addCourse(Course course){
        courses.add(course);
        return course;
    }

    public boolean deleteCourse(int id){
        return courses.removeIf(s->s.getId() == id);

    }

    public Course updateCourse(Course course){
        Optional<Course> co = courses.stream().filter(course1 -> course1.getId() == course.getId()).findFirst();
        if(co.isPresent()){
            co.get().setName(course.getName());
            co.get().setLevel(course.getLevel());

            return course;
        }
        throw new RuntimeException("Course not found");
    }

    public List<Course> getCourses(){
        return courses;
    }

    public Course getCourseById(int id){
        Optional<Course> first = courses.stream().filter(course -> course.getId() == id).findFirst();
        if (first.isPresent()) return first.get();
        throw new RuntimeException("Course not found");
    }



}
