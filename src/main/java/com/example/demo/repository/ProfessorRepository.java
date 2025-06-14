package com.example.demo.repository;

import com.example.demo.dto.ProfessorDto;
import com.example.demo.entity.Professor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
