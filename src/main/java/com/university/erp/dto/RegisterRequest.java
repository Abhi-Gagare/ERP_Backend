package com.university.erp.dto;

import com.university.erp.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Email                  String email,
    @NotBlank @Size(min = 8)          String password,
    @NotBlank                         String fullName,
    @NotNull                          User.Role role
) {}
