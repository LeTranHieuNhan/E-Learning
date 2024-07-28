package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT course FROM Course course WHERE course.title LIKE %:keyword% ")
    List<Course> searchByTitleOrText(@Param("keyword") String keyword);

    Optional<Course> findByUserId(Long teacherId);

    Collection<Course> findAllByUserId(Long teacherId);
}
