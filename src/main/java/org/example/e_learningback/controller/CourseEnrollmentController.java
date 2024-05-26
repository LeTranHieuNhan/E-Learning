package org.example.e_learningback.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.e_learningback.dto.CourseDto;
import org.example.e_learningback.dto.CourseEnrollmentDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.service.CourseEnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course_enrollments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseEnrollmentController {

    CourseEnrollmentService courseEnrollmentService;

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<List<UserDto>> getAllEnrolledUsersByCourseId(@PathVariable Long courseId) throws Exception {
        List<UserDto> users = courseEnrollmentService.findAllEnrolledUsersByCourseId(courseId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<CourseDto>> getAllEnrolledUsersByUserId(@PathVariable Long userId) throws Exception {
        List<CourseDto> courses = courseEnrollmentService.findAllEnrolledCoursesByUserId(userId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/courses/grades/{courseId}")
    public ResponseEntity<List<CourseEnrollmentDto>> getAllGradesByCourseId(@PathVariable Long courseId) {
        try {
            List<CourseEnrollmentDto> grades = courseEnrollmentService.findAllGradesByCourseId(courseId);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/users/grades/{userId}")
    public ResponseEntity<List<CourseEnrollmentDto>> getAllGradesByUserId(@PathVariable Long userId) {
        try {
            List<CourseEnrollmentDto> grades = courseEnrollmentService.findAllGradesByUserId(userId);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/courses/{courseId}/users/{userId}")
    public ResponseEntity<CourseEnrollmentDto> getGrade(@PathVariable Long courseId, @PathVariable Long userId) {
        try {
            CourseEnrollmentDto grade = courseEnrollmentService.findGrade(courseId, userId);
            return ResponseEntity.ok(grade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/courses/{courseId}/users/{userId}")
    public ResponseEntity<CourseEnrollmentDto> createCourse(@RequestParam double grade, @PathVariable Long courseId, @PathVariable Long userId) throws Exception {
        try {
            CourseEnrollmentDto createdGrade = courseEnrollmentService.createGrade(courseId, userId, grade);
            return ResponseEntity.ok(createdGrade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/courses/{courseId}/users/{userId}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long courseId, @PathVariable Long userId) {
        try {
            courseEnrollmentService.deleteGrade(courseId, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
