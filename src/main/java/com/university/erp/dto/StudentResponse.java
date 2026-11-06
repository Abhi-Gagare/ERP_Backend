package com.university.erp.dto;

import com.university.erp.entity.Student;

import java.time.OffsetDateTime;

public record StudentResponse(
    Long           id,
    String         rollNumber,
    String         fullName,
    String         email,
    String         program,
    Short          semester,
    Short          batchYear,
    String         dateOfBirth,
    String         phone,
    String         address,
    String         guardianName,
    String         guardianPhone,
    String         enrollmentDate,
    boolean        isActive,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
    public static StudentResponse from(Student s) {
        return new StudentResponse(
            s.getId(),
            s.getRollNumber(),
            s.getUser().getFullName(),
            s.getUser().getEmail(),
            s.getProgram(),
            s.getSemester(),
            s.getBatchYear(),
            s.getDateOfBirth() != null ? s.getDateOfBirth().toString() : null,
            s.getPhone(),
            s.getAddress(),
            s.getGuardianName(),
            s.getGuardianPhone(),
            s.getEnrollmentDate() != null ? s.getEnrollmentDate().toString() : null,
            s.isActive(),
            s.getCreatedAt(),
            s.getUpdatedAt()
        );
    }
}
