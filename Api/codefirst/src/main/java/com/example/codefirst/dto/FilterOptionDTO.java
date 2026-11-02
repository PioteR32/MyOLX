package com.example.codefirst.dto;

import java.util.List;

public record FilterOptionDTO(
    String name,
    Long filterId,
    Long parentFilterOptionId,
    List<FilterOptionDTO> subFilterOptions
) {}
