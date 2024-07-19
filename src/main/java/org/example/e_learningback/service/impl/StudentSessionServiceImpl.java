package org.example.e_learningback.service.impl;

import lombok.AllArgsConstructor;
import org.example.e_learningback.dto.CourseSessionDto;
import org.example.e_learningback.dto.StudentSessionDto;
import org.example.e_learningback.entity.*;
import org.example.e_learningback.exception.CourseNotFoundException;
import org.example.e_learningback.exception.CourseSessionNotFoundException;
import org.example.e_learningback.exception.StudentSessionNotFoundException;
import org.example.e_learningback.exception.UserNotFoundException;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.repository.CourseSessionRepository;
import org.example.e_learningback.repository.StudentSessionRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.StudentSessionService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentSessionServiceImpl implements StudentSessionService {
    private final StudentSessionRepository studentSessionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final GenericMapper genericMapper;

    private User getUserById(Long studentId) {
        return userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + studentId));
    }

    private Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
    }

    @Transactional
    @Override
    public String changeStudentSessionStatus(Long courseId, Long studentId, Long courseSessionId) {
        User user = getUserById(studentId);
        Course course = getCourseById(courseId);
        CourseSession courseSession = courseSessionRepository.findById(courseSessionId)
                .orElseThrow(() -> new CourseSessionNotFoundException("Course session not found with ID: " + courseSessionId));
        StudentSession studentSession = studentSessionRepository.findByCourseAndUserAndCourseSession(course, user, courseSession);
        studentSession.setStatus(Status.WATCHED);

        return "Status changed";
    }

    @Transactional
    @Override
    public void deleteStudentSession(Long courseId, Long studentId, Long courseSessionId) {
        User user = getUserById(studentId);
        Course course = getCourseById(courseId);
        StudentSession studentSession = studentSessionRepository.findByCourseAndUser(course, user)
                .orElseThrow(() -> new StudentSessionNotFoundException("Student session not found with ID: " + courseSessionId));
        studentSessionRepository.delete(studentSession);
    }

    @Override
    public StudentSessionDto getStudentSession(Long courseId, Long studentId, Long courseSessionId) {
        User user = getUserById(studentId);
        Course course = getCourseById(courseId);
        Optional<StudentSession> studentSession = studentSessionRepository.findByCourseAndUser(course, user);
        return studentSession.map(session -> genericMapper.map(session, StudentSessionDto.class)).orElse(null);
    }

    @Override
    public List<StudentSessionDto> getAllStudentSessionByCourseIdAndStudentID(Long courseId, Long studentID) {
        User user = getUserById(studentID);
        Course course = getCourseById(courseId);
        Optional<List<StudentSession>> studentSessionLists = studentSessionRepository.findAllByCourseAndUser(course, user);

        if (studentSessionLists.isPresent()) {
            List<StudentSessionDto> studentSessionDtos = new ArrayList<>();
            for (StudentSession studentSession : studentSessionLists.get()) {
                CourseSessionDto courseSessionDto = new CourseSessionDto();
                courseSessionDto.setVideoUrl(studentSession.getCourseSession().getVideoUrl());
                courseSessionDto.setTitle(studentSession.getCourseSession().getTitle());
                courseSessionDto.setSessionOrder(studentSession.getCourseSession().getSessionOrder());
                courseSessionDto.setId(studentSession.getCourseSession().getId());
                StudentSessionDto studentSessionDto = new StudentSessionDto();
                studentSessionDto.setId(studentSession.getId());
                studentSessionDto.setStatus(studentSession.getStatus());
                studentSessionDto.setCourseSession(courseSessionDto);
                studentSessionDtos.add(studentSessionDto);
            }
            return studentSessionDtos;
        } else {
            return new ArrayList<>();
        }
    }
}
