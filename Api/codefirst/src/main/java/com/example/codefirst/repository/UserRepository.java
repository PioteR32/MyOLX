package com.example.codefirst.repository;

import com.example.codefirst.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
   @Query("SELECT u FROM User u WHERE u.email = :login OR u.phoneNum = :login")
    Optional<User> findByLogin(@Param("login") String login);
   
}
