package com.example.demo.service;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Discipline;
import com.example.demo.entity.Professor;
import com.example.demo.entity.Student;
import com.example.demo.repository.DisciplineRepository;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private ProfessorService professorService;

    @Test
    void testCreateProfessor() {
        Discipline discipline = Discipline.
                builder()
                .id(1L)
                .name("Math")
                .build();

        Student student = Student.
                builder()
                .id(10L)
                .teachers(new HashSet<>())
                .build();

        ProfessorDto inputDto = new ProfessorDto(null, "Doe", "John", "M", 35, new DisciplineDto(1L, "Math", null), Set.of(10L));
        Professor professor = Professor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .middleName("M")
                .age(35)
                .discipline(discipline)
                .students(Set.of(student))
                .build();

        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(studentRepository.findAllById(Set.of(10L))).thenReturn(List.of(student));
        when(professorRepository.save(any())).thenReturn(professor);
        when(professorRepository.findById(any())).thenReturn(Optional.of(professor));

        ProfessorDto result = professorService.createProfessor(inputDto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetProfessor_found() {
        Professor professor = Professor.builder()
                .id(1L)
                .firstName("Alice")
                .build();
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));

        Optional<ProfessorDto> result = professorService.getProfessor(1L);

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getFirstName());
    }

    @Test
    void testUpdateProfessor_success() {
        Discipline discipline = Discipline.builder()
                .id(2L)
                .name("Physics")
                .build();

        Student student = Student.builder()
                .id(10L)
                .teachers(new HashSet<>())
                .build();

        Professor existing = Professor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .middleName("A")
                .age(40)
                .build();

        Professor updated = Professor
                .builder()
                .id(1L)
                .firstName("Mike")
                .lastName("Smith")
                .middleName("B")
                .age(45)
                .discipline(discipline)
                .students(Set.of(student))
                .build();

        ProfessorDto inputDto = new ProfessorDto(1L, "Smith", "Mike", "B", 45, new DisciplineDto(2L, "Physics", null), Set.of(10L));

        when(professorRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(disciplineRepository.findById(2L)).thenReturn(Optional.of(discipline));
        when(studentRepository.findAllById(Set.of(10L))).thenReturn(List.of(student));
        when(professorRepository.save(any())).thenReturn(updated);

        ProfessorDto result = professorService.updateProfessor(1L, inputDto);

        assertEquals("Mike", result.getFirstName());
    }

    @Test
    void testUpdateProfessor_notFound() {
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                professorService.updateProfessor(1L, new ProfessorDto(1L, "Test", "Test", "T", 30, null, null)));

        assertTrue(exception.getMessage().contains("Professor not found"));
    }

    @Test
    void testDeleteProfessor_success() {
        Student student = Student
                .builder()
                .id(10L)
                .teachers(new HashSet<>())
                .build();
        Professor professor = Professor
                .builder()
                .id(1L)
                .students(new HashSet<>(Set.of(student)))
                .build();
        student.getTeachers().add(professor);

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));

        professorService.deleteProfessor(1L);

        verify(professorRepository, times(1)).save(professor);
        verify(professorRepository, times(1)).delete(professor);
    }

    @Test
    void testDeleteProfessor_notFound() {
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                professorService.deleteProfessor(1L));

        assertTrue(exception.getMessage().contains("Professor not found"));
    }

    @Test
    void testProfessorList() {
        Professor professor1 = Professor
                .builder()
                .id(1L)
                .build();
        Professor professor2 = Professor
                .builder()
                .id(2L)
                .build();

        when(professorRepository.findAll()).thenReturn(List.of(professor1, professor2));

        List<ProfessorDto> result = professorService.professorList();

        assertEquals(2, result.size());
    }
}
