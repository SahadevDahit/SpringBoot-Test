package com.example.test.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>  {

    public UserEntity findByUsername(String username);
}
