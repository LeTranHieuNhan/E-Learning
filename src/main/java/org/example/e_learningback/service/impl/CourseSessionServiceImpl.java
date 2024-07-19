package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.e_learningback.dto.CourseDto;
import org.example.e_learningback.dto.CourseSessionDto;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.CourseSession;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.repository.CourseSessionRepository;
import org.example.e_learningback.service.CourseSessionService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j // for logging
public class CourseSessionServiceImpl implements CourseSessionService {

    private final CourseSessionRepository courseSessionRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<CourseSessionDto> findAllCourseSessions() {
        log.info("Fetching all course sessions");
        return courseSessionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseSessionDto findCourseSessionById(Long id) {
        log.info("Fetching course session with ID: {}", id);
        return courseSessionRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Course session does not exist"));
    }

    @Override
    public List<CourseSessionDto> findAllCourseSessionsByCourseId(Long courseId) {
        log.info("Fetching all course sessions for course ID: {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course does not exist"));

        return course.getCourseSessions().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseSessionDto createCourseSession(CourseSessionDto newCourseSessionDto, Long courseId) {
        log.info("Creating new course session for course ID: {}", courseId);

// Fetch the course by ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course does not exist"));

// Create a new CourseSession entity and set its properties manually
        CourseSession courseSession = new CourseSession();
        courseSession.setId(null); // Ensure a new entity is created
        courseSession.setCourse(course);
        courseSession.setTitle(newCourseSessionDto.getTitle());
        courseSession.setVideoUrl(newCourseSessionDto.getVideoUrl());
        courseSession.setThumbnailUrl(newCourseSessionDto.getThumbnailUrl());
// Generate or validate unique session order
        Long sessionOrder = newCourseSessionDto.getSessionOrder();
        if (sessionOrder == null) {
            sessionOrder = generateUniqueSessionOrder(course);
        } else {
            validateUniqueSessionOrder(course, sessionOrder);
        }
        courseSession.setSessionOrder(sessionOrder);

// Save the course session to the repository
        CourseSession savedCourseSession = courseSessionRepository.save(courseSession);

// Convert the saved CourseSession entity back to CourseSessionDto
        return convertToDto(savedCourseSession);
    }

    // Method to generate a unique session order
    private Long generateUniqueSessionOrder(Course course) {
        Long maxOrder = courseSessionRepository.findMaxSessionOrderByCourse(course);
        return (maxOrder == null ? 1 : maxOrder + 1);
    }

    // Method to validate session order
    private void validateUniqueSessionOrder(Course course, Long sessionOrder) {
        boolean exists = courseSessionRepository.existsByCourseAndSessionOrder(course, sessionOrder);
        if (exists) {
            throw new RuntimeException("Session order " + sessionOrder + " already exists for this course.");
        }
    }

    @Override
    @Transactional
    public CourseSessionDto updateCourseSession(Long courseSessionId, CourseSessionDto newCourseSessionDto) {
        log.info("Updating course session with ID: {}", courseSessionId);
        CourseSession courseSession = courseSessionRepository.findById(courseSessionId)
                .orElseThrow(() -> new RuntimeException("Course session not found"));

// Map only the fields that are allowed to change
        courseSession.setTitle(newCourseSessionDto.getTitle());
        courseSession.setSessionOrder(newCourseSessionDto.getSessionOrder());
        courseSession.setVideoUrl(newCourseSessionDto.getVideoUrl());

// No need to save explicitly, as @Transactional will handle it

        return convertToDto(courseSession);
    }

    @Override
    @Transactional
    public void deleteCourseSession(Long courseSessionId) {
        log.info("Deleting course session with ID: {}", courseSessionId);
        if (!courseSessionRepository.existsById(courseSessionId)) {
            throw new RuntimeException("Course session not found");
        }
        courseSessionRepository.deleteById(courseSessionId);
    }

    // Helper method to convert CourseSession to CourseSessionDto
    private CourseSessionDto convertToDto(CourseSession courseSession) {
        CourseSessionDto dto = new CourseSessionDto();
        dto.setId(courseSession.getId());
        dto.setTitle(courseSession.getTitle());
        dto.setSessionOrder(courseSession.getSessionOrder());
        dto.setVideoUrl(courseSession.getVideoUrl());
        dto.setThumbnailUrl(courseSession.getThumbnailUrl());
        if (courseSession.getCourse() != null) {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(courseSession.getCourse().getId());
            courseDto.setTitle(courseSession.getCourse().getTitle());
            dto.setCourse(courseDto);
        }
        return dto;
    }
}