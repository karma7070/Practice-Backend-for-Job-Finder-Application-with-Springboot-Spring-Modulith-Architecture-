package com.FindAJob.demo.SecurityPackage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthDTO (
        @Email(message ="Invalid Email")
        String email,
       @NotNull(message = "Password is required")
       String password){
}
