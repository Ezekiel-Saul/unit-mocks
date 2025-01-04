package com.zql.unit_mocks;

import com.zql.unit_mocks.Entity.Course;
import com.zql.unit_mocks.service.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceImplTest {

    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl();
    }

    @Test
    void addCourse_ShouldAddCourseSuccessfully() {
        Course course = new Course(101, "Mathematics", 2.5);
        Course addedCourse = courseService.addCourse(course);

        assertEquals(101, addedCourse.getId());
        assertEquals("Mathematics", addedCourse.getName());
        assertEquals(2.5, addedCourse.getLevel());
        assertEquals(1, courseService.getCourses().size());
    }

    @Test
    void deleteCourse_ShouldDeleteCourseSuccessfully() {
        courseService.addCourse(new Course(101, "Mathematics", 2.5));
        boolean isDeleted = courseService.deleteCourse(101);

        assertTrue(isDeleted);
        assertTrue(courseService.getCourses().isEmpty());
    }

    @Test
    void updateCourse_ShouldUpdateCourseSuccessfully() {
        courseService.addCourse(new Course(101, "Mathematics", 2.5));
        Course updatedCourse = new Course(101, "Advanced Mathematics", 3.0);

        Course result = courseService.updateCourse(updatedCourse);

        assertEquals("Advanced Mathematics", result.getName());
        assertEquals(3.0, result.getLevel());
        assertEquals(1, courseService.getCourses().size());
    }

    @Test
    void updateCourse_ShouldThrowException_WhenCourseNotFound() {
        Course updatedCourse = new Course(102, "Physics", 3.0);

        Exception exception = assertThrows(RuntimeException.class, () -> courseService.updateCourse(updatedCourse));

        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void getCourses_ShouldReturnAllCourses() {
        courseService.addCourse(new Course(101, "Mathematics", 2.5));
        courseService.addCourse(new Course(102, "Physics", 3.0));

        List<Course> courses = courseService.getCourses();

        assertEquals(2, courses.size());
    }
}
