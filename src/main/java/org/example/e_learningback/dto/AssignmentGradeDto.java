package org.example.e_learningback.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.e_learningback.entity.Assignment;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentGradeDto {
    private Long id;
    private String feedback;
    private double grade;

    private AssignmentDto assignment;
}
