package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.e_learningback.entity.CourseSession;
import org.example.e_learningback.entity.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String body;
    private Date created_at;

    // private CourseSession courseSession;
    private UserDto user;
}
