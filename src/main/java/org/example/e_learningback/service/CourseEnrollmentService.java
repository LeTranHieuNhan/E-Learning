package org.example.e_learningback.service;

import org.example.e_learningback.dto.CourseDto;
import org.example.e_learningback.dto.CourseEnrollmentDto;
import org.example.e_learningback.dto.UserDto;

import java.util.List;

public interface CourseEnrollmentService {
    List<UserDto> findAllEnrolledUsersByCourseId(Long courseId) throws Exception;
    List<CourseDto> findAllEnrolledCoursesByUserId(Long userId) throws Exception;
    List<CourseEnrollmentDto> findAllGradesByCourseId(Long courseId) throws Exception;
    List<CourseEnrollmentDto> findAllGradesByUserId(Long userId) throws Exception;
    CourseEnrollmentDto findGrade(Long courseId, Long userId) throws Exception;
    CourseEnrollmentDto createGrade(Long courseId, Long userId, double grade) throws Exception;
    void deleteGrade(Long courseId, Long userId) throws Exception;
    CourseEnrollmentDto updateGrade(Long courseId, Long userId, double grade) throws Exception;
}
