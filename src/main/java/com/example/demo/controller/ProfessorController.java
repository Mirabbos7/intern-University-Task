package com.example.demo.controller;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService disciplineService){
        this.professorService = disciplineService;
    }


    @GetMapping("/list")
    public List<ProfessorDto> getAll(){
        return professorService.professorList();
    }

    @GetMapping("/get/{id}")
    public Optional<ProfessorDto> getById(@PathVariable("id") Long id){
        return Optional.of(professorService.getProfessor(id).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto ProfessorDto) {
        ProfessorDto created = professorService.createProfessor(ProfessorDto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ProfessorDto> deleteProfessor(@PathVariable("id") Long id){
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ProfessorDto> updateProfessor(@PathVariable("id") Long id, @RequestBody ProfessorDto professorDetailsDto){
        professorService.updateProfessor(id, professorDetailsDto);
        return ResponseEntity.ok().build();
    }
}
