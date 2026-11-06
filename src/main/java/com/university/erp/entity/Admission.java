package com.university.erp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "admissions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admission_id", nullable = false, unique = true, length = 30)
    private String admissionId;

    // Personal
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(length = 20)
    private String gender;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 10)
    private String pincode;

    @Column(length = 60)
    private String nationality;

    // Academic
    @Column(nullable = false, length = 100)
    private String program;

    @Column(nullable = false)
    private Short semester;

    @Column(name = "batch_year", nullable = false)
    private Short batchYear;

    // Previous education
    @Column(name = "prev_school", length = 255)
    private String prevSchool;

    @Column(name = "prev_board", length = 255)
    private String prevBoard;

    @Column(name = "prev_year", length = 10)
    private String prevYear;

    @Column(name = "prev_percentage", length = 20)
    private String prevPercentage;

    // Guardian
    @Column(name = "guardian_name", length = 255)
    private String guardianName;

    @Column(name = "guardian_relation", length = 50)
    private String guardianRelation;

    @Column(name = "guardian_phone", length = 20)
    private String guardianPhone;

    @Column(name = "guardian_email", length = 255)
    private String guardianEmail;

    // Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void prePersist() { createdAt = updatedAt = OffsetDateTime.now(); }

    @PreUpdate
    void preUpdate() { updatedAt = OffsetDateTime.now(); }

    public enum Status { PENDING, REVIEW, APPROVED, REJECTED }
}
