package com.example.demo.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    public List<Student> getAll(){
        return studentService.studentList();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") Long id){
        studentService.getStudent(id).orElseThrow();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student discipline) {
        Student created = studentService.createStudent(discipline);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student disciplineDetails){
        studentService.updateStudent(id, disciplineDetails);
        return ResponseEntity.ok().build();
    }

}
