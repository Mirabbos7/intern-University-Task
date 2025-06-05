package com.example.demo.service;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Professor;
import com.example.demo.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfessorService {

    private ProfessorRepository professorRepository;

    public ProfessorDto createProfessor(ProfessorDto professorDto){
        return professorRepository.save(professorDto);
    }

    public Optional<ProfessorDto> getProfessor(Long id){
        return professorRepository.findById(id);
    }

    public void updateProfessor(Long id, ProfessorDto professorDetails){
        ProfessorDto professor = professorRepository.findById(id).orElseThrow();
        professor.setLastName(professorDetails.getLastName());
        professor.setFirstName(professorDetails.getFirstName());
        professor.setMiddleName(professorDetails.getMiddleName());
        professor.setAge(professorDetails.getAge());
        professorRepository.save(professor);
        System.out.println("Professor's data with ID" + professorDetails.getId() + " has been updated");
    }

    public void deleteProfessor(Long id){
        professorRepository.deleteById(id);
        System.out.println("Professor with ID " + id + "has been removed");
    }

    public List<ProfessorDto> professorList(){
        return professorRepository.findAll();
    }
}
