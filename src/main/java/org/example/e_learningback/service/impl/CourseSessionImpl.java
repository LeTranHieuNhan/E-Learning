package org.example.e_learningback.service.impl;

import org.example.e_learningback.dto.CourseSessionDto;
import org.example.e_learningback.service.CourseSessionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CourseSessionImpl implements CourseSessionService {
    @Override
    public List<CourseSessionDto> findAllCourseSessions() {
        return null;
    }

    @Override
    public CourseSessionDto findCourseSessionById() {
        return null;
    }

    @Override
    @Transactional
    public CourseSessionDto createCourseSession(CourseSessionDto newCourseSessionDto, Long courseId) {
        return null;
    }

    @Override
    @Transactional
    public CourseSessionDto updateCourseSession(Long courseSessionId, CourseSessionDto newCourseSessionDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteCourseSession(Long courseSessionId) {

    }
}
