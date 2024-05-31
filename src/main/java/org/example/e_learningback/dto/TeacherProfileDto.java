package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherProfileDto {
    private Long id;
    private String name;
    private String avatar;
    private  String bio;
    private  String occupation;
    private String email;
    private Double averageRating; // Change the type to Double
    private long totalReviews;
    private long totalCourses;
    private long totalStudents; // Update to totalSessions to match the query
}
