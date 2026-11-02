package com.example.codefirst.dto;

import java.util.List;

public record FilterDTO(
    String name,
    List<Long> categoryIds
) {}
