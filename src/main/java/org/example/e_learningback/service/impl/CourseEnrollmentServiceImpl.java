package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CourseDto;
import org.example.e_learningback.dto.CourseEnrollmentDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.CourseEnrollment;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.repository.CourseEnrollmentRepository;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.CourseEnrollmentService;
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
    private final GenericMapper genericMapper;
    @Override
    public List<UserDto> findAllEnrolledUsersByCourseId(Long courseId) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new Exception("Course does not exist");
        }

        Course course = courseOptional.get();
        List<CourseEnrollment> enrollments = course.getCourseEnrollments();
        List<UserDto> enrolledUsers = enrollments.stream()
                .map(enrollment -> genericMapper.map(enrollment.getUser(), UserDto.class))
                .collect(Collectors.toList());

        return enrolledUsers;
    }

    @Override
    public List<CourseDto> findAllEnrolledCoursesByUserId(Long userId) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new Exception("User does not exist");
        }

        User user = userOptional.get();
        List<CourseEnrollment> enrollments = user.getCourseEnrollments();
        List<CourseDto> enrolledCourses = enrollments.stream()
                .map(enrollment -> genericMapper.map(enrollment.getCourse(), CourseDto.class))
                .collect(Collectors.toList());

        return enrolledCourses;
    }

    @Override
    public List<CourseEnrollmentDto> findAllGradesByCourseId(Long courseId) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new Exception("Course does not exist");
        }

        Course course = courseOptional.get();
        List<CourseEnrollment> enrollments = course.getCourseEnrollments();
        List<CourseEnrollmentDto> grades = enrollments.stream()
                .map(enrollment -> genericMapper.map(enrollment, CourseEnrollmentDto.class))
                .collect(Collectors.toList());

        return grades;
    }

    @Override
    public List<CourseEnrollmentDto> findAllGradesByUserId(Long userId) throws Exception {
        Optional<User> userOptional  = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new Exception("User does not exist");
        }

        User user = userOptional.get();
        List<CourseEnrollment> enrollments = user.getCourseEnrollments();
        List<CourseEnrollmentDto> grades = enrollments.stream()
                .map(enrollment -> genericMapper.map(enrollment, CourseEnrollmentDto.class))
                .collect(Collectors.toList());

        return grades;
    }

    @Override
    public CourseEnrollmentDto findGrade(Long courseId, Long userId) throws Exception {
        Optional<CourseEnrollment> courseEnrollmentOptional = courseEnrollmentRepository.findByCourseIdAndUserId(courseId, userId);

        if (courseEnrollmentOptional.isEmpty()) {
            throw new Exception("Course enrollment does not exist");
        }

        CourseEnrollment courseEnrollment = courseEnrollmentOptional.get();

        return genericMapper.map(courseEnrollment, CourseEnrollmentDto.class);
    }

    @Override
    @Transactional
    public CourseEnrollmentDto createGrade(Long courseId, Long userId, double grade) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (userOptional.isEmpty()) {
            throw new Exception("User does not exist");
        }

        if (courseOptional.isEmpty()) {
            throw new Exception("Course does not exist");
        }

        User user = userOptional.get();
        Course course = courseOptional.get();

        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setCourseGrade(grade);

        courseEnrollmentRepository.save(enrollment);

        return genericMapper.map(enrollment, CourseEnrollmentDto.class);
    }

    @Override
    @Transactional
    public void deleteGrade(Long courseId, Long userId) throws Exception {
        Optional<CourseEnrollment> courseEnrollmentOptional = courseEnrollmentRepository.findByCourseIdAndUserId(courseId, userId);

        if (courseEnrollmentOptional.isEmpty()) {
            throw new Exception("Course enrollment does not exist");
        }


        courseEnrollmentRepository.delete(courseEnrollmentOptional.get());
    }

    @Override
    @Transactional
    public CourseEnrollmentDto updateGrade(Long courseId, Long userId, double grade) throws Exception{
        Optional<CourseEnrollment> courseEnrollmentOptional = courseEnrollmentRepository.findByCourseIdAndUserId(courseId, userId);

        if (courseEnrollmentOptional.isEmpty()) {
            throw new Exception("Course enrollment does not exist");
        }

        CourseEnrollment courseEnrollment = courseEnrollmentOptional.get();
        courseEnrollment.setCourseGrade(grade);
        courseEnrollmentRepository.save(courseEnrollment);

        return genericMapper.map(courseEnrollment, CourseEnrollmentDto.class);
    }
}
