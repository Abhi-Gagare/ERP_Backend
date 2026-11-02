package com.university.erp.controller;

import com.university.erp.dto.DepartmentRequest;
import com.university.erp.dto.DepartmentResponse;
import com.university.erp.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest req) {
        return ResponseEntity.ok(departmentService.create(req));
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentResponse>> list(
        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "") String status,
        @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(departmentService.list(search, status, page));
    }

    @GetMapping("/{code}")
    public ResponseEntity<DepartmentResponse> get(@PathVariable String code) {
        return ResponseEntity.ok(departmentService.getByCode(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<DepartmentResponse> update(
        @PathVariable String code,
        @Valid @RequestBody DepartmentRequest req
    ) {
        return ResponseEntity.ok(departmentService.update(code, req));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        departmentService.delete(code);
        return ResponseEntity.noContent().build();
    }
}
