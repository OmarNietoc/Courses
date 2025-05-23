package com.edutech.courses.controller;

import com.edutech.courses.model.Category;
import com.edutech.courses.repository.CategoryRepository;
import com.edutech.courses.controller.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/edutech/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // GET: Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    // GET: Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Categoría con ID " + id + " no encontrada."));
        }
        return ResponseEntity.ok(optionalCategory.get());
    }

    // POST: Crear nueva categoría
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Ya existe una categoría con ese nombre."));
        }

        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Categoría creada exitosamente."));
    }

    // PUT: Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody Category updatedCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Categoría con ID " + id + " no encontrada."));
        }

        // Verifica si ya existe otra categoría con ese nombre (ignorando mayúsculas/minúsculas)
        boolean nombreEnUso = categoryRepository.existsByNameIgnoreCaseAndIdNot(updatedCategory.getName(), id);
        if (nombreEnUso) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Ya existe otra categoría con ese nombre."));
        }

        Category category = optionalCategory.get();
        category.setName(updatedCategory.getName());
        categoryRepository.save(category);

        return ResponseEntity.ok(new MessageResponse("Categoría actualizada exitosamente."));
    }
}
