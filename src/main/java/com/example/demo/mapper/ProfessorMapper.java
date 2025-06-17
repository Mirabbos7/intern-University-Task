package com.example.demo.mapper;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Professor;
import com.example.demo.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface ProfessorMapper {

    ProfessorMapper PROFESSOR_MAPPER = Mappers.getMapper(ProfessorMapper.class);

    Professor toEntity (ProfessorDto professorDto);

    @Mapping(target = "studentsId", source = "students", qualifiedByName = "studentsToIds")
    ProfessorDto toDto(Professor professor);

    @Named("studentsToIds")
    default Set<Long> studentsToIds(Set<Student> students) {
        return students == null ? Set.of() : students.stream().map(Student::getId).collect(Collectors.toSet());
    }

    List<ProfessorDto> toDtoList(List<Professor> professor);
}