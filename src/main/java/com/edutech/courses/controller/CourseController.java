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
@RequestMapping("/edutech/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LevelRepository levelRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            MessageResponse response = MessageResponse.builder()
                    .message("Curso con ID " + id + " no encontrado.")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto dto) {
        try{
        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
        Optional<Level> level = levelRepository.findById(dto.getLevelId());

        if (category.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Categoría no válida."));
        }
        if (level.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nivel no válido."));
        }



            Course course = Course.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .category(category.get())
                    .level(level.get())
                    .instructorId(dto.getInstructorId())
                    .price(dto.getPrice())
                    .tags(dto.getTags())
                    .build();
            courseRepository.save(course);
            return ResponseEntity.ok(new MessageResponse("Curso creado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error inesperado al crear el curso: " + e.getMessage()));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody Course courseDetails) {
        return courseRepository.findById(id).map(course -> {
            course.setTitle(courseDetails.getTitle());
            course.setDescription(courseDetails.getDescription());
            course.setPrice(courseDetails.getPrice());
            course.setCategory(courseDetails.getCategory());
            course.setLevel(courseDetails.getLevel());
            course.setTags(courseDetails.getTags());
            courseRepository.save(course);
            return ResponseEntity.ok(new MessageResponse("Curso actualizado correctamente."));
        }).orElse(ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(MessageResponse.builder()
                        .message("Curso con ID " + id + " no encontrado.")
                        .build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable Long id) {
        return courseRepository.findById(id).map(course -> {
            courseRepository.delete(course);
            return ResponseEntity.ok(new MessageResponse("Curso eliminado correctamente."));
        }).orElse(ResponseEntity.notFound().build());
    }
}
