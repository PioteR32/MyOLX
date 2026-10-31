package com.example.codefirst.dto;

public record AnnouncementsImageDTO(
    Long id,
    byte[] imageData,
    Long announcementId
) {}
