package com.example.demo.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<StudentDto> getById(@PathVariable("id") Long id){
        return Optional.of(studentService.getStudent(id).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto disciplineDto) {
        StudentDto created = studentService.createStudent(disciplineDto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDto disciplineDetailsDto){
        studentService.updateStudent(id, disciplineDetailsDto);
        return ResponseEntity.ok().build();
    }

}
