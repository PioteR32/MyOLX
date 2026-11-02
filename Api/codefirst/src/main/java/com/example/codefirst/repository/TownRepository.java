package com.example.codefirst.repository;

import com.example.codefirst.entities.Town;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    Optional<Town> findByName(String name);
}
