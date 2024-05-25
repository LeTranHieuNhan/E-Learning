package org.example.e_learningback.repository;

import org.example.e_learningback.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
