package com.example.Hospital_Management_System.repo;

import com.example.Hospital_Management_System.entities.User;
import com.example.Hospital_Management_System.entities.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}