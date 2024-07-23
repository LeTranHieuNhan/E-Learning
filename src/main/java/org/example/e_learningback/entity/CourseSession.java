package org.example.e_learningback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_session", uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "session_order"}))
public class CourseSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(name = "session_order")
    private Long sessionOrder;

    private String videoUrl;
    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "courseSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentSession> studentSessions = new ArrayList<>();
    @OneToMany(mappedBy = "courseSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;
}
