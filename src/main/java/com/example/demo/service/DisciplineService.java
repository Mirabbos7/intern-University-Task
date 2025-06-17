package com.example.demo.service;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.entity.Discipline;
import com.example.demo.entity.Professor;
import com.example.demo.repository.DisciplineRepository;
import com.example.demo.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.demo.mapper.DisciplineMapper.DISCIPLINE_MAPPER;

@Service
@Transactional
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final ProfessorRepository professorRepository;

    public DisciplineService(DisciplineRepository disciplineRepository, ProfessorRepository professorRepository) {
        this.disciplineRepository = disciplineRepository;
        this.professorRepository = professorRepository;
    }

    public DisciplineDto createDiscipline(DisciplineDto disciplineDto) {
        Discipline entity = DISCIPLINE_MAPPER.toEntity(disciplineDto);
        if (disciplineDto.getTeachersId() != null && !disciplineDto.getTeachersId().isEmpty()) {
            Set<Professor> professors = new HashSet<>(professorRepository.findAllById(disciplineDto.getTeachersId()));
            entity.setTeachers(professors);

            for (Professor professor : professors) {
                professor.setDiscipline(entity);
            }
            professorRepository.saveAll(professors);
        }

        Discipline saved = disciplineRepository.save(entity);
        return DISCIPLINE_MAPPER.toDto(saved);
    }


    public Optional<DisciplineDto> getDiscipline(Long id) {
        return disciplineRepository.findById(id).map(DISCIPLINE_MAPPER::toDto);
    }

    public DisciplineDto updateDiscipline(Long id, DisciplineDto disciplineDetails) {
        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discipline not found with id: " + id));
        discipline.setName(disciplineDetails.getName());
        Discipline saved = disciplineRepository.save(discipline);
        return DISCIPLINE_MAPPER.toDto(saved);
    }

    public void deleteDiscipline(Long disciplineId) {
        Discipline discipline = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new RuntimeException("Discipline not found with id: " + disciplineId));
        List<Professor> professors = professorRepository.findAllByDiscipline(discipline);
        for (Professor professor : professors) {
            professor.setDiscipline(null);
        }
        professorRepository.saveAll(professors);

        disciplineRepository.delete(discipline);
    }

    public List<DisciplineDto> disciplineList() {
        List<Discipline> disciplines = disciplineRepository.findAll();
        return DISCIPLINE_MAPPER.toDtoList(disciplines);
    }
}