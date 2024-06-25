package org.example.e_learningback.service;

import org.example.e_learningback.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAllCommentsByCourseSessionId(Long courseSessionId);
    List<CommentDto> findAllComments();
    CommentDto findCommentById(Long id);
    CommentDto createComment(CommentDto commentDto, Long courseSessionId, Long userId) throws Exception;
    CommentDto updateComment(Long commentId, CommentDto newCommentDto) throws Exception;
    void deleteComment(Long id) throws Exception;
    void deleteAllCommentsByCourseSessionId(Long courseSessionId);
}
