package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CommentDto;
import org.example.e_learningback.entity.Comment;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.CourseSession;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.repository.CommentRepository;
import org.example.e_learningback.repository.CourseSessionRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.CommentService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final UserRepository userRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<CommentDto> findAllCommentsByCourseSessionId(Long courseSessionId) {
        return commentRepository.findByCourseSessionId(courseSessionId)
                .stream()
                .map(comment -> genericMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> genericMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto findCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
        return genericMapper.map(comment, CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto createComment(CommentDto commentDto, Long courseSessionId, Long userId) throws Exception{
        Optional<CourseSession> courseSessionOptional = courseSessionRepository.findById(userId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (courseSessionOptional.isEmpty() || userOptional.isEmpty()) {
            throw new Exception("Course or User does not exist");
        }
        Comment comment = genericMapper.map(commentDto, Comment.class);

        comment.setCourseSession(courseSessionOptional.get());
        comment.setUser(userOptional.get());
        comment = commentRepository.save(comment);

        return genericMapper.map(comment, CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long commentId, CommentDto newCommentDto) throws Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception("Comment not found with id " + commentId));

        comment.setBody(newCommentDto.getBody());
        comment = commentRepository.save(comment);

        return genericMapper.map(comment, CommentDto.class);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) throws Exception {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new Exception("Comment not found with id " + id));
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByCourseSessionId(Long courseSessionId) {
        commentRepository.deleteAllByCourseSessionId(courseSessionId);
    }
}
