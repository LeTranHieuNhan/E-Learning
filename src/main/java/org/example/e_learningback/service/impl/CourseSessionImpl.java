package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CourseRatingDto;
import org.example.e_learningback.dto.CourseSessionDto;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.CourseRating;
import org.example.e_learningback.entity.CourseSession;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.repository.CourseSessionRepository;
import org.example.e_learningback.service.CourseSessionService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseSessionImpl implements CourseSessionService {

    private final CourseSessionRepository courseSessionRepository;
    private final CourseRepository courseRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<CourseSessionDto> findAllCourseSessions() {
        List<CourseSession> courseSessions = courseSessionRepository.findAll();
        return genericMapper.mapList(courseSessions, CourseSessionDto.class);
    }

    @Override
    public CourseSessionDto findCourseSessionById(Long id) {
        Optional<CourseSession> courseSession = courseSessionRepository.findById(id);

        if (courseSession.isEmpty()) {
            throw new RuntimeException("Course session does not exist");
        }

        return genericMapper.map(courseSession.get(), CourseSessionDto.class);
    }

    @Override
    public List<CourseSessionDto> findAllCourseSessionsByCourseId(Long courseId) throws Exception{
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new Exception("Course does not exist");
        }

        List<CourseSession> courseSessions = courseOptional.get().getCourseSessions();
        List<CourseSessionDto> courseSessionDtos = new ArrayList<>();

        for (CourseSession courseSession : courseSessions) {
            CourseSessionDto courseSessionDto = genericMapper.map(courseSession, CourseSessionDto.class);
            courseSessionDtos.add(courseSessionDto);
        }

        return courseSessionDtos;
    }

    @Override
    @Transactional
    public CourseSessionDto createCourseSession(CourseSessionDto newCourseSessionDto, Long courseId) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new Exception("Course does not exist");
        }

        CourseSession courseSession = genericMapper.map(newCourseSessionDto, CourseSession.class);

        // Set ID to null to ensure a new course session is created
        courseSession.setId(null);
        courseSession.setCourse(courseOptional.get());

        System.out.println("url " + newCourseSessionDto.getVideoUrl());
        System.out.println(courseSession.getVideoUrl());
        CourseSessionDto createdCourseSessionDto = genericMapper.map(courseSessionRepository.save(courseSession), CourseSessionDto.class);

        return createdCourseSessionDto;
    }

    @Override
    @Transactional
    public CourseSessionDto updateCourseSession(Long courseSessionId, CourseSessionDto newCourseSessionDto) throws Exception {
        Optional<CourseSession> optionalCourseSession = courseSessionRepository.findById(courseSessionId);

        if (optionalCourseSession.isEmpty()) {
            throw new Exception("Course session not found");
        }

        CourseSession courseSession = optionalCourseSession.get();
        courseSession.setTitle(newCourseSessionDto.getTitle());
        courseSession.setSessionOrder(newCourseSessionDto.getSessionOrder());
        courseSession.setVideoUrl(newCourseSessionDto.getVideoUrl());

        CourseSessionDto updatedCourseSessionDto = genericMapper.map(courseSession, CourseSessionDto.class);

        return updatedCourseSessionDto;
    }

    @Override
    @Transactional
    public void deleteCourseSession(Long courseSessionId) {
        if (courseSessionRepository.existsById(courseSessionId)) {

            courseSessionRepository.deleteById(courseSessionId);

        } else {
            throw new RuntimeException("Course session not found");
        }
    }
}
