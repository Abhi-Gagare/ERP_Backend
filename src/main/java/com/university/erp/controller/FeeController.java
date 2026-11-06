package com.university.erp.controller;

import com.university.erp.dto.FeeRequest;
import com.university.erp.dto.FeeResponse;
import com.university.erp.service.FeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
public class FeeController {

    private final FeeService feeService;

    @PostMapping
    public ResponseEntity<FeeResponse> create(@Valid @RequestBody FeeRequest req) {
        return ResponseEntity.ok(feeService.create(req));
    }

    @GetMapping
    public ResponseEntity<Page<FeeResponse>> list(
        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "") String status,
        @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(feeService.list(search, status, page));
    }

    @GetMapping("/stats")
    public ResponseEntity<FeeResponse.Stats> stats() {
        return ResponseEntity.ok(feeService.stats());
    }

    @GetMapping("/admissions-summary")
    public ResponseEntity<java.util.List<FeeService.AdmissionSummary>> admissionsSummary(
        @RequestParam(defaultValue = "20") int limit
    ) {
        return ResponseEntity.ok(feeService.admissionsForFees(limit));
    }

    @GetMapping("/{feeId}")
    public ResponseEntity<FeeResponse> get(@PathVariable String feeId) {
        return ResponseEntity.ok(feeService.getByFeeId(feeId));
    }

    @PostMapping("/{feeId}/pay")
    public ResponseEntity<FeeResponse> pay(
        @PathVariable String feeId,
        @Valid @RequestBody FeeRequest.PayRequest req
    ) {
        return ResponseEntity.ok(feeService.pay(feeId, req));
    }

    @PostMapping("/{feeId}/waive")
    public ResponseEntity<FeeResponse> waive(
        @PathVariable String feeId,
        @RequestBody(required = false) Map<String, String> body
    ) {
        String remarks = body != null ? body.get("remarks") : null;
        return ResponseEntity.ok(feeService.waive(feeId, remarks));
    }
}
