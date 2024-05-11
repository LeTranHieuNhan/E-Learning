package org.example.e_learningback.service;



import org.example.e_learningback.dto.CourseDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> findAllCourses();
    CourseDto findCourseById(Long id);

    CourseDto createCourse(CourseDto newCourseDTO, Long userID, Long categoryID);

    void deleteCourse(Long id);

    CourseDto updateCourse(Long id, CourseDto newCourseDTO);
}
