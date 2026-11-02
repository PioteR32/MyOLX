package com.example.codefirst.dto;

public record OrderDTO(
    String name,
    String status,
    boolean isCorrect,
    boolean isProtected,
    Long announcementId
) {}
