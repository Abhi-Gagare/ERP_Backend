package com.university.erp.dto;

import com.university.erp.entity.Admission;

import java.time.OffsetDateTime;

public record AdmissionResponse(
    Long            id,
    String          admissionId,
    String          firstName,
    String          lastName,
    String          dateOfBirth,
    String          gender,
    String          email,
    String          phone,
    String          address,
    String          city,
    String          state,
    String          pincode,
    String          nationality,
    String          program,
    String          department,
    Short           semester,
    Short           batchYear,
    String          prevSchool,
    String          prevBoard,
    String          prevYear,
    String          prevPercentage,
    String          guardianName,
    String          guardianRelation,
    String          guardianPhone,
    String          guardianEmail,
    Admission.Status status,
    OffsetDateTime  createdAt
) {
    public static AdmissionResponse from(Admission a) {
        return new AdmissionResponse(
            a.getId(), a.getAdmissionId(),
            a.getFirstName(), a.getLastName(),
            a.getDateOfBirth() != null ? a.getDateOfBirth().toString() : null,
            a.getGender(), a.getEmail(), a.getPhone(),
            a.getAddress(), a.getCity(), a.getState(), a.getPincode(), a.getNationality(),
            a.getProgram(), a.getDepartment(), a.getSemester(), a.getBatchYear(),
            a.getPrevSchool(), a.getPrevBoard(), a.getPrevYear(), a.getPrevPercentage(),
            a.getGuardianName(), a.getGuardianRelation(), a.getGuardianPhone(), a.getGuardianEmail(),
            a.getStatus(), a.getCreatedAt()
        );
    }
}
