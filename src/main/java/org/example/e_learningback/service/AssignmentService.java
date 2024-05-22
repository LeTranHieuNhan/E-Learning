package org.example.e_learningback.service;

import org.example.e_learningback.dto.AssignmentDto;

import java.util.List;

public interface AssignmentService {
    List<AssignmentDto> findAllAssignments();
    AssignmentDto findAssignmentById (Long id);
    List<AssignmentDto> findAllAssignmentsByCourseId(Long courseId) throws Exception;
    AssignmentDto createAssignment(AssignmentDto newAssignmentDto, Long courseId) throws Exception;
    AssignmentDto updateAssignment(Long id, AssignmentDto newAssignmentDto) throws Exception;
    void deleteAssignment(Long id);
}
