package com.edutech.courses.service;

import com.edutech.courses.client.UserClient;
import com.edutech.courses.dto.CourseDto;
import com.edutech.courses.dto.UserResponseDto;
import com.edutech.courses.exception.ResourceNotFoundException;
import com.edutech.courses.model.Category;
import com.edutech.courses.model.Course;
import com.edutech.courses.model.Level;
import com.edutech.courses.repository.CategoryRepository;
import com.edutech.courses.repository.CourseRepository;
import com.edutech.courses.repository.LevelRepository;
import com.edutech.courses.controller.response.MessageResponse;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryService categoryService;
    private final LevelService levelService;
    private final UserClient userClient;
    private final InstructorValidatorService instructorValidatorService;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado: "+ id ));
    }


    public ResponseEntity<?> createCourse(@Valid CourseDto dto) {
        Category category = categoryService.getCategoryById(dto.getCategoryId());
        Level level = levelService.getLevelById(dto.getLevelId());

        instructorValidatorService.validateInstructor(dto.getInstructorId());

        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(category)
                .level(level)
                .instructorId(dto.getInstructorId())
                .price(dto.getPrice())
                .tags(dto.getTags())
                .build();

        courseRepository.save(course);
        return ResponseEntity.ok(new MessageResponse("Curso creado exitosamente"));
    }

    public ResponseEntity<MessageResponse> updateCourse(Long id, CourseDto dto) {
            Course course = getCourseById(id);
            Category category = categoryService.getCategoryById(dto.getCategoryId());
            Level level = levelService.getLevelById(dto.getLevelId());

            course.setTitle(dto.getTitle());
            course.setDescription(dto.getDescription());
            course.setPrice(dto.getPrice());
            course.setCategory(category);
            course.setLevel(level);
            course.setTags(dto.getTags());

            courseRepository.save(course);
            return ResponseEntity.ok(new MessageResponse("Curso actualizado correctamente."));
    }

    public ResponseEntity<MessageResponse> deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
        return ResponseEntity.ok(new MessageResponse("Curso eliminado correctamente."));
    }
}
