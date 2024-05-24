package org.example.e_learningback.service.impl;

import lombok.AllArgsConstructor;
import org.example.e_learningback.entity.Status;
import org.example.e_learningback.entity.StudentSession;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.repository.CourseSessionRepository;
import org.example.e_learningback.repository.StudentSessionRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.StudentSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StudentSessionServiceImpl implements StudentSessionService {
    private final StudentSessionRepository studentSessionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository usserRepository;
    private final CourseSessionRepository courseSessionRepository;

    @Override
    @Transactional
    public String saveStudentSession(Long courseId, Long studentId, Long courseSessionId) {
        StudentSession studentSession = new StudentSession();
        studentSession.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found")));
        studentSession.setUser(usserRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found")));
        studentSession.setCourseSession(courseSessionRepository.findById(courseSessionId).orElseThrow(() -> new RuntimeException("CourseSession not found")));
        studentSession.setStatus(Status.WATCHED);

        return studentSession.getStatus().toString();
    }


    @Override
    @Transactional
    public void deleteStudentSession(Long courseId, Long studentId, Long courseSessionId) {

    }

    @Override
    public void getStudentSession(Long courseId, Long studentId, Long courseSessionId) {

    }

    @Override
    public void getAllStudentSession(Long courseId) {

    }
}
