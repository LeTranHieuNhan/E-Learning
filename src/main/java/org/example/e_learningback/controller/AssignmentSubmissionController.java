package org.example.e_learningback.controller;


import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.AssignmentSubmissionDto;
import org.example.e_learningback.service.AssignmentSubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assignment_submissions")
public class AssignmentSubmissionController {
    private final AssignmentSubmissionService assignmentSubmissionService;

    @GetMapping
    public ResponseEntity<List<AssignmentSubmissionDto>> getAllSubmissions() {
        return new ResponseEntity<>(assignmentSubmissionService.findAllSubmissions(), HttpStatus.OK);
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<AssignmentSubmissionDto>> getSubmissionsByAssignmentId(@PathVariable Long assignmentId) throws Exception {
        return new ResponseEntity<>(assignmentSubmissionService.findAllSubmissionsByAssignmentId(assignmentId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentSubmissionDto> getSubmissionById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(assignmentSubmissionService.findSubmissionById(id), HttpStatus.OK);
    }

    @PostMapping("/assignment/{assignmentId}/user/{userId}")
    public ResponseEntity<AssignmentSubmissionDto> createSubmission(@RequestBody AssignmentSubmissionDto assignmentSubmissionDto,
                                                    @PathVariable Long userId,
                                                    @PathVariable Long assignmentId) throws Exception {
        return new ResponseEntity<>(assignmentSubmissionService.createSubmission(assignmentSubmissionDto, userId, assignmentId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentSubmissionDto> updateSubmission(@PathVariable Long id, @RequestBody AssignmentSubmissionDto assignmentSubmissionDto) throws Exception {
        return new ResponseEntity<>(assignmentSubmissionService.updateSubmission(id, assignmentSubmissionDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) throws Exception {
        assignmentSubmissionService.deleteSubmission(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
