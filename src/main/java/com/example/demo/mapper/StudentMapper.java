package com.example.demo.mapper;

import com.example.demo.dto.StudentDto;
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
public interface StudentMapper {

    StudentMapper STUDENT_MAPPER = Mappers.getMapper(StudentMapper.class);

    @Mapping(target = "teachers", ignore = true)
    Student toEntity(StudentDto studentDto);

    @Mapping(target = "teachersId", source = "teachers", qualifiedByName = "professorToId")
    StudentDto toDto(Student student);

    List<StudentDto> toDtoList(List<Student> studentList);

    @Named("professorToId")
    default Set<Long> professorToId(Set<Professor> professorSet) {
        if (professorSet == null) return null;
        return professorSet.stream().map(Professor::getId).collect(Collectors.toSet());
    }
}