package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT course FROM Course course WHERE course.title LIKE %:keyword% ")
    List<Course> searchByTitleOrText(@Param("keyword") String keyword);

}
