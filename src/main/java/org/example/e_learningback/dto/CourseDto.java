package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private String course_duration;

    private CategoryDto category;
    private UserDto user;
    private Double averageRating;

    private Long totalReviews;
    private List<String> images;
}
