package org.example.e_learningback.repository;

import org.example.e_learningback.dto.TeacherProfileDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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








}
