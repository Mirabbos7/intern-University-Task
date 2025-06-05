package com.example.demo.service;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.entity.Discipline;
import com.example.demo.repository.DisciplineRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DisciplineService {

    private DisciplineRepository disciplineRepository;

    public DisciplineDto createDiscipline(DisciplineDto discipline){
        return disciplineRepository.save(discipline);
    }

    public Optional<DisciplineDto> getDiscipline(Long id){
        return disciplineRepository.findById(id);
    }

    public DisciplineDto updateDiscipline(Long id, DisciplineDto disciplineDetails){
        DisciplineDto discipline = disciplineRepository.findById(id).orElseThrow();
        discipline.setName(disciplineDetails.getName());
        return disciplineRepository.save(discipline);
    }

    public void deleteDiscipline(Long id){
        disciplineRepository.deleteById(id);
        System.out.println("Discipline with ID " + id + "has been removed");
    }

    public List<DisciplineDto> disciplineList(){
        return disciplineRepository.findAll();
    }
}
