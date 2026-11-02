package com.university.erp.service;

import com.university.erp.dto.DepartmentRequest;
import com.university.erp.dto.DepartmentResponse;
import com.university.erp.entity.Department;
import com.university.erp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repo;

    @Transactional
    public DepartmentResponse create(DepartmentRequest req) {
        if (repo.existsByCode(req.code.toUpperCase()))
            throw new IllegalArgumentException("Program code already exists: " + req.code);
        if (repo.existsByName(req.name))
            throw new IllegalArgumentException("Program name already exists: " + req.name);

        Department dept = Department.builder()
            .code(req.code.toUpperCase())
            .name(req.name)
            .description(req.description)
            .deanName(req.deanName)
            .deanEmail(req.deanEmail)
            .deanPhone(req.deanPhone)
            .programs(req.programs != null ? new ArrayList<>(req.programs) : new ArrayList<>())
            .status(req.status != null ? Department.Status.valueOf(req.status) : Department.Status.ACTIVE)
            .build();

        return DepartmentResponse.from(repo.save(dept));
    }

    public Page<DepartmentResponse> list(String search, String status, int page) {
        Department.Status st = (status != null && !status.isBlank()) ? Department.Status.valueOf(status) : null;
        return repo.search(
            search == null ? "" : search,
            st,
            PageRequest.of(page, 20)
        ).map(DepartmentResponse::from);
    }

    public DepartmentResponse getByCode(String code) {
        return repo.findAll().stream()
            .filter(d -> d.getCode().equalsIgnoreCase(code))
            .findFirst()
            .map(DepartmentResponse::from)
            .orElseThrow(() -> new NoSuchElementException("Program not found: " + code));
    }

    @Transactional
    public DepartmentResponse update(String code, DepartmentRequest req) {
        Department dept = repo.findAll().stream()
            .filter(d -> d.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Program not found: " + code));

        dept.setName(req.name);
        dept.setDescription(req.description);
        dept.setDeanName(req.deanName);
        dept.setDeanEmail(req.deanEmail);
        dept.setDeanPhone(req.deanPhone);
        dept.setPrograms(req.programs != null ? new ArrayList<>(req.programs) : new ArrayList<>());
        if (req.status != null) dept.setStatus(Department.Status.valueOf(req.status));

        return DepartmentResponse.from(repo.save(dept));
    }

    @Transactional
    public void delete(String code) {
        Department dept = repo.findAll().stream()
            .filter(d -> d.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Program not found: " + code));
        repo.delete(dept);
    }
}
