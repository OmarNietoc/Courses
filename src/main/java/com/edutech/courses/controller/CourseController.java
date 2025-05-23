package com.edutech.courses.controller;

import com.edutech.courses.client.UserClient;
import com.edutech.courses.dto.CourseDto;
import com.edutech.courses.dto.UserResponseDto;
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
    @Autowired
    private UserClient userClient;


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

            // 👉 Validación del instructor
            UserResponseDto instructor;
            try {
                instructor = userClient.getUserById(dto.getInstructorId());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("El usuario con ID " + dto.getInstructorId() + " no existe."));
            }

            if (instructor.getRole() == null ||
                    !"INSTRUCTOR".equalsIgnoreCase(instructor.getRole().getName())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("El usuario con ID " + dto.getInstructorId() + " no tiene el rol de PROFESOR."));
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
    public ResponseEntity<MessageResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {

        return courseRepository.findById(id).map(course -> {

            // Verificar que la categoría exista
            Optional<Category> categoryOpt = categoryRepository.findById(courseDto.getCategoryId());
            if (categoryOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("La categoría con ID " + courseDto.getCategoryId() + " no existe."));
            }

            // Verificar que el nivel exista
            Optional<Level> levelOpt = levelRepository.findById(courseDto.getLevelId());
            if (levelOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("El nivel con ID " + courseDto.getLevelId() + " no existe."));
            }

            // Actualizar campos
            course.setTitle(courseDto.getTitle());
            course.setDescription(courseDto.getDescription());
            course.setPrice(courseDto.getPrice());
            course.setCategory(categoryOpt.get());
            course.setLevel(levelOpt.get());
            course.setTags(courseDto.getTags());

            courseRepository.save(course);

            return ResponseEntity.ok(new MessageResponse("Curso actualizado correctamente."));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Curso con ID " + id + " no encontrado.")));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable Long id) {
        return courseRepository.findById(id).map(course -> {
            courseRepository.delete(course);
            return ResponseEntity.ok(new MessageResponse("Curso eliminado correctamente."));
        }).orElse(ResponseEntity.notFound().build());
    }
}
