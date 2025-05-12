package com.edutech.courses.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;

    @NotBlank
    @Size(min = 20)
    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long levelId;

    @NotNull
    private Long instructorId;

    @PositiveOrZero
    private Double price;

    private List<@Size(min = 1, max = 20) String> tags;
}

