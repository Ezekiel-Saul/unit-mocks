package com.zql.unit_mocks.rest;

import com.zql.unit_mocks.Entity.Course;
import com.zql.unit_mocks.Entity.Student;
import com.zql.unit_mocks.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseRestController {


    private final CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id){
        return courseService.getCourseById(id);
    }

    @PutMapping
    public Course updateCourse(@RequestBody Course course){
        return courseService.updateCourse(course);
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCourse(@PathVariable int id){
       return courseService.deleteCourse(id);
    }


}
