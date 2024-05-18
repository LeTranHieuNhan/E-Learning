package org.example.e_learningback.service;

import org.example.e_learningback.dto.CourseSessionDto;

import java.util.List;

public interface CourseSessionService {
    List<CourseSessionDto> findAllCourseSessions();
    CourseSessionDto findCourseSessionById(Long id);
    List<CourseSessionDto> findAllCourseSessionsByCourseId(Long courseId) throws Exception;
    CourseSessionDto createCourseSession(CourseSessionDto newCourseSessionDto, Long courseId) throws Exception;
    CourseSessionDto updateCourseSession(Long courseSessionId, CourseSessionDto newCourseSessionDto) throws Exception;
    void deleteCourseSession(Long courseSessionId);
}
