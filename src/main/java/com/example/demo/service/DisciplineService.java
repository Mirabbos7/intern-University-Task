package com.example.demo.service;

import com.example.demo.entity.Discipline;
import com.example.demo.repository.DisciplineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;

    public Discipline createDiscipline(Discipline discipline){
        return disciplineRepository.save(discipline);
    }

    public Optional<Discipline> getDiscipline(Long id){
        return disciplineRepository.findById(id);
    }

    public Discipline updateDiscipline(Long id, Discipline disciplineDetails){
        Discipline discipline = disciplineRepository.findById(id).orElseThrow();
        discipline.setName(disciplineDetails.getName());
        return disciplineRepository.save(discipline);
    }

    public void deleteDiscipline(Long id){
        disciplineRepository.deleteById(id);
        System.out.println("Discipline with ID " + id + "has been removed");
    }

    public List<Discipline> disciplineList(){
        return disciplineRepository.findAll();
    }
}
