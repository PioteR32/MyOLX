package com.example.codefirst.dto;

import java.util.List;

public record CategoryDTO(
    String title,
    Long parentCategoryId,
    List<CategoryDTO> subCategories,
    List<Long> filterIds
) {}
