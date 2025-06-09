package com.example.demo.service;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.entity.Discipline;
import com.example.demo.repository.DisciplineRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.mapper.DisciplineMapper.DISCIPLINE_MAPPER;

@Service
@Transactional
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public DisciplineDto createDiscipline(DisciplineDto disciplineDto){
        Discipline entity = DISCIPLINE_MAPPER.toEntity(disciplineDto);
        Discipline saved = disciplineRepository.save(entity);
        return DISCIPLINE_MAPPER.toDto(saved);
    }

    public Optional<DisciplineDto> getDiscipline(Long id){
        return disciplineRepository.findById(id).map(DISCIPLINE_MAPPER::toDto);
    }

    public DisciplineDto updateDiscipline(Long id, DisciplineDto disciplineDetails){
        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discipline not found with id: " + id));
        discipline.setName(disciplineDetails.getName());
        Discipline saved = disciplineRepository.save(discipline);
        return DISCIPLINE_MAPPER.toDto(saved);
    }

    public void deleteDiscipline(Long id){
        disciplineRepository.deleteById(id);
        System.out.println("Discipline with ID " + id + "has been removed");
    }

    public List<DisciplineDto> disciplineList() {
        List<Discipline> disciplines = disciplineRepository.findAll();
        return DISCIPLINE_MAPPER.toDtoList(disciplines);
    }
}