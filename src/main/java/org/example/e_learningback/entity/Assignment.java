package org.example.e_learningback.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date uploaded_at;
    private Date updated_at;
    private Date deadline;
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
