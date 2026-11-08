package com.example.codefirst.dto.OperationsDto;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data 
public class UserRegistrationDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(max = 15, message = "Phone number must be at most 15 characters long")
    @NotBlank(message = "Phone number is required")
    private String phoneNum;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    
    @NotBlank(message = "Town name is required")
    private String town_name;
    public UserRegistrationDTO() {
    }
    public UserRegistrationDTO(String firstName, String lastName, String email, String phoneNum, String password, String town_name) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
        this.town_name = town_name;
    }
}
