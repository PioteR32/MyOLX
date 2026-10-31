package com.example.codefirst.dto;

public record OrderDTO(
    Long id,
    String name,
    String status,
    boolean isCorrect,
    boolean isProtected,
    Long announcementId
) {}
