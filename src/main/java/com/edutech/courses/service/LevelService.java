package com.edutech.courses.service;

import com.edutech.courses.exception.ConflictException;
import com.edutech.courses.exception.ResourceNotFoundException;
import com.edutech.courses.model.Level;
import com.edutech.courses.repository.CourseRepository;
import com.edutech.courses.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;
    private final CourseRepository courseRepository;


    public Level getLevelById(Long id) {
        return levelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Level no encontrado: "+ id ));
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    public void createLevel(Level level) {
        levelRepository.save(level);
    }

    public void updateLevel(Long id, Level updatedLevel) {
        Level existingLevel = levelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Level no encontrado: " + id));
        existingLevel.setName(updatedLevel.getName());
        levelRepository.save(existingLevel);
    }

    public void deleteLevel(Long id) {
        getLevelById(id);
        if(courseRepository.existsCoursesByLevelId(id)){
            throw new ConflictException("No se puede eliminar el nivel porque hay cursos asociados a este.");
        }
        else {
        levelRepository.deleteById(id);
        }
    }

}
