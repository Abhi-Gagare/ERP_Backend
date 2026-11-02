package com.university.erp.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FeeRequest {

    @NotBlank
    public String admissionId;

    @NotBlank
    public String feeType;

    @NotNull @DecimalMin("0.01")
    public BigDecimal amount;

    @NotNull
    public LocalDate dueDate;

    public String remarks;

    // For payment update
    public static class PayRequest {
        @NotNull @DecimalMin("0.01")
        public BigDecimal amountPaid;
        public String remarks;
    }
}
