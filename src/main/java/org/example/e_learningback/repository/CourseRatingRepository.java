package org.example.e_learningback.repository;

import org.example.e_learningback.entity.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CourseRatingRepository extends JpaRepository<CourseRating, Long> {


    List<CourseRating> findAllByCourseId(Long id);
}
