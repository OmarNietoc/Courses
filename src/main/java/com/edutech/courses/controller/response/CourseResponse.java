package com.edutech.courses.controller.response;

import com.edutech.courses.model.Category;
import com.edutech.courses.model.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private Category category;
    private Level level;
    private Long instructorId; // Se obtiene del microservicio de usuarios
    private BigDecimal price;
    private List<@Size(min = 1, max = 20) String> tags;

}