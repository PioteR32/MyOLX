package com.example.codefirst.dto;

import java.util.List;

public record TownDTO(
    Long id,
    String name,
    List<Long> userIds
) {}
