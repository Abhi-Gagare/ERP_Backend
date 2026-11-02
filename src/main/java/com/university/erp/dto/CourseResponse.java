package com.university.erp.dto;

import com.university.erp.entity.Course;
import com.university.erp.entity.CourseFeeComponent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record CourseResponse(
    Long id,
    String courseCode,
    String name,
    String description,
    String department,
    String program,
    Integer credits,
    Integer durationMonths,
    String level,
    String mode,
    Integer totalSeats,
    Integer availableSeats,
    String status,
    List<FeeComponent> feeComponents,
    BigDecimal totalFeePerSemester,
    BigDecimal totalFeePerYear,
    BigDecimal totalOneTime,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
    public record FeeComponent(
        Long id, String feeType, BigDecimal amount, String frequency, String remarks
    ) {}

    public static CourseResponse from(Course c) {
        List<FeeComponent> components = c.getFeeComponents() == null ? List.of() :
            c.getFeeComponents().stream()
                .map(f -> new FeeComponent(f.getId(), f.getFeeType(), f.getAmount(), f.getFrequency(), f.getRemarks()))
                .toList();

        BigDecimal perSem  = components.stream().filter(f -> "Per Semester".equals(f.frequency())).map(FeeComponent::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal perYear = components.stream().filter(f -> "Per Year".equals(f.frequency())).map(FeeComponent::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal oneTime = components.stream().filter(f -> "One-time".equals(f.frequency())).map(FeeComponent::amount).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CourseResponse(
            c.getId(), c.getCourseCode(), c.getName(), c.getDescription(),
            c.getDepartment(), c.getProgram(), c.getCredits(), c.getDurationMonths(),
            c.getLevel(), c.getMode(), c.getTotalSeats(), c.getAvailableSeats(),
            c.getStatus().name(), components, perSem, perYear, oneTime,
            c.getCreatedAt(), c.getUpdatedAt()
        );
    }
}
