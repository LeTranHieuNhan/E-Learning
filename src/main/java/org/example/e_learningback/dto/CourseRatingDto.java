package org.example.e_learningback.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRatingDto {
    private Long id;
    private Long rating;
    private String review;
    private Date created_at;
    private UserDto user;
}
