package org.example.e_learningback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_session")
public class StudentSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_session_id")
    private CourseSession courseSession;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
