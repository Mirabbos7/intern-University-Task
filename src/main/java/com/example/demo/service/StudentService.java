package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.mapper.StudentMapper.STUDENT_MAPPER;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDto createStudent(StudentDto studentDto){
        Student entity = STUDENT_MAPPER.toEntity(studentDto);
        Student student = studentRepository.save(entity);
        return STUDENT_MAPPER.toDto(student);
    }

    public Optional<StudentDto> getStudent(Long id){
        return studentRepository.findById(id).map(STUDENT_MAPPER::toDto);
    }

    public StudentDto updateStudent(Long id, StudentDto studentDetails){
        Student student = studentRepository.findById(id).orElseThrow(()-> new RuntimeException("Professor not found with id: " + id));
        student.setLastName(studentDetails.getLastName());
        student.setFirstName(studentDetails.getFirstName());
        student.setMiddleName(studentDetails.getMiddleName());
        student.setAge(studentDetails.getAge());
        Student saved = studentRepository.save(student);
        return STUDENT_MAPPER.toDto(saved);
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
        System.out.println("Student with ID " + id + "has been removed");
    }

    public List<StudentDto> studentList(){
        List<Student> students = studentRepository.findAll();
        return STUDENT_MAPPER.toDtoList(students);
    }
}