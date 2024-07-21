package org.example.e_learningback.service;

import org.example.e_learningback.dto.AssignmentSubmissionDto;
import org.example.e_learningback.entity.AssignmentSubmission;

import java.util.List;

public interface AssignmentSubmissionService {
    List<AssignmentSubmissionDto> findAllSubmissions();
    List<AssignmentSubmissionDto> findAllSubmissionsByAssignmentId(Long assignmentId) throws Exception;
    AssignmentSubmissionDto findSubmissionById(Long id) throws Exception;
    AssignmentSubmissionDto createSubmission(AssignmentSubmissionDto assignmentSubmissionDto, Long userId, Long assignmentId) throws Exception;
    AssignmentSubmissionDto updateSubmission(Long id, AssignmentSubmissionDto assignmentSubmissionDto) throws Exception;
    void deleteSubmission(Long id) throws Exception;
}
