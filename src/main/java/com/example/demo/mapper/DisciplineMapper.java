package com.example.demo.mapper;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.entity.Discipline;
import com.example.demo.entity.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface DisciplineMapper {

    DisciplineMapper DISCIPLINE_MAPPER = Mappers.getMapper(DisciplineMapper.class);

    Discipline toEntity (DisciplineDto disciplineDto);

    @Mapping(target = "teachersId", source = "teachers", qualifiedByName = "mapTeachersToIds")
    DisciplineDto toDto(Discipline discipline);

    @Named("mapTeachersToIds")
    default Set<Long> mapTeachersToIds(Set<Professor> teachers) {
        if (teachers == null) return Set.of();
        return teachers.stream().map(Professor::getId).collect(Collectors.toSet());
    }

    List<DisciplineDto> toDtoList(List<Discipline> entities);
}