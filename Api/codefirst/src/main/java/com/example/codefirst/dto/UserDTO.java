package com.example.codefirst.dto;


public record UserDTO(
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    Long townId
) {}
