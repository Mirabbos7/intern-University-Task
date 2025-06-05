package com.example.demo.dto;

import java.util.HashSet;
import java.util.Set;

public class DisciplineDto {

    private Long id;

    private String name;

    private Set<ProfessorDto> teachers = new HashSet<>();

    private Set<StudentDto> students = new HashSet<>();

}
