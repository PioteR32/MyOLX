package com.example.codefirst.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record AnnouncementDTO(
    Long id,
    String title,
    String description,
    BigDecimal price,
    Instant createdAt,
    boolean isActive,
    Long userId,
    List<AnnouncementsImageDTO> images,
    Long orderId
) {}
