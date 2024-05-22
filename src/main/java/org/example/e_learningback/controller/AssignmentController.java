package org.example.e_learningback.controller;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.AssignmentDto;
import org.example.e_learningback.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping()
    public ResponseEntity<List<AssignmentDto>> getAssignments() {
        return new ResponseEntity<>(assignmentService.findAllAssignments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable Long id) {
        return new ResponseEntity<>(assignmentService.findAssignmentById(id), HttpStatus.OK);
    }

    @GetMapping("/course_assignments/{courseId}")
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByCourseId(@PathVariable Long courseId) throws Exception {
        return new ResponseEntity<>(assignmentService.findAllAssignmentsByCourseId(courseId), HttpStatus.OK);
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentDto newAssignment, @PathVariable Long courseId) throws Exception {
        return new ResponseEntity<>(assignmentService.createAssignment(newAssignment, courseId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDto> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto newAssignment) throws Exception {
        return new ResponseEntity<>(assignmentService.updateAssignment(id, newAssignment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
