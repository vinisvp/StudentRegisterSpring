package com.abutua.studentregister_backend.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.studentregister_backend.models.Course;

import jakarta.annotation.PostConstruct;

@RestController
@CrossOrigin
public class CourseController {
    private List<Course> courses = new ArrayList<>();

    @PostConstruct
    private void init(){
        Course c1 = new Course(1, "Java");
        Course c2 = new Course(2, "Angular");
        Course c3 = new Course(3, "HTML/CSS");
        courses.add(c1);
        courses.add(c2);
        courses.add(c3);
    }

    @GetMapping("courses")
    private List<Course> getCourses(){
        return courses;
    }
}
