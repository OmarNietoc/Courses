package com.edutech.courses.repository;

import com.edutech.courses.model.Level;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
Optional<Level> findByNameIgnoreCase(String name);
}
