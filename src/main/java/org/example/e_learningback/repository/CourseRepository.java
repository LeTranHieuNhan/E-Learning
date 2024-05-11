package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
