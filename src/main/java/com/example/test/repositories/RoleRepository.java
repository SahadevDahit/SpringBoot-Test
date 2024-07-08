package com.example.test.repositories;

import com.example.test.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByRole(String role);
}
