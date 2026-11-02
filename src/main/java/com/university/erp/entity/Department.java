package com.university.erp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(length = 100)
    private String hodName;

    @Column(length = 100)
    private String hodEmail;

    @Column(length = 20)
    private String hodPhone;

    @Column(nullable = false)
    private Integer totalSeats;

    @ElementCollection
    @CollectionTable(name = "department_programs", joinColumns = @JoinColumn(name = "department_id"))
    @Column(name = "program", length = 60)
    private List<String> programs;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() { createdAt = updatedAt = OffsetDateTime.now(); }

    @PreUpdate
    void onUpdate() { updatedAt = OffsetDateTime.now(); }

    public enum Status { ACTIVE, INACTIVE }
}
