package com.slippery.userservice.repository;

import com.slippery.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findUserByUsernameAndEmail(String username,String email);
}
