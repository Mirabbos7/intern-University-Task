package com.example.demo.service;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.entity.Discipline;
import com.example.demo.entity.Professor;
import com.example.demo.repository.DisciplineRepository;
import com.example.demo.repository.ProfessorRepository;
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
class DisciplineServiceTest {

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private DisciplineService disciplineService;


    @Test
    void testCreateDiscipline() {
        DisciplineDto inputDto = new DisciplineDto(null, "Math", Set.of(1L));

        Professor professor = Professor.builder()
                .id(1L)
                .firstName("Steeve")
                .lastName("Evans")
                .middleName("Doe")
                .age(40)
                .build();

        Discipline savedDiscipline = Discipline.builder()
                .id(10L)
                .name("Math")
                .teachers(Set.of(professor))
                .build();

        when(professorRepository.findAllById(Set.of(1L))).thenReturn(List.of(professor));
        when(disciplineRepository.save(any())).thenReturn(savedDiscipline);

        DisciplineDto result = disciplineService.createDiscipline(inputDto);

        assertNotNull(result);
        assertEquals("Math", result.getName());
        assertTrue(result.getTeachersId().contains(1L));

        verify(disciplineRepository, times(1)).save(any());
    }

    @Test
    void testGetDiscipline_found() {
        Discipline discipline = Discipline.builder()
                .id(1L)
                .name("Math")
                .teachers(Set.of())
                .build();

        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));

        Optional<DisciplineDto> result = disciplineService.getDiscipline(1L);

        assertTrue(result.isPresent());
        assertEquals("Math", result.get().getName());
    }

    @Test
    void testUpdateDiscipline_success() {
        Discipline existing = Discipline.builder()
                .id(1L)
                .name("Math")
                .build();

        Discipline updated = Discipline.builder()
                .id(1L)
                .name("Physics")
                .build();

        DisciplineDto inputDto = new DisciplineDto(1L, "Physics", null);

        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(disciplineRepository.save(any())).thenReturn(updated);

        DisciplineDto result = disciplineService.updateDiscipline(1L, inputDto);

        assertEquals("Physics", result.getName());
    }

    @Test
    void testUpdateDiscipline_notFound() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                disciplineService.updateDiscipline(1L, new DisciplineDto(1L, "Physics", null)));

        assertTrue(exception.getMessage().contains("Discipline not found"));
    }

    @Test
    void testDeleteDiscipline_success() {
        Discipline discipline = Discipline.builder()
                .id(1L)
                .name("Math")
                .build();

        Professor professor = Professor.builder()
                .id(1L)
                .discipline(discipline)
                .build();

        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(professorRepository.findAllByDiscipline(discipline)).thenReturn(List.of(professor));

        disciplineService.deleteDiscipline(1L);

        verify(professorRepository, times(1)).saveAll(any());
        verify(disciplineRepository, times(1)).delete(discipline);
    }

    @Test
    void testDeleteDiscipline_notFound() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                disciplineService.deleteDiscipline(1L));

        assertTrue(exception.getMessage().contains("Discipline not found"));
    }

    @Test
    void testDisciplineList() {
        Discipline discipline1 = Discipline.builder().id(1L).name("Math").build();
        Discipline discipline2 = Discipline.builder().id(2L).name("Physics").build();

        when(disciplineRepository.findAll()).thenReturn(List.of(discipline1, discipline2));

        List<DisciplineDto> result = disciplineService.disciplineList();

        assertEquals(2, result.size());
    }


}

