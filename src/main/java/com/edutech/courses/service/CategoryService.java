package com.edutech.courses.service;

import com.edutech.courses.exception.ConflictException;
import com.edutech.courses.exception.ResourceNotFoundException;
import com.edutech.courses.model.Category;
import com.edutech.courses.repository.CategoryRepository;
import com.edutech.courses.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category no encontrada: " + id));
    }

    public void createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, Category updatedCategory) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category no encontrada: " + id));

        boolean nombreEnUso = categoryRepository.existsByNameIgnoreCaseAndIdNot(updatedCategory.getName(), id);
        if (nombreEnUso) {
            throw new ConflictException("Ya existe otra categoría con ese nombre.");
        }

        existing.setName(updatedCategory.getName());
        categoryRepository.save(existing);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        if (courseRepository.existsCoursesByCategoryId(id)) {
            throw new IllegalStateException("No se puede eliminar la category porque hay courses asociados a esta.");
        }

        categoryRepository.deleteById(id);
    }

}
