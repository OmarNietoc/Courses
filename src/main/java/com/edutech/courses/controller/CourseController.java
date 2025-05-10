package com.edutech.courses.controllers;

import com.edutech.courses.model.Course;
import com.edutech.courses.response.MessageResponse;
import com.edutech.courses.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createCourse(@Valid @RequestBody Course course) {
        courseRepository.save(course);
        return ResponseEntity.ok(new MessageResponse("Curso creado correctamente."));
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
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable Long id) {
        return courseRepository.findById(id).map(course -> {
            courseRepository.delete(course);
            return ResponseEntity.ok(new MessageResponse("Curso eliminado correctamente."));
        }).orElse(ResponseEntity.notFound().build());
    }
}
