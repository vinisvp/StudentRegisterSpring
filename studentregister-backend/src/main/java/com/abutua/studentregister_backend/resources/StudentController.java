package com.abutua.studentregister_backend.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.studentregister_backend.models.Student;

import jakarta.annotation.PostConstruct;

@RestController
@CrossOrigin
public class StudentController {

    private List<Student> students = new ArrayList<>();

    @PostConstruct
    public void init() {
        Student s1 = new Student(1, "Pedro Vinicius", "pedro.vinicius@abutua.com", "(11) 99999-9999", 1, 1);
        Student s2 = new Student(2, "Glauco Todesco", "glauco.todesco@abutua.com", "(11) 99999-9999", 2, 2);
        students.add(s1);
        students.add(s2);
    }
    
    @GetMapping("students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        Student student = students.stream()
                          .filter(s -> s.getId() == id)
                          .findFirst()
                          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found!"));
        return ResponseEntity.ok(student);
    }

    @PostMapping("students")
    public ResponseEntity<Student> postStudent(@RequestBody Student student) {
        student.setId(students.size() + 1);
        students.add(student);
        
        URI location = ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(student.getId())
                       .toUri();
        return ResponseEntity.created(location).body(student);
    }
}
