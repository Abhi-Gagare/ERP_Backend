package com.university.erp.dto;

import com.university.erp.entity.Department;

import java.time.OffsetDateTime;
import java.util.List;

public record DepartmentResponse(
    Long id,
    String code,
    String name,
    String description,
    String deanName,
    String deanEmail,
    String deanPhone,
    List<String> degrees,
    String status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
    public static DepartmentResponse from(Department d) {
        return new DepartmentResponse(
            d.getId(), d.getCode(), d.getName(), d.getDescription(),
            d.getDeanName(), d.getDeanEmail(), d.getDeanPhone(),
            d.getDegrees() == null ? List.of() : d.getDegrees(),
            d.getStatus().name(),
            d.getCreatedAt(), d.getUpdatedAt()
        );
    }
}
