package org.example.e_learningback.controller;


import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.ReplyCommentDto;
import org.example.e_learningback.service.ReplyCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reply_comments")
@RequiredArgsConstructor
public class ReplyCommentController {
    private final ReplyCommentService replyCommentService;

    @GetMapping("/{id}")
    public ResponseEntity<ReplyCommentDto> getReplyCommentById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(replyCommentService.findReplyCommentById(id), HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<ReplyCommentDto>> getAllReplyCommentsByCommentId(@PathVariable Long commentId) throws Exception {
        return new ResponseEntity<>(replyCommentService.findAllReplyCommentsByCommentId(commentId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReplyCommentDto>> getAllReplyCommentsByUserId(@PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(replyCommentService.findAllReplyCommentsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReplyCommentDto>> getAllReplyComments() {
        return new ResponseEntity<>(replyCommentService.findAllReplyComments(), HttpStatus.OK);
    }

    @PostMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<ReplyCommentDto> createReplyComment(@RequestBody ReplyCommentDto replyCommentDto,
                                                              @PathVariable Long commentId,
                                                              @PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(replyCommentService.createReplyComment(replyCommentDto, commentId, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReplyCommentDto> updateReplyComment(@PathVariable Long id, @RequestBody ReplyCommentDto replyCommentDto) throws Exception {
        return new ResponseEntity<>(replyCommentService.updateReplyComment(id, replyCommentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReplyCommentById(@PathVariable Long id) throws Exception {
        replyCommentService.deleteReplyCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteAllReplyCommentsByCommentId(@PathVariable Long commentId) throws Exception {
        replyCommentService.deleteAllReplyCommentsByCommentId(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
