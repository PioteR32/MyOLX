package com.example.codefirst.repository;

import com.example.codefirst.entities.UsersOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersOrderRepository extends JpaRepository<UsersOrder, Long> {
}
