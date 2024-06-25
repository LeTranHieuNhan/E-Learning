package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherReviewDto {
    private Long id;
    private String name;
    private double averageRating;
    private long totalReviews;
    private long rating1Count;
    private long rating2Count;
    private long rating3Count;
    private long rating4Count;
    private long rating5Count;
}
