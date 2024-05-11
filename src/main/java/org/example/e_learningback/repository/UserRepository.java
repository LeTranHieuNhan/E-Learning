package org.example.e_learningback.repository;

import org.example.e_learningback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
