package com.example.codefirst.dto;

import java.util.List;

public record UserDTO(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    Long townId
) {}
