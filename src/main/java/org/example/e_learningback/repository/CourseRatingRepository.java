package org.example.e_learningback.repository;

import org.example.e_learningback.entity.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRatingRepository extends JpaRepository<CourseRating, Long> {
    List<CourseRating> findByCourseId(Long courseId);
}
