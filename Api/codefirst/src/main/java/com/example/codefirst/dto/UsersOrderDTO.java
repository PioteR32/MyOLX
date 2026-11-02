package com.example.codefirst.dto;

public record UsersOrderDTO(
    Long orderId,
    Long buyerId,
    Long sellerId
) {}
