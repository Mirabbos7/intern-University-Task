package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Optional<Student> getStudent(Long id){
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student studentDetails){
        Student student = studentRepository.findById(id).orElseThrow();
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

    public List<Student> studentList(){
        return studentRepository.findAll();
    }
}
