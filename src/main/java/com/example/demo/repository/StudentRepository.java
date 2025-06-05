package com.example.demo.repository;

import com.example.demo.dto.StudentDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<StudentDto, Long> {
}
