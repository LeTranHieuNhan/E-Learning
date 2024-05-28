package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.e_learningback.entity.Course;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSessionDto {

    private Long id;
    private String status;
    private CourseSessionDto courseSession;
}
