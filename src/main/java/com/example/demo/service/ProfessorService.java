package com.example.demo.service;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Professor;
import com.example.demo.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;

    public ProfessorDto createProfessor(ProfessorDto professorDto){
        return professorRepository.save(professorDto);
    }

    public Optional<ProfessorDto> getProfessor(Long id){
        return professorRepository.findById(id);
    }

    public ProfessorDto updateProfessor(Long id, Professor professorDetails){
        ProfessorDto professor = professorRepository.findById(id).orElseThrow();
        professor.setLastName(professorDetails.getLastName());
        professor.setFirstName(professorDetails.getFirstName());
        professor.setMiddleName(professorDetails.getMiddleName());
        professor.setAge(professorDetails.getAge());
        return professorRepository.save(professor);
    }

    public void deleteProfessor(Long id){
        professorRepository.deleteById(id);
        System.out.println("Professor with ID " + id + "has been removed");
    }

    public List<Professor> professorList(){
        return professorRepository.findAll();
    }
}
