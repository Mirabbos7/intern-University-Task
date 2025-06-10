package com.example.demo.mapper;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Professor;
import com.example.demo.entity.Student;
import org.mapstruct.*;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper STUDENT_MAPPER = org.mapstruct.factory.Mappers.getMapper(StudentMapper.class);

    // Игнорируем teachers, т.к. устанавливаем их в сервисе вручную
    @Mapping(target = "teachers", ignore = true)
    Student toEntity(StudentDto studentDto);

    // Преобразуем Set<Professor> -> Set<Long>
    @Mapping(target = "teachersId", source = "teachers", qualifiedByName = "professorsToIds")
    StudentDto toDto(Student student);

    List<StudentDto> toDtoList(List<Student> studentList);
    List<Student> toEntityList(List<StudentDto> studentDtos);

    @Named("professorsToIds")
    default Set<Long> professorsToIds(Set<Professor> teachers) {
        if (teachers == null) return null;
        return teachers.stream().map(Professor::getId).collect(Collectors.toSet());
    }
}