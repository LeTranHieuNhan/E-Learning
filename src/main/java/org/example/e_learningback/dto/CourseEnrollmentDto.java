package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentDto {
    private Long id;
    private double courseGrade;

    private CourseDto course;
    private UserDto user;
}
