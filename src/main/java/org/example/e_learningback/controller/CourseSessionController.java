package org.example.e_learningback.controller;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CourseSessionDto;
import org.example.e_learningback.service.CourseSessionService;
import org.example.e_learningback.service.impl.StorageServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course_sessions")
public class CourseSessionController {
    private final CourseSessionService courseSessionService;
    private  final StorageServiceImpl storageService;

    @GetMapping()
    public ResponseEntity<List<CourseSessionDto>> getCourseSessions() {
        return new ResponseEntity<>(courseSessionService.findAllCourseSessions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseSessionDto> getCourseSessionById(@PathVariable Long id) {
        return new ResponseEntity<>(courseSessionService.findCourseSessionById(id), HttpStatus.OK);
    }

    @GetMapping("/course_sessions/{courseId}")
    public ResponseEntity<List<CourseSessionDto>> getCourseSessionsByCourseId(@PathVariable Long courseId) throws Exception {
        return new ResponseEntity<>(courseSessionService.findAllCourseSessionsByCourseId(courseId), HttpStatus.OK);
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<CourseSessionDto> createCourseSession(@RequestBody CourseSessionDto newCourseSession, @PathVariable Long courseId ) throws Exception {
        return new ResponseEntity<>(courseSessionService.createCourseSession(newCourseSession, courseId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseSessionDto> updateCourseSession(@PathVariable Long id, @RequestBody CourseSessionDto newCourseSession) throws Exception {
        return new ResponseEntity<>(courseSessionService.updateCourseSession(id, newCourseSession), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseSession(@PathVariable Long id) {
        courseSessionService.deleteCourseSession(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
