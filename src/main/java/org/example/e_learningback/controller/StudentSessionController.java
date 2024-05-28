package org.example.e_learningback.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.e_learningback.dto.StudentSessionDto;
import org.example.e_learningback.service.StudentSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student_sessions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentSessionController {

    final StudentSessionService studentSessionService;

    @PutMapping
    public ResponseEntity<String> changeStudentSessionStatus(
            @RequestParam Long courseId,
            @RequestParam Long studentId,
            @RequestParam Long courseSessionId
    ) {
        String result = studentSessionService.changeStudentSessionStatus(courseId, studentId, courseSessionId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStudentSession(
            @RequestParam Long courseId,
            @RequestParam Long studentId,
            @RequestParam Long courseSessionId
    ) {
        studentSessionService.deleteStudentSession(courseId, studentId, courseSessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<StudentSessionDto> getStudentSession(
            @RequestParam Long courseId,
            @RequestParam Long studentId,
            @RequestParam Long courseSessionId
    ) {
        StudentSessionDto studentSessionDto = studentSessionService.getStudentSession(courseId, studentId, courseSessionId);
        return ResponseEntity.ok(studentSessionDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentSessionDto>> getAllStudentSessions(
            @RequestParam Long courseId,
            @RequestParam Long studentId
    ) {
        List<StudentSessionDto> studentSessions = studentSessionService.getAllStudentSessionByCourseIdAndStudentID(courseId, studentId);
        return ResponseEntity.ok(studentSessions);
    }
}
