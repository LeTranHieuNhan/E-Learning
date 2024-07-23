package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.AssignmentGradeDto;
import org.example.e_learningback.entity.Assignment;
import org.example.e_learningback.entity.AssignmentGrade;
import org.example.e_learningback.entity.AssignmentSubmission;
import org.example.e_learningback.repository.AssignmentGradeRepository;
import org.example.e_learningback.repository.AssignmentRepository;
import org.example.e_learningback.service.AssignmentGradeService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AssignmentGradeServiceImpl implements AssignmentGradeService {

    private final AssignmentGradeRepository assignmentGradeRepository;
    private final AssignmentRepository assignmentRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<AssignmentGradeDto> findAllGrades() {
        List<AssignmentGrade> grades = assignmentGradeRepository.findAll();
        return genericMapper.mapList(grades, AssignmentGradeDto.class);
    }

    @Override
    public AssignmentGradeDto findGradeById(Long id) throws Exception {
        Optional<AssignmentGrade> assignmentGrade = assignmentGradeRepository.findById(id);

        if(assignmentGrade.isEmpty()) {
            throw new Exception("Grade not found");
        }

        return genericMapper.map(assignmentGrade.get(), AssignmentGradeDto.class);
    }

    @Override
    public AssignmentGradeDto findGradeByAssignmentId(Long assignmentId) throws Exception {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        if(assignment.isEmpty()) {
            throw new Exception("Assignment not found");
        }

        AssignmentGrade assignmentGrade = assignmentGradeRepository.findByAssignment(assignment.get());
        return genericMapper.map(assignmentGrade, AssignmentGradeDto.class);
    }

    @Override
    @Transactional
    public AssignmentGradeDto createGrade(Long assignmentId, AssignmentGradeDto assignmentGradeDto) throws Exception {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        if(assignment.isEmpty()) {
            throw new Exception("Assignment not found");
        }

        AssignmentGrade assignmentGrade = new AssignmentGrade();
        assignmentGrade.setAssignment(assignment.get());
        assignmentGrade.setFeedback(assignmentGradeDto.getFeedback());
        assignmentGrade.setGrade(assignmentGradeDto.getGrade());

        AssignmentGrade savedGrade = assignmentGradeRepository.save(assignmentGrade);
        return genericMapper.map(savedGrade, AssignmentGradeDto.class);
    }

    @Override
    @Transactional
    public AssignmentGradeDto updateGrade(Long id, AssignmentGradeDto assignmentGradeDto) throws Exception {
        Optional<AssignmentGrade> assignmentGrade = assignmentGradeRepository.findById(id);
        if(assignmentGrade.isEmpty()) {
            throw new Exception("Grade not found");
        }

        AssignmentGrade updatedGrade = assignmentGrade.get();
        updatedGrade.setFeedback(assignmentGradeDto.getFeedback());
        updatedGrade.setGrade(assignmentGradeDto.getGrade());

        AssignmentGrade savedGrade = assignmentGradeRepository.save(updatedGrade);
        return genericMapper.map(savedGrade, AssignmentGradeDto.class);
    }

    @Override
    @Transactional
    public void deleteGrade(Long id) throws Exception {
        AssignmentGrade assignmentGrade = assignmentGradeRepository.findById(id)
                .orElseThrow(() -> new Exception("Grade not found with id " + id));
        assignmentGradeRepository.delete(assignmentGrade);
    }
}
