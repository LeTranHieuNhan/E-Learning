package org.example.e_learningback.service;

import org.example.e_learningback.dto.StudentSessionDto;

import java.util.List;

public interface StudentSessionService {

    String changeStudentSessionStatus(Long courseId, Long studentId, Long courseSessionId);
    void deleteStudentSession(Long courseId, Long studentId, Long courseSessionId);
    StudentSessionDto getStudentSession(Long courseId, Long studentId, Long courseSessionId);
    List<StudentSessionDto> getAllStudentSessionByCourseIdAndStudentID(Long courseId, Long studentID);

}
