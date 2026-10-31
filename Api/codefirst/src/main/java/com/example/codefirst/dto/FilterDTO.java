package com.example.codefirst.dto;

import java.util.List;

public record FilterDTO(
    Long id,
    String name,
    List<Long> categoryIds
) {}
