package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
