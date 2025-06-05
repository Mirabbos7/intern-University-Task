package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private StudentRepository studentRepository;

    public StudentDto createStudent(StudentDto student){
        return studentRepository.save(student);
    }

    public Optional<StudentDto> getStudent(Long id){
        return studentRepository.findById(id);
    }

    public StudentDto updateStudent(Long id, StudentDto studentDetails){
        StudentDto student = studentRepository.findById(id).orElseThrow();
        student.setLastName(studentDetails.getLastName());
        student.setFirstName(studentDetails.getFirstName());
        student.setMiddleName(studentDetails.getMiddleName());
        student.setAge(studentDetails.getAge());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
        System.out.println("Student with ID " + id + "has been removed");
    }

    public List<StudentDto> studentList(){
        return studentRepository.findAll();
    }
}
