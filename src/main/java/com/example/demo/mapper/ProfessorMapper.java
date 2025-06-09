package com.example.demo.mapper;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProfessorMapper {

    ProfessorMapper PROFESSOR_MAPPER = Mappers.getMapper(ProfessorMapper.class);

    Professor toEntity (ProfessorDto professorDto);
    ProfessorDto toDto (Professor professor);

    List<ProfessorDto> toDtoList(List<Professor> professor);
    List<Professor> toEntityList(List<ProfessorDto> professorDtoList);
}