package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.*;
import org.example.e_learningback.entity.*;
import org.example.e_learningback.repository.*;
import org.example.e_learningback.service.*;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final StudentSessionRepository studentSessionRepository;
    private final GenericMapper genericMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllEnrolledUsersByCourseId(Long courseId) {
        Course course = getCourseById(courseId);
        return course.getCourseEnrollments().stream()
                .map(enrollment -> genericMapper.map(enrollment.getUser(), UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void enrollUserToCourse(Long courseId, Long userId) {
        User user = getUserById(userId);
        Course course = getCourseById(courseId);

        // Check if the user is already enrolled in the course
        List<CourseEnrollment> existingEnrollments = courseEnrollmentRepository.findByUserAndCourse(user, course);
        if (!existingEnrollments.isEmpty() || course.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User is already enrolled in the course or is the course owner.");
        }

        System.out.println("break point");
        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        System.out.println("break point 2");
        courseEnrollmentRepository.save(enrollment);
        System.out.println("break point 3");
        initializeAllStudentSessions(courseId, userId);
    }

    @Transactional
    protected void initializeAllStudentSessions(Long courseId, Long userId) {
        Course course = getCourseById(courseId);
        User user = getUserById(userId);
        course.getCourseSessions().forEach(courseSession -> initializeStudentSession(courseSession, user));
    }

    @Transactional
    public void initializeStudentSession(CourseSession courseSession, User user) {
        StudentSession studentSession = new StudentSession();
        Course course = courseSession.getCourse();
        studentSession.setCourse(course);
        studentSession.setUser(user);
        studentSession.setStatus(Status.UNWATCHED);
        studentSession.setCourseSession(courseSession);
        studentSessionRepository.save(studentSession);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> findAllEnrolledCoursesByUserId(Long userId) {
        User user = getUserById(userId);
        return user.getCourseEnrollments().stream()
                .map(enrollment -> genericMapper.map(enrollment.getCourse(), CourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseEnrollmentDto> findAllGradesByCourseId(Long courseId) {
        Course course = getCourseById(courseId);
        return course.getCourseEnrollments().stream()
                .map(enrollment -> genericMapper.map(enrollment, CourseEnrollmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CourseEnrollmentDto> findAllGradesByUserId(Long userId) {
        User user = getUserById(userId);
        return user.getCourseEnrollments().stream()
                .map(enrollment -> genericMapper.map(enrollment, CourseEnrollmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseEnrollmentDto findGrade(Long courseId, Long userId) {
        System.out.println(courseId + " " + userId);
        CourseEnrollment enrollment = getCourseEnrollmentByCourseIdAndUserId(courseId, userId);
            return genericMapper.map(enrollment, CourseEnrollmentDto.class);
    }

    @Override
    @Transactional
    public CourseEnrollmentDto createGrade(Long courseId, Long userId, double grade) {
        User user = getUserById(userId);
        Course course = getCourseById(courseId);
        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setCourseGrade(grade);
        courseEnrollmentRepository.save(enrollment);
        return genericMapper.map(enrollment, CourseEnrollmentDto.class);
    }

    @Override
    @Transactional
    public void deleteGrade(Long courseId, Long userId) {
        CourseEnrollment enrollment = getCourseEnrollmentByCourseIdAndUserId(courseId, userId);
        enrollment.setCourseGrade(-1);
        courseEnrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public CourseEnrollmentDto updateGrade(Long courseId, Long userId, double grade) {
        CourseEnrollment enrollment = getCourseEnrollmentByCourseIdAndUserId(courseId, userId);
        enrollment.setCourseGrade(grade);
        courseEnrollmentRepository.save(enrollment);
        return genericMapper.map(enrollment, CourseEnrollmentDto.class);
    }

    // Helper methods

    private Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course does not exist with ID: " + courseId));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist with ID: " + userId));
    }

    private CourseEnrollment getCourseEnrollmentByCourseIdAndUserId(Long courseId, Long userId) {
        return courseEnrollmentRepository.findByCourseIdAndUserId(courseId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Course enrollment does not exist for courseId: " + courseId + " and userId: " + userId));
    }
}
