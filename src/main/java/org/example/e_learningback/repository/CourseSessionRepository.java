package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.CourseSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSessionRepository  extends JpaRepository<CourseSession, Long> {
    Long findMaxSessionOrderByCourse(Course course);

    boolean existsByCourseAndSessionOrder(Course course, Long sessionOrder);
}
