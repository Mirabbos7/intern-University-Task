package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Professor;
import com.example.demo.entity.Student;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.demo.mapper.StudentMapper.STUDENT_MAPPER;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    public StudentService(StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    public StudentDto createStudent(StudentDto studentDto) {
        Student entity = STUDENT_MAPPER.toEntity(studentDto);
        if (studentDto.getTeachersId() != null && !studentDto.getTeachersId().isEmpty()) {
            Set<Professor> professorSet = new HashSet<>(professorRepository.findAllById(studentDto.getTeachersId()));
            entity.setTeachers(professorSet);
        }
        Student student = studentRepository.save(entity);
        return STUDENT_MAPPER.toDto(student);
    }

    public Optional<StudentDto> getStudent(Long id) {
        return studentRepository.findById(id).map(STUDENT_MAPPER::toDto);
    }

    public StudentDto updateStudent(Long id, StudentDto studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        student.setLastName(studentDetails.getLastName());
        student.setFirstName(studentDetails.getFirstName());
        student.setMiddleName(studentDetails.getMiddleName());
        student.setAge(studentDetails.getAge());

        if (studentDetails.getTeachersId() != null) {
            Set<Professor> professorSet = new HashSet<>(professorRepository.findAllById(studentDetails.getTeachersId()));
            student.setTeachers(professorSet);
        }

        Student saved = studentRepository.save(student);
        Student refreshed = studentRepository.findById(saved.getId()).orElseThrow();
        return STUDENT_MAPPER.toDto(refreshed);
    }


    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Set<Professor> professors = student.getTeachers();
        for (Professor professor : professors) {
            Set<Student> students = professor.getStudents();
            if (students != null) {
                students.remove(student);
            }
        }

        professorRepository.saveAll(professors);
        studentRepository.delete(student);
    }


    public List<StudentDto> studentList() {
        List<Student> students = studentRepository.findAll();
        return STUDENT_MAPPER.toDtoList(students);
    }
}