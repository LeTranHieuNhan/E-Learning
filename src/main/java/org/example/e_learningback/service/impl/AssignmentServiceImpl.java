package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.AssignmentDto;
import org.example.e_learningback.dto.CourseRatingDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.Assignment;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.CourseRating;
import org.example.e_learningback.repository.AssignmentRepository;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.service.AssignmentService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final GenericMapper genericMapper;
    @Override
    public List<AssignmentDto> findAllAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return genericMapper.mapList(assignments, AssignmentDto.class);
    }

    @Override
    public AssignmentDto findAssignmentById(Long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);

        if (!assignment.isPresent()) {
            throw new RuntimeException("Assignment does not exist");
        }

        return genericMapper.map(assignment.get(), AssignmentDto.class);
    }

    @Override
    public List<AssignmentDto> findAllAssignmentsByCourseId(Long courseId) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new Exception("Course does not exist");
        }

        List<Assignment> assignments = courseOptional.get().getAssignments();
        List<AssignmentDto> assignmentDtos = new ArrayList<>();




        return assignmentDtos;
    }

    @Override
    public AssignmentDto createAssignment(AssignmentDto newAssignmentDto, Long courseId) throws Exception {
        return null;
    }

    @Override
    public AssignmentDto updateAssignment(Long id, AssignmentDto newAssignmentDto) throws Exception {
        return null;
    }

    @Override
    public void deleteAssignment(Long id) {

    }
}
