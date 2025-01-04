package com.zql.unit_mocks.service;

import com.zql.unit_mocks.Entity.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CourseService {

    public Course addCourse(Course course);

    public boolean deleteCourse(int id);

    public Course updateCourse(Course course);

    public List<Course> getCourses();

    public Course getCourseById(int id);
}
