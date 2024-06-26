package org.example.e_learningback.service;

import org.example.e_learningback.dto.ReplyCommentDto;

import java.util.List;

public interface ReplyCommentService {
    ReplyCommentDto findReplyCommentById(Long id) throws Exception;
    List<ReplyCommentDto> findAllReplyCommentsByCommentId(Long commentId) throws Exception;
    List<ReplyCommentDto> findAllReplyCommentsByUserId(Long userId) throws Exception;
    List<ReplyCommentDto> findAllReplyComments();
    ReplyCommentDto createReplyComment(ReplyCommentDto newReplyCommentDto, Long commentId, Long userId) throws Exception;
    ReplyCommentDto updateReplyComment(Long replyCommentId, ReplyCommentDto newReplyCommentDto);
    void deleteReplyCommentById(Long replyCommentId);
    void deleteAllReplyCommentsByCommentId(Long commentId);
}
