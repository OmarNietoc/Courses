package com.edutech.courses.controller;

import com.edutech.courses.controller.response.EnrollmentResponse;
import com.edutech.courses.dto.EnrollmentDto;
import com.edutech.courses.model.Enrollment;
import com.edutech.courses.service.EnrollmentService;
import com.edutech.courses.controller.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edutech/courses/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getEnrollmentById(@PathVariable Long id) {

        return enrollmentService.getEnrollmentDtoById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<MessageResponse> createEnrollment(@Valid @RequestBody EnrollmentDto dto) {
        return enrollmentService.createEnrollment(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateEnrollment(@PathVariable Long id, @Valid @RequestBody EnrollmentDto dto) {
        return enrollmentService.updateEnrollment(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEnrollment(@PathVariable Long id) {
        return enrollmentService.deleteEnrollment(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateEnrollmentStatus(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        enrollmentService.updateEnrollmentStatusById(id, active);
        return ResponseEntity.ok(new MessageResponse("Estado de inscripción actualizado exitosamente:" + active));
    }

}