package com.example.demo.repository;

import com.example.demo.dto.DisciplineDto;
import com.example.demo.entity.Discipline;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}