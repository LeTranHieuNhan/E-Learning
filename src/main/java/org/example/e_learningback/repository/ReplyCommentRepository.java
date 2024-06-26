package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Comment;
import org.example.e_learningback.entity.ReplyComment;
import org.example.e_learningback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

    List<ReplyComment> findAllByComment(Comment comment);
    List<ReplyComment> findAllByUser(User user);
    void deleteAllByComment(Comment comment);
}
