package com.example.demo.mapper;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    Student toEntity(StudentDto studentDto);

    StudentDto toDto (Student student);
}
