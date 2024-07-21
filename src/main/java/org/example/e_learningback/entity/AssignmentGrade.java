package org.example.e_learningback.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AssignmentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String feedback;
    private double grade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
}
