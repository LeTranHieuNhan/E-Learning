package org.example.e_learningback.service;

import org.example.e_learningback.dto.AssignmentGradeDto;

import java.util.List;

public interface AssignmentGradeService {
    List<AssignmentGradeDto> findAllGrades();
    AssignmentGradeDto findGradeById(Long id) throws Exception;
    AssignmentGradeDto findGradeByAssignmentId(Long assignmentId) throws Exception;
    AssignmentGradeDto createGrade(Long assignmentId, AssignmentGradeDto assignmentGradeDto) throws Exception;
    AssignmentGradeDto updateGrade(Long id, AssignmentGradeDto assignmentGradeDto) throws Exception;
    void deleteGrade(Long id) throws Exception;
}
