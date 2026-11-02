package com.university.erp.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class DepartmentRequest {

    @NotBlank public String code;
    @NotBlank public String name;
    public String description;
    public String deanName;
    public String deanEmail;
    public String deanPhone;
    public List<String> degrees;
    public String status;
}
