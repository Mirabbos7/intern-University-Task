package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Professor;
import com.example.demo.entity.Student;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testCreateStudent() {
        StudentDto inputDto = new StudentDto(null, "John", "Doe", "Simonson", 22, Set.of(1L, 2L));

        Professor professor = Professor.builder()
                .id(10L)
                .firstName("Mirabbos")
                .lastName("Axmedov")
                .middleName("Nodir o'g'li")
                .build();


        Student student = Student.builder()
                .id(1L)
                .firstName("Steeve")
                .lastName("Evans")
                .middleName("Doe")
                .age(22)
                .teachers(Set.of(professor))
                .build();

        when(professorRepository.findAllById(Set.of(1L, 2L))).thenReturn(List.of(professor));
        when(studentRepository.save(any())).thenReturn(student);

        StudentDto result = studentService.createStudent(inputDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Steeve", result.getFirstName());

        verify(studentRepository, times(1)).save(any());

    }

    @Test
    void testGetStudent_found() {
        Student student = Student.builder()
                .id(1L)
                .firstName("Steeve")
                .lastName("Evans")
                .middleName("Doe")
                .age(22)
                .teachers(Set.of())
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<StudentDto> result = studentService.getStudent(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Steeve", result.get().getFirstName());
        assertEquals("Evans", result.get().getLastName());
        assertEquals("Doe", result.get().getMiddleName());
    }

    @Test
    void testUpdateDiscipline_success() {
        Student existing = Student.builder()
                .id(1L)
                .firstName("Steeve")
                .lastName("Evans")
                .middleName("Doe")
                .age(22)
                .teachers(Set.of())
                .build();

        Student updated = Student.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("Evens")
                .middleName("Doe")
                .age(22)
                .teachers(Set.of())
                .build();

        StudentDto inputDto = new StudentDto(1L, "Robert", "Evans", "Doe", 22, Set.of());

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(studentRepository.save(any())).thenReturn(updated);

        StudentDto result = studentService.updateStudent(1L, inputDto);

        assertEquals("Robert", result.getLastName());

    }

    @Test
    void testUpdateDiscipline_notFound(){
        when(studentRepository.findById(1L)).thenReturn((Optional.empty()));

        RuntimeException exception = assertThrows(RuntimeException.class,
                ()-> studentService.updateStudent(1L, new StudentDto(10L, "Ethan", "James", "Carter", 18, Set.of())));
        assertTrue(exception.getMessage().contains("Student not found"));
    }

    @Test
    void testDeleteStudent_success() {
        Professor professor = Professor.builder()
                .id(10L)
                .firstName("Mirabbos")
                .lastName("Axmedov")
                .middleName("Nodir o'g'li")
                .build();


        Student student = Student.builder()
                .id(1L)
                .firstName("Steeve")
                .lastName("Evans")
                .middleName("Doe")
                .age(22)
                .teachers(Set.of(professor))
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(professorRepository, times(1)).saveAll(any());
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void testDeleteStudent_notFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                studentService.deleteStudent(1L));

        assertTrue(exception.getMessage().contains("Student not found"));
    }

    @Test
    void testStudentList() {
        Student student1 = Student.builder().id(1L).build();
        Student student2 = Student.builder().id(2L).build();

        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        List<StudentDto> result = studentService.studentList();

        assertEquals(2, result.size());
    }
}





