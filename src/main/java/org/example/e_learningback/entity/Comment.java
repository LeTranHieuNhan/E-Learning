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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "course_video_id")
    private CourseSession courseSession;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
