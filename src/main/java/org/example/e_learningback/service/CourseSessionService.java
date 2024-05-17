package org.example.e_learningback.service;

import org.example.e_learningback.dto.CourseSessionDto;

import java.util.List;

public interface CourseSessionService {
    List<CourseSessionDto> findAllCourseSessions();
    CourseSessionDto findCourseSessionById();
    CourseSessionDto createCourseSession(CourseSessionDto newCourseSessionDto, Long courseId);
    CourseSessionDto updateCourseSession(Long courseSessionId, CourseSessionDto newCourseSessionDto);
    void deleteCourseSession(Long courseSessionId);
}
