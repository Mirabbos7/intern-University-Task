package com.example.demo.mapper;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.entity.Discipline;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DisciplineMapper {

    DisciplineMapper DISCIPLINE_MAPPER = Mappers.getMapper(DisciplineMapper.class);

    Discipline toEntity (DisciplineDto disciplineDto);
    DisciplineDto toDto (Discipline discipline);

    List<DisciplineDto> toDtoList(List<Discipline> entities);
    List<Discipline> toEntityList(List<DisciplineDto> dtos);
}