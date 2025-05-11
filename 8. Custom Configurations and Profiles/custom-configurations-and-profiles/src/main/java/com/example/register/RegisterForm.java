package com.example.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username should be less than 50 characters long")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 50, message = "Password should be less than 50 characters long")
    private String password;
}
