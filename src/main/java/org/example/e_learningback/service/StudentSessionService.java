package org.example.e_learningback.service;

public interface StudentSessionService {

    String saveStudentSession(Long courseId, Long studentId, Long courseSessionId);
    void deleteStudentSession(Long courseId, Long studentId, Long courseSessionId);
    void getStudentSession(Long courseId, Long studentId, Long courseSessionId);
    void getAllStudentSession(Long courseId);

}
