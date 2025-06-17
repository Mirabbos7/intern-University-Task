package com.example.demo.service;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Discipline;
import com.example.demo.entity.Professor;
import com.example.demo.entity.Student;
import com.example.demo.repository.DisciplineRepository;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.demo.mapper.ProfessorMapper.PROFESSOR_MAPPER;

@Service
@Transactional
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentRepository studentRepository;

    public ProfessorService(ProfessorRepository professorRepository, DisciplineRepository disciplineRepository, StudentRepository studentRepository) {
        this.professorRepository = professorRepository;
        this.disciplineRepository = disciplineRepository;
        this.studentRepository = studentRepository;
    }

    public ProfessorDto createProfessor(ProfessorDto professorDto) {
        Professor professor = PROFESSOR_MAPPER.toEntity(professorDto);

        if (professorDto.getDiscipline() != null && professorDto.getDiscipline().getId() != null) {
            Discipline discipline = disciplineRepository.findById(professorDto.getDiscipline().getId())
                    .orElseThrow(() -> new RuntimeException("Discipline not found!"));
            professor.setDiscipline(discipline);
        }

        if (professorDto.getStudentsId() != null && !professorDto.getStudentsId().isEmpty()) {
            Set<Student> students = new HashSet<>(studentRepository.findAllById(professorDto.getStudentsId()));
            professor.setStudents(students);
            for (Student s : students) {
                s.getTeachers().add(professor);
            }
        }

        Professor saved = professorRepository.save(professor);
        Professor complete = professorRepository.findById(saved.getId())
                .orElseThrow(() -> new RuntimeException("Professor not found after save!"));

        return PROFESSOR_MAPPER.toDto(complete);
    }

    public Optional<ProfessorDto> getProfessor(Long id) {
        return professorRepository.findById(id).map(PROFESSOR_MAPPER::toDto);
    }

    public ProfessorDto updateProfessor(Long id, ProfessorDto professorDetails) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id));
        professor.setLastName(professorDetails.getLastName());
        professor.setFirstName(professorDetails.getFirstName());
        professor.setMiddleName(professorDetails.getMiddleName());
        professor.setAge(professorDetails.getAge());
        if (professorDetails.getDiscipline() != null && professorDetails.getDiscipline().getId() != null) {
            Discipline discipline = disciplineRepository.findById(professorDetails.getDiscipline().getId())
                    .orElseThrow(() -> new RuntimeException("Discipline not found!"));
            professor.setDiscipline(discipline);
        }

        if (professorDetails.getStudentsId() != null) {
            Set<Student> students = new HashSet<>(studentRepository.findAllById(professorDetails.getStudentsId()));
            professor.setStudents(students);
            for (Student student : students) {
                student.getTeachers().add(professor);
            }
        }

        Professor saved = professorRepository.save(professor);
        return PROFESSOR_MAPPER.toDto(saved);
    }

    public void deleteProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        for (Student student : professor.getStudents()) {
            student.getTeachers().remove(professor);
        }
        professor.getStudents().clear();
        professorRepository.save(professor);
        professorRepository.delete(professor);
    }

    public List<ProfessorDto> professorList() {
        List<Professor> professors = professorRepository.findAll();
        return PROFESSOR_MAPPER.toDtoList(professors);
    }
}