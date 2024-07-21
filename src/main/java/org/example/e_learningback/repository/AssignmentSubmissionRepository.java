package org.example.e_learningback.repository;

import org.example.e_learningback.entity.Assignment;
import org.example.e_learningback.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    List<AssignmentSubmission> findAllByAssignment(Assignment assignment);
}
