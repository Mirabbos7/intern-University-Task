package com.example.demo.mapper;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.entity.Discipline;
import org.mapstruct.Mapper;

@Mapper
public interface DisciplineMapper {

    Discipline toDiscipline (DisciplineDto disciplineDto);
    DisciplineDto toDto (Discipline discipline);
}
