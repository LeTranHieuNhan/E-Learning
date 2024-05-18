package org.example.e_learningback.controller;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CourseRatingDto;
import org.example.e_learningback.service.CourseRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course_ratings")
public class CourseRatingController {
    private final CourseRatingService courseRatingService;

    @GetMapping()
    public ResponseEntity<List<CourseRatingDto>> getCourseRatings() {
        return new ResponseEntity<>(courseRatingService.findAllCourseRatings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseRatingDto> getCourseRatingById(@PathVariable Long id) {
        return new ResponseEntity<>(courseRatingService.findCourseRatingById(id), HttpStatus.OK);
    }

    @GetMapping("/course_ratings/{courseId}")
    public ResponseEntity<List<CourseRatingDto>> getCourseRatingsByCourseId(@PathVariable Long courseId) throws Exception {
        return new ResponseEntity<>(courseRatingService.findAllCourseRatingsByCourseId(courseId), HttpStatus.OK);
    }

    @PostMapping("/{courseId}/{userId}")
    public ResponseEntity<CourseRatingDto> createCourseRating(@RequestBody CourseRatingDto newCourseRating, @PathVariable Long courseId, @PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(courseRatingService.createCourseRating(newCourseRating, courseId, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseRatingDto> updateCourseRating(@PathVariable Long id, @RequestBody CourseRatingDto newCourseRating) throws Exception {
        return new ResponseEntity<>(courseRatingService.updateCourseRating(id, newCourseRating), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseRating(@PathVariable Long id) {
        courseRatingService.deleteCourseRating(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
