package com.university.erp.controller;

import com.university.erp.dto.CourseRequest;
import com.university.erp.dto.CourseResponse;
import com.university.erp.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest req) {
        return ResponseEntity.ok(courseService.create(req));
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> list(
        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "") String program,
        @RequestParam(defaultValue = "") String status,
        @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(courseService.list(search, program, status, page));
    }

    @GetMapping("/{courseCode}")
    public ResponseEntity<CourseResponse> get(@PathVariable String courseCode) {
        return ResponseEntity.ok(courseService.getByCourseCode(courseCode));
    }

    @PutMapping("/{courseCode}")
    public ResponseEntity<CourseResponse> update(
        @PathVariable String courseCode,
        @Valid @RequestBody CourseRequest req
    ) {
        return ResponseEntity.ok(courseService.update(courseCode, req));
    }
}
