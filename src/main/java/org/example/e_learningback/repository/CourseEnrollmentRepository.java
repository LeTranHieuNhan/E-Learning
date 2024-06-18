package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.CourseEnrollment;
import org.example.e_learningback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    @Query(value = "SELECT ce FROM CourseEnrollment ce WHERE ce.course.id = :course_id and ce.user.id = :user_id")
    Optional<CourseEnrollment> findByCourseIdAndUserId(@Param("course_id") Long course_id, @Param("user_id")Long user_id);

    boolean findByCourse(Course course);

    boolean findByUser(User user);

    List<CourseEnrollment> findByUserAndCourse(User user, Course course);
}
