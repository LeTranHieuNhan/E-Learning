package org.example.e_learningback.service.impl;

import lombok.AllArgsConstructor;
import org.example.e_learningback.dto.StudentSessionDto;
import org.example.e_learningback.entity.*;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.repository.CourseSessionRepository;
import org.example.e_learningback.repository.StudentSessionRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.StudentSessionService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Transactional
    @Override
    public String changeStudentSessionStatus(Long courseId, Long studentId, Long courseSessionId) {
        User user = getUserById(studentId);
        Course course = getCourseById(courseId);
        CourseSession courseSession = courseSessionRepository.findById(courseSessionId)
                .orElseThrow(() -> new RuntimeException("Course session not found"));
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
                .orElseThrow(() -> new RuntimeException("Student session not found"));
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

        return studentSessionLists.map(studentSessions -> genericMapper.mapList(studentSessions, StudentSessionDto.class)).orElse(null);
    }
}
