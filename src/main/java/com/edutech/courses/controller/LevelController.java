package com.edutech.courses.controller;


import com.edutech.courses.dto.CourseDto;
import com.edutech.courses.model.Category;
import com.edutech.courses.model.Course;
import com.edutech.courses.controller.response.MessageResponse;
import com.edutech.courses.model.Level;
import com.edutech.courses.repository.CourseRepository;
import com.edutech.courses.repository.CategoryRepository;
import com.edutech.courses.repository.LevelRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/edutech/levels")
public class LevelController {

    @Autowired
    private LevelRepository levelRepository;

    @GetMapping
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Level> level = levelRepository.findById(id);
        if (level.isPresent()) {
            return ResponseEntity.ok(level.get());
        } else {
            MessageResponse response = MessageResponse.builder()
                    .message("Level con ID " + id + " no encontrado.")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PostMapping
    public ResponseEntity<MessageResponse> addLevel( @Valid @RequestBody Level level) {
        levelRepository.save(level);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Level creado exitosamente."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateLevel(@PathVariable Long id, @Valid @RequestBody Level levelDetails) {
        Optional<Level> optionalLevel = levelRepository.findById(id);
        if (optionalLevel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Level no encontrado."));
        }
        try {
            Level level = optionalLevel.get();
            level.setName(levelDetails.getName());
            levelRepository.save(level);
            return ResponseEntity.ok(new MessageResponse("Level actualizado exitosamente."));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error inesperado al crear el curso: " + e.getMessage()));
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteLevel(@PathVariable Long id) {
        if (!levelRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Level no encontrado."));
        }
        levelRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Level eliminado exitosamente."));
    }

}
