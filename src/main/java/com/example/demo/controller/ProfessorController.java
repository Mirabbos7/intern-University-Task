package com.example.demo.controller;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ProfessorDto> getById(@PathVariable("id") Long id){
        ProfessorDto professorDto = professorService.getProfessor(id).orElseThrow();
        return ResponseEntity.ok(professorDto);
    }

    @PostMapping
    public ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto ProfessorDto) {
        ProfessorDto created = professorService.createProfessor(ProfessorDto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProfessorDto> deleteProfessor(@PathVariable("id") Long id){
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfessorDto> updateProfessor(@PathVariable("id") Long id, @RequestBody ProfessorDto professorDetails){
        ProfessorDto professorDto = professorService.updateProfessor(id, professorDetails);
        return ResponseEntity.ok(professorDto);
    }
}