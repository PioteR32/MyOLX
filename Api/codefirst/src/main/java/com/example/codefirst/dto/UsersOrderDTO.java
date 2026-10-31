package com.example.codefirst.dto;

public record UsersOrderDTO(
    Long id,
    Long orderId,
    Long buyerId,
    Long sellerId
) {}
