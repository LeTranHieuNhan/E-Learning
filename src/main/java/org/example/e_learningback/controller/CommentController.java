package org.example.e_learningback.controller;


import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CommentDto;
import org.example.e_learningback.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/course_session/{courseSessionId}")
    public ResponseEntity<List<CommentDto>> getCommentsByCourseSessionId(@PathVariable Long courseSessionId) throws Exception {
        return new ResponseEntity<>(commentService.findAllCommentsByCourseSessionId(courseSessionId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(commentService.findAllCommentsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return new ResponseEntity<>(commentService.findAllComments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.findCommentById(id), HttpStatus.OK);
    }

    @PostMapping("/course_session/{courseSessionId}/user/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Long courseSessionId,
                                                    @PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(commentService.createComment(commentDto, courseSessionId, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) throws Exception {
        return new ResponseEntity<>(commentService.updateComment(id, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) throws Exception {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/course_session/{courseSessionId}")
    public ResponseEntity<Void> deleteAllCommentsByCourseSessionId(@PathVariable Long courseSessionId) throws Exception {
        commentService.deleteAllCommentsByCourseSessionId(courseSessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
