package com.example.demo.mapper;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Professor;
import org.mapstruct.Mapper;

@Mapper
public interface ProfessorMapper {

    Professor toEntity (ProfessorDto professorDto);
    ProfessorDto toDto (Professor professor);
}
