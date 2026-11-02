package com.university.erp.dto;

import jakarta.validation.constraints.*;

public record AdmissionRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    String dateOfBirth,
    String gender,
    @NotBlank @Email String email,
    String phone,
    String address,
    String city,
    String state,
    String pincode,
    String nationality,
    @NotBlank String program,
    @NotBlank String department,
    @NotNull  Short  semester,
    @NotNull  Short  batchYear,
    String prevSchool,
    String prevBoard,
    String prevYear,
    String prevPercentage,
    String guardianName,
    String guardianRelation,
    String guardianPhone,
    String guardianEmail
) {}
