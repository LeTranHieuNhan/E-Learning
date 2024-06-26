package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.ReplyCommentDto;
import org.example.e_learningback.entity.Comment;
import org.example.e_learningback.entity.ReplyComment;
import org.example.e_learningback.entity.User;
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
    public ReplyCommentDto findReplyCommentById(Long id) throws Exception {
        ReplyComment replyComment = replyCommentRepository.findById(id)
                .orElseThrow(() -> new Exception("Reply Comment does not exist"));
        return genericMapper.map(replyComment, ReplyCommentDto.class);
    }

    @Override
    public List<ReplyCommentDto> findAllReplyCommentsByCommentId(Long commentId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isEmpty()) {
            throw new Exception("Comment does not exist");
        }

        List<ReplyComment> replyComments = replyCommentRepository.findAllByComment(commentOptional.get());

        return replyComments.stream()
                .map(replyComment -> genericMapper.map(replyComment, ReplyCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReplyCommentDto> findAllReplyCommentsByUserId(Long userId) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new Exception("User does not exist");
        }

        List<ReplyComment> replyComments = replyCommentRepository.findAllByUser(userOptional.get());

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
    public ReplyCommentDto createReplyComment(ReplyCommentDto newReplyCommentDto, Long commentId, Long userId) throws Exception{
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (commentOptional.isEmpty()) {
            throw new Exception("Comment does not exist");
        }
        if (userOptional.isEmpty()) {
            throw new Exception("User does not exist");
        }

        ReplyComment replyComment = genericMapper.map(newReplyCommentDto, ReplyComment.class);

        replyComment.setComment(commentOptional.get());
        replyComment.setUser(userOptional.get());

        replyComment = replyCommentRepository.save(replyComment);
        return genericMapper.map(replyComment, ReplyCommentDto.class);
    }

    @Override
    @Transactional
    public ReplyCommentDto updateReplyComment(Long replyCommentId, ReplyCommentDto newReplyCommentDto) {
        ReplyComment existingReplyComment = replyCommentRepository.findById(replyCommentId)
                .orElseThrow(() -> new RuntimeException("Reply Comment does not exist"));

        existingReplyComment.setBody(newReplyCommentDto.getBody());

        existingReplyComment = replyCommentRepository.save(existingReplyComment);
        return genericMapper.map(existingReplyComment, ReplyCommentDto.class);
    }

    @Override
    @Transactional
    public void deleteReplyCommentById(Long replyCommentId) {
        replyCommentRepository.deleteById(replyCommentId);
    }

    @Override
    @Transactional
    public void deleteAllReplyCommentsByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment does not exist"));

        replyCommentRepository.deleteAllByComment(comment);
    }
}
