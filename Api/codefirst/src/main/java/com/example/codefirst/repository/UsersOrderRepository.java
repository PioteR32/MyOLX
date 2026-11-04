package com.example.codefirst.repository;

import com.example.codefirst.entities.Order;
import com.example.codefirst.entities.UsersOrder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersOrderRepository extends JpaRepository<UsersOrder, Long> {
    @Query ("SELECT DISTINCT uo.order FROM UsersOrder uo"+
    " WHERE uo.buyer.id = :buyerId")
    List<Order> findOrdersByBuyerId(@Param("buyerId")Long buyerId);

    @Query ("SELECT DISTINCT uo.order FROM UsersOrder uo"+
    " WHERE uo.seller.id = :sellerId")
    List<Order> findOrdersBySellerId(@Param("sellerId")Long sellerId);

    @Query ("SELECT DISTINCT uo.order FROM UsersOrder uo"+
    " WHERE uo.seller.id = :userId OR uo.buyer.id = :userId")
    List<Order> findOrdersByUserId(@Param("userId")Long userId);



}
