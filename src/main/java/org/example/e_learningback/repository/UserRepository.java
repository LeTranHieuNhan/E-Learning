package org.example.e_learningback.repository;

import org.example.e_learningback.dto.TeacherProfileDto;
import org.example.e_learningback.dto.TeacherReviewDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT new org.example.e_learningback.dto.TeacherProfileDto(" +
            "u.id, u.name, u.avatar, u.bio, u.occupation, u.email, " +
            "CAST(AVG(cr.rating) AS double), " + // Cast AVG(cr.rating) to double
            "COUNT(DISTINCT ce.user.id), " +
            "COUNT(DISTINCT c.id)," +
            "COUNT(DISTINCT ce.user.id)) " +
            "FROM User u " +
            "LEFT JOIN Course c ON c.user.id = u.id " +
            "LEFT JOIN CourseEnrollment ce ON ce.course.id = c.id " +
            "LEFT JOIN CourseRating cr ON cr.course.id = c.id " +
            "WHERE u.id = :id AND u.role.name = 'ADMIN' " +
            "GROUP BY u.id, u.name, u.avatar, u.bio, u.occupation, u.email")
    Optional<TeacherProfileDto> getTeacherProfile(@Param("id") Long id);

    @Query("SELECT new org.example.e_learningback.dto.TeacherReviewDto(" +
            "u.id, u.name, " +
            "CAST(AVG(cr.rating) AS double), " + // Cast AVG(cr.rating) to double
            "COUNT(cr.id), " +
            "SUM(CASE WHEN cr.rating = 1 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN cr.rating = 2 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN cr.rating = 3 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN cr.rating = 4 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN cr.rating = 5 THEN 1 ELSE 0 END)) " +
            "FROM User u " +
            "JOIN Course c ON u.id = c.user.id " +
            "LEFT JOIN CourseRating cr ON c.id = cr.course.id " +
            "WHERE u.id = :id AND u.role.name = 'ADMIN' " +
            "GROUP BY u.id, u.name")
    Optional<TeacherReviewDto> getTeacherReview(@Param("id") Long id);
    @Query("SELECT u FROM User u WHERE u.joinedAt >= :startOfWeek")
    List<User> findNewUsersOfTheWeek(@Param("startOfWeek") LocalDateTime startOfWeek);
}
