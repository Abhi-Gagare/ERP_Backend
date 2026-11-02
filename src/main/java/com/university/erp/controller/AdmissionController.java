package com.university.erp.controller;

import com.university.erp.dto.AdmissionRequest;
import com.university.erp.dto.AdmissionResponse;
import com.university.erp.entity.Admission;
import com.university.erp.service.AdmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admissions")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;

    @PostMapping
    public ResponseEntity<AdmissionResponse> create(@Valid @RequestBody AdmissionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(admissionService.create(req));
    }

    @GetMapping
    public ResponseEntity<Page<AdmissionResponse>> list(
        @RequestParam(defaultValue = "")  String search,
        @RequestParam(defaultValue = "")  String status,
        @RequestParam(defaultValue = "0") int    page,
        @RequestParam(defaultValue = "20") int   size
    ) {
        return ResponseEntity.ok(admissionService.list(search, status, page, size));
    }

    @GetMapping("/{admissionId}")
    public ResponseEntity<AdmissionResponse> getOne(@PathVariable String admissionId) {
        return ResponseEntity.ok(admissionService.getByAdmissionId(admissionId));
    }

    @PatchMapping("/{admissionId}/status")
    public ResponseEntity<AdmissionResponse> updateStatus(
        @PathVariable String admissionId,
        @RequestBody  Map<String, String> body
    ) {
        Admission.Status status = Admission.Status.valueOf(body.get("status").toUpperCase());
        return ResponseEntity.ok(admissionService.updateStatus(admissionId, status));
    }
}
