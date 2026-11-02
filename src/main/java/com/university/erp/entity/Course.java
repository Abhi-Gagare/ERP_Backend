package com.university.erp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    private String courseCode;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, length = 100)
    private String program;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false)
    private Integer durationMonths;

    @Column(nullable = false, length = 20)
    private String level;     // UG, PG, PhD, Diploma

    @Column(nullable = false, length = 20)
    private String mode;      // Full-time, Part-time, Online

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    // Fee structure
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CourseFeeComponent> feeComponents;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() { createdAt = updatedAt = OffsetDateTime.now(); }

    @PreUpdate
    void onUpdate() { updatedAt = OffsetDateTime.now(); }

    public enum Status { ACTIVE, INACTIVE, UPCOMING }
}
