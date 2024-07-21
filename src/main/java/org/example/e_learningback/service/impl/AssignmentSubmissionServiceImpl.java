package org.example.e_learningback.service.impl;

import com.amazonaws.services.apigateway.model.Op;
import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.AssignmentSubmissionDto;
import org.example.e_learningback.entity.Assignment;
import org.example.e_learningback.entity.AssignmentSubmission;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.repository.AssignmentRepository;
import org.example.e_learningback.repository.AssignmentSubmissionRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.AssignmentSubmissionService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {
    private final AssignmentSubmissionRepository assignmentSubmissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final GenericMapper genericMapper;
    @Override
    public List<AssignmentSubmissionDto> findAllSubmissions() {
        List<AssignmentSubmission> assignmentSubmissions = assignmentSubmissionRepository.findAll();
        return genericMapper.mapList(assignmentSubmissions, AssignmentSubmissionDto.class);
    }

    @Override
    public List<AssignmentSubmissionDto> findAllSubmissionsByAssignmentId(Long assignmentId) throws Exception {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        if (assignment.isEmpty()) {
            throw new Exception("Assignment does not exists");
        }

        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findAllByAssignment(assignment.get());

        if (submissions.isEmpty()) {
            throw new Exception("No submissions found");
        }
        return genericMapper.mapList(submissions, AssignmentSubmissionDto.class);
    }

    @Override
    public AssignmentSubmissionDto findSubmissionById(Long id) throws Exception {
        Optional<AssignmentSubmission> submission = assignmentSubmissionRepository.findById(id);
        if (submission.isEmpty()) {
            throw new Exception("Submission not found with id " + id);
        }
        return genericMapper.map(submission.get(), AssignmentSubmissionDto.class);
    }

    @Override
    @Transactional
    public AssignmentSubmissionDto createSubmission(AssignmentSubmissionDto assignmentSubmissionDto, Long userId, Long assignmentId) throws Exception {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new Exception("user not found");
        }
        if (assignment.isEmpty()) {
            throw new Exception("assignment not found");
        }

        AssignmentSubmission newSubmission = genericMapper.map(assignmentSubmissionDto, AssignmentSubmission.class);
        AssignmentSubmission submission = new AssignmentSubmission();

        submission.setUser(user.get());
        submission.setFiles(newSubmission.getFiles());
        submission.setAssignment(assignment.get());


        AssignmentSubmission savedSubmission = assignmentSubmissionRepository.save(submission);
        return genericMapper.map(savedSubmission, AssignmentSubmissionDto.class);
    }

    @Override
    @Transactional
    public AssignmentSubmissionDto updateSubmission(Long id, AssignmentSubmissionDto assignmentSubmissionDto) throws Exception {
        AssignmentSubmission submission = assignmentSubmissionRepository.findById(id)
                .orElseThrow(() -> new Exception("Submission not found with id " + id));

        AssignmentSubmission newSubmission = genericMapper.map(assignmentSubmissionDto, AssignmentSubmission.class);

        submission.setFiles(newSubmission.getFiles());

        AssignmentSubmission updatedSubmission = assignmentSubmissionRepository.save(submission);
        return genericMapper.map(updatedSubmission, AssignmentSubmissionDto.class);
    }

    @Override
    @Transactional
    public void deleteSubmission(Long id) throws Exception {
        AssignmentSubmission submission = assignmentSubmissionRepository.findById(id)
                .orElseThrow(() -> new Exception("Submission not found with id " + id));
        assignmentSubmissionRepository.delete(submission);
    }
}
