package com.example.demo.controller;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.service.DisciplineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService){
        this.disciplineService = disciplineService;
    }

    @GetMapping("/list")
    public List<DisciplineDto> getAll(){
        return disciplineService.disciplineList();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DisciplineDto> getById(@PathVariable("id") Long id){
        DisciplineDto dto = disciplineService.getDiscipline(id).orElseThrow();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<DisciplineDto> createDiscipline(@RequestBody DisciplineDto disciplineDto) {
        DisciplineDto created = disciplineService.createDiscipline(disciplineDto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DisciplineDto> deleteDiscipline(@PathVariable("id") Long id){
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DisciplineDto> updateDiscipline(@PathVariable("id") Long id, @RequestBody DisciplineDto disciplineDetailsDto){
        disciplineService.updateDiscipline(id, disciplineDetailsDto);
        return ResponseEntity.ok().build();
    }



}