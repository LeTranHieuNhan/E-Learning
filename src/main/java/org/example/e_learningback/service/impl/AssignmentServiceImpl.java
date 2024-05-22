package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.AssignmentDto;
import org.example.e_learningback.dto.CourseDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

        for (Assignment assignment : assignments) {
            AssignmentDto assignmentDto = genericMapper.map(assignment, AssignmentDto.class);
            assignmentDtos.add(assignmentDto);
        }


        return assignmentDtos;
    }

    @Override
    @Transactional
    public AssignmentDto createAssignment(AssignmentDto newAssignmentDto, Long courseId) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new Exception("Course does not exist");
        }

        Assignment assignment = genericMapper.map(newAssignmentDto, Assignment.class);
        assignment.setId(null);  // Ensure a new entity is created
        assignment.setUploaded_at(new Date());
        assignment.setUpdated_at(null);

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return genericMapper.map(savedAssignment, AssignmentDto.class);
    }

    @Override
    @Transactional
    public AssignmentDto updateAssignment(Long id, AssignmentDto newAssignmentDto) throws Exception {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(id);

        if (assignmentOptional.isEmpty()) {
            throw new Exception("Assignment not found");
        }

        Assignment assignment = assignmentOptional.get();
        assignment.setName(newAssignmentDto.getName());
        assignment.setUploaded_at(newAssignmentDto.getUploaded_at());
        assignment.setUpdated_at(newAssignmentDto.getUpdated_at());
        assignment.setDeadline(newAssignmentDto.getDeadline());

        Assignment updatedAssignment = assignmentRepository.save(assignment);
        return genericMapper.map(updatedAssignment, AssignmentDto.class);
    }

    @Override
    @Transactional
    public void deleteAssignment(Long id) {
        if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Assignment not found");
        }
    }
}
