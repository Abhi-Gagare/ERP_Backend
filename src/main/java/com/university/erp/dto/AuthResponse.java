package com.university.erp.dto;

import com.university.erp.entity.User;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    long   expiresIn,
    UserDto user
) {
    public record UserDto(
        Long        id,
        String      email,
        String      fullName,
        User.Role   role
    ) {}
}
