package com.example.codefirst.dto.OperationsDto;
import lombok.Data;


import jakarta.validation.constraints.NotBlank;


@Data 
public class UserLoginDTO {

    @NotBlank(message = "Email or phone number is required")
    String login;

    @NotBlank(message = "Password is required")
    String password;
}
