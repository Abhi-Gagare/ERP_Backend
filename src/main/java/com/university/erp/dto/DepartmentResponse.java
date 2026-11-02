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
    List<String> programs,
    String status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
    public static DepartmentResponse from(Department d) {
        return new DepartmentResponse(
            d.getId(), d.getCode(), d.getName(), d.getDescription(),
            d.getDeanName(), d.getDeanEmail(), d.getDeanPhone(),
            d.getPrograms() == null ? List.of() : d.getPrograms(),
            d.getStatus().name(),
            d.getCreatedAt(), d.getUpdatedAt()
        );
    }
}
