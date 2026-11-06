package com.university.erp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "course_fee_components")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseFeeComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 60)
    private String feeType;   // Tuition, Hostel, Exam, Library, Lab, Sports…

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String frequency;  // Per Semester, Per Year, One-time

    private String remarks;
}
