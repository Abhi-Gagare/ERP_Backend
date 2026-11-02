package com.university.erp.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class DepartmentRequest {

    @NotBlank public String code;
    @NotBlank public String name;
    public String description;
    public String hodName;
    public String hodEmail;
    public String hodPhone;
    @NotNull @Min(0) public Integer totalSeats;
    public List<String> programs;
    public String status;
}
