package org.example.e_learningback.repository;

import org.example.e_learningback.dto.AssignmentGradeDto;
import org.example.e_learningback.entity.Assignment;
import org.example.e_learningback.entity.AssignmentGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentGradeRepository extends JpaRepository<AssignmentGrade, Long> {
    AssignmentGrade findByAssignment(Assignment assignment);
}
