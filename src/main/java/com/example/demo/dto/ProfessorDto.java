package com.example.demo.dto;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class ProfessorDto {

    private Long id;

    private String lastName;
    private String firstName;
    private String middleName;
    private int age;

    private DisciplineDto discipline;

    private Set<StudentDto> students = new HashSet<>();
}
