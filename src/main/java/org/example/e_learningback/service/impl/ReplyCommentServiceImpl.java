package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.ReplyCommentDto;
import org.example.e_learningback.entity.Comment;
import org.example.e_learningback.entity.ReplyComment;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.exception.CommentNotFoundException;
import org.example.e_learningback.exception.ReplyCommentNotFoundException;
import org.example.e_learningback.exception.UserNotFoundException;
import org.example.e_learningback.repository.CommentRepository;
import org.example.e_learningback.repository.ReplyCommentRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.ReplyCommentService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyCommentServiceImpl implements ReplyCommentService {

    private final ReplyCommentRepository replyCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final GenericMapper genericMapper;

    @Override
    public ReplyCommentDto findReplyCommentById(Long id) {
        ReplyComment replyComment = replyCommentRepository.findById(id)
                .orElseThrow(() -> new ReplyCommentNotFoundException("Reply Comment does not exist with ID: " + id));
        return genericMapper.map(replyComment, ReplyCommentDto.class);
    }

    @Override
    public List<ReplyCommentDto> findAllReplyCommentsByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment does not exist with ID: " + commentId));

        List<ReplyComment> replyComments = replyCommentRepository.findAllByComment(comment);

        return replyComments.stream()
                .map(replyComment -> genericMapper.map(replyComment, ReplyCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReplyCommentDto> findAllReplyCommentsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID: " + userId));

        List<ReplyComment> replyComments = replyCommentRepository.findAllByUser(user);

        return replyComments.stream()
                .map(replyComment -> genericMapper.map(replyComment, ReplyCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReplyCommentDto> findAllReplyComments() {
        List<ReplyComment> replyComments = replyCommentRepository.findAll();

        return replyComments.stream()
                .map(replyComment -> genericMapper.map(replyComment, ReplyCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReplyCommentDto createReplyComment(ReplyCommentDto newReplyCommentDto, Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment does not exist with ID: " + commentId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID: " + userId));

        ReplyComment replyComment = genericMapper.map(newReplyCommentDto, ReplyComment.class);

        replyComment.setComment(comment);
        replyComment.setUser(user);

        replyComment = replyCommentRepository.save(replyComment);
        return genericMapper.map(replyComment, ReplyCommentDto.class);
    }

    @Override
    @Transactional
    public ReplyCommentDto updateReplyComment(Long replyCommentId, ReplyCommentDto newReplyCommentDto) {
        ReplyComment existingReplyComment = replyCommentRepository.findById(replyCommentId)
                .orElseThrow(() -> new ReplyCommentNotFoundException("Reply Comment does not exist with ID: " + replyCommentId));

        existingReplyComment.setBody(newReplyCommentDto.getBody());

        existingReplyComment = replyCommentRepository.save(existingReplyComment);
        return genericMapper.map(existingReplyComment, ReplyCommentDto.class);
    }

    @Override
    @Transactional
    public void deleteReplyCommentById(Long replyCommentId) {
        if (!replyCommentRepository.existsById(replyCommentId)) {
            throw new ReplyCommentNotFoundException("Reply Comment does not exist with ID: " + replyCommentId);
        }
        replyCommentRepository.deleteById(replyCommentId);
    }

    @Override
    @Transactional
    public void deleteAllReplyCommentsByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment does not exist with ID: " + commentId));

        replyCommentRepository.deleteAllByComment(comment);
    }
}
