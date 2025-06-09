package com.example.demo.mapper;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper {

    StudentMapper STUDENT_MAPPER = Mappers.getMapper(StudentMapper.class);

    Student toEntity(StudentDto studentDto);

    StudentDto toDto (Student student);

    List<StudentDto> toDtoList(List<Student> studentList);
    List<Student> toEntityList(List<StudentDto> studentDtos);
}