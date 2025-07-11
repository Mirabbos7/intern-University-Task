package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;

    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

    @NotBlank
    private String middleName;

    private int age;

    private Set<Long> teachersId;

}