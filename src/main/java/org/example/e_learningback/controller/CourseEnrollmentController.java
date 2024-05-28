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

    final CourseEnrollmentService courseEnrollmentService;

    @GetMapping("/courses/users")
    public ResponseEntity<List<UserDto>> getAllEnrolledUsersByCourseId(@RequestParam Long courseId) throws Exception {
        List<UserDto> users = courseEnrollmentService.findAllEnrolledUsersByCourseId(courseId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/courses/users/enroll")
    public ResponseEntity<Void> enrollUserToCourse(
            @RequestParam Long courseId,
            @RequestParam Long userId
    ) throws Exception {
        courseEnrollmentService.enrollUserToCourse(courseId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/courses")
    public ResponseEntity<List<CourseDto>> getAllEnrolledCoursesByUserId(@RequestParam Long userId) throws Exception {
        List<CourseDto> courses = courseEnrollmentService.findAllEnrolledCoursesByUserId(userId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/courses/grades")
        public ResponseEntity<List<CourseEnrollmentDto>> getAllGradesByCourseId(@RequestParam Long courseId) {
        try {
            List<CourseEnrollmentDto> grades = courseEnrollmentService.findAllGradesByCourseId(courseId);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/users/grades")
    public ResponseEntity<List<CourseEnrollmentDto>> getAllGradesByUserId(@RequestParam Long userId) {
        try {
            List<CourseEnrollmentDto> grades = courseEnrollmentService.findAllGradesByUserId(userId);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/courses/users/grade")
    public ResponseEntity<CourseEnrollmentDto> getGrade(
            @RequestParam Long courseId,
            @RequestParam Long userId
    ) {
        try {
            CourseEnrollmentDto grade = courseEnrollmentService.findGrade(courseId, userId);
            return ResponseEntity.ok(grade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/courses/users/grade")
    public ResponseEntity<CourseEnrollmentDto> createGrade(
            @RequestParam double grade,
            @RequestParam Long courseId,
            @RequestParam Long userId
    ) throws Exception {
        try {
            CourseEnrollmentDto createdGrade = courseEnrollmentService.createGrade(courseId, userId, grade);
            return ResponseEntity.ok(createdGrade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PutMapping("/courses/users/grade")
    public ResponseEntity<Void> updateGrade (
            @RequestParam double grade,
                @RequestParam Long courseId,
            @RequestParam Long userId
    ) {
        try {
            System.out.println("updateGrade");
            courseEnrollmentService.updateGrade(courseId, userId, grade);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/courses/users/grade")
    public ResponseEntity<Void> deleteGrade(
            @RequestParam Long courseId,
            @RequestParam Long userId
    ) {
        try {
            courseEnrollmentService.deleteGrade(courseId, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
