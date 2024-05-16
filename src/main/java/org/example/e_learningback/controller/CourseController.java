package org.example.e_learningback.controller;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CourseDto;
import org.example.e_learningback.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.findAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        CourseDto course = courseService.findCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping("/{userID}/{categoryID}")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto, @PathVariable Long userID, @PathVariable Long categoryID) {
        CourseDto course = courseService.createCourse(courseDto, userID, categoryID);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        CourseDto course = courseService.updateCourse(id, courseDto);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public List<CourseDto> searchPosts(@RequestParam String keyword) {
        return courseService.searchCourses(keyword);
    }
}
