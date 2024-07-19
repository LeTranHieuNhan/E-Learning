package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSessionDto {
    private Long id;
    private String title;
    private Long sessionOrder;
    private String videoUrl;
    private CourseDto course;
    private String thumbnailUrl;

}
