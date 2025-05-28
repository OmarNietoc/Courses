package com.edutech.courses.controller;

import com.edutech.courses.dto.CourseDto;
import com.edutech.courses.model.Course;
import com.edutech.courses.controller.response.MessageResponse;
import com.edutech.courses.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edutech/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long levelId) {

        Page<Course> courses = courseService.getCourses(page, size, categoryId, levelId);
        List<Course> content = courses.getContent();
        if (content.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto dto) {
        return courseService.createCourse(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        return courseService.updateCourse(id, courseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }
}
