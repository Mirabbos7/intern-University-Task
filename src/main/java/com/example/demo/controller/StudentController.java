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
    public List<StudentDto> getAll(){
        return studentService.studentList();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable("id") Long id){
        StudentDto studentDto = studentService.getStudent(id).orElseThrow();
        return ResponseEntity.ok(studentDto);
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto discipline) {
        StudentDto created = studentService.createStudent(discipline);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDto studentDetailsDto){
        StudentDto updated = studentService.updateStudent(id, studentDetailsDto);
        return ResponseEntity.ok(updated);
    }

}