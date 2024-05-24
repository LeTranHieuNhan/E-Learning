package org.example.e_learningback.repository;

import org.example.e_learningback.entity.StudentSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSessionRepository extends JpaRepository<StudentSession, Long> {
}
