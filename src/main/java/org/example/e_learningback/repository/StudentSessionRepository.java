package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.CourseSession;
import org.example.e_learningback.entity.StudentSession;
import org.example.e_learningback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentSessionRepository extends JpaRepository<StudentSession, Long> {
    Optional<StudentSession> findByCourseAndUser(Course course, User user);

    Optional<List<StudentSession>> findAllByCourseAndUser(Course course, User user);

    StudentSession findByCourseAndUserAndCourseSession(Course course, User user, CourseSession courseSession);
}
