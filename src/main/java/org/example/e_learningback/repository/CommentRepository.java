package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Comment;
import org.example.e_learningback.entity.CourseSession;
import org.example.e_learningback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCourseSession(CourseSession courseSession);
    List<Comment> findAllByUser(User user);

    void deleteAllByCourseSessionId(Long courseSessionId);
}
