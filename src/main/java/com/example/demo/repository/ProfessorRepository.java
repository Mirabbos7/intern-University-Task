package com.example.demo.repository;


import com.example.demo.entity.Discipline;
import com.example.demo.entity.Professor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findAllByDiscipline(Discipline discipline);
}