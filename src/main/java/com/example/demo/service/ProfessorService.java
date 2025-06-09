package com.example.demo.service;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Professor;
import com.example.demo.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import static com.example.demo.mapper.ProfessorMapper.PROFESSOR_MAPPER;

@Service
@Transactional
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public ProfessorDto createProfessor(ProfessorDto professorDto){
        Professor entity = PROFESSOR_MAPPER.toEntity(professorDto);
        Professor saved = professorRepository.save(entity);
        return PROFESSOR_MAPPER.toDto(saved);
    }

    public Optional<ProfessorDto> getProfessor(Long id){
        return professorRepository.findById(id).map(PROFESSOR_MAPPER::toDto);
    }

    public ProfessorDto updateProfessor(Long id, ProfessorDto professorDetails){
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id));
        professor.setLastName(professorDetails.getLastName());
        professor.setFirstName(professorDetails.getFirstName());
        professor.setMiddleName(professorDetails.getMiddleName());
        professor.setAge(professorDetails.getAge());
        Professor saved = professorRepository.save(professor);
        return PROFESSOR_MAPPER.toDto(saved);
    }

    public void deleteProfessor(Long id){
        professorRepository.deleteById(id);
        System.out.println("Professor with ID " + id + "has been removed");
    }

    public List<ProfessorDto> professorList(){
        List<Professor> professors = professorRepository.findAll();
        return PROFESSOR_MAPPER.toDtoList(professors);
    }
}