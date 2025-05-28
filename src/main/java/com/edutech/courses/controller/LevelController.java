package com.edutech.courses.controller;

import com.edutech.courses.model.Level;
import com.edutech.courses.controller.response.MessageResponse;
import com.edutech.courses.service.LevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edutech/courses/levels")
@RequiredArgsConstructor
public class LevelController {

    private final LevelService levelService;

    // GET: Obtener todos los niveles
    @GetMapping
    public ResponseEntity<List<Level>> getAllLevels() {
        return ResponseEntity.ok(levelService.getAllLevels());
    }

    // GET: Obtener nivel por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLevelById(@PathVariable Long id) {
        Level level = levelService.getLevelById(id);
        return ResponseEntity.ok(level);
    }

    // POST: Crear nivel
    @PostMapping("/add")
    public ResponseEntity<MessageResponse> createLevel(@Valid @RequestBody Level level) {
        levelService.createLevel(level);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Level creado exitosamente."));
    }

    // PUT: Actualizar nivel
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateLevel(@PathVariable Long id, @Valid @RequestBody Level updatedLevel) {
        levelService.updateLevel(id, updatedLevel);
        return ResponseEntity.ok(new MessageResponse("Level actualizado exitosamente."));
    }

    // DELETE: Eliminar nivel
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteLevel(@PathVariable Long id) {
        levelService.deleteLevel(id);
        return ResponseEntity.ok(new MessageResponse("Level eliminado exitosamente."));
    }
}