package org.example.e_learningback.controller;


import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.AssignmentGradeDto;
import org.example.e_learningback.service.AssignmentGradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assignment_grade")
public class AssignmentGradeController {
    private final AssignmentGradeService assignmentGradeService;

    @GetMapping()
    public ResponseEntity<List<AssignmentGradeDto>> getAllGrades() {
        return new ResponseEntity<>(assignmentGradeService.findAllGrades(), HttpStatus.OK);
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<AssignmentGradeDto> getGradesByAssignmentId(@PathVariable Long assignmentId) throws Exception {
        return new ResponseEntity<>(assignmentGradeService.findGradeByAssignmentId(assignmentId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentGradeDto> getGradeById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(assignmentGradeService.findGradeById(id), HttpStatus.OK);
    }

    @PostMapping("/assignment/{assignmentId}")
    public ResponseEntity<AssignmentGradeDto> createGrade(@PathVariable Long assignmentId,
                                                          @RequestBody AssignmentGradeDto assignmentGradeDto) throws Exception {
        return new ResponseEntity<>(assignmentGradeService.createGrade(assignmentId, assignmentGradeDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentGradeDto> updateGrade(@PathVariable Long id, @RequestBody AssignmentGradeDto assignmentGradeDto) throws Exception {
        return new ResponseEntity<>(assignmentGradeService.updateGrade(id, assignmentGradeDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) throws Exception {
        assignmentGradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
