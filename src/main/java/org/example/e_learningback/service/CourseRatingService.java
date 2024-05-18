package org.example.e_learningback.service;

import org.example.e_learningback.dto.CourseRatingDto;

import java.util.List;

public interface CourseRatingService {
    List<CourseRatingDto> findAllCourseRatings();
    CourseRatingDto findCourseRatingById(Long id);
    List<CourseRatingDto> findAllCourseRatingsByCourseId(Long courseId) throws Exception;
    CourseRatingDto createCourseRating(CourseRatingDto newCourseRatingDto, Long courseId, Long userId) throws Exception;
    CourseRatingDto updateCourseRating(Long courseRatingId, CourseRatingDto newCourseRatingDto) throws Exception;
    void deleteCourseRating(Long courseRatingId);
}
