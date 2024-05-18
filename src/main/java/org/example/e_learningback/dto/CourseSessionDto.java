package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSessionDto {
    private Long id;
    private String title;
    private Long order;
    private String video_url;

    private CourseDto courseDto;
}
