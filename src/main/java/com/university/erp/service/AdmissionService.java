package com.university.erp.service;

import com.university.erp.dto.AdmissionRequest;
import com.university.erp.dto.AdmissionResponse;
import com.university.erp.entity.Admission;
import com.university.erp.repository.AdmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final AdmissionRepository repo;
    private final StudentService studentService;

    @Transactional
    public AdmissionResponse create(AdmissionRequest req) {
        String admId = generateAdmissionId();

        Admission a = Admission.builder()
            .admissionId(admId)
            .firstName(req.firstName())
            .lastName(req.lastName())
            .dateOfBirth(req.dateOfBirth() != null && !req.dateOfBirth().isBlank()
                ? LocalDate.parse(req.dateOfBirth()) : null)
            .gender(req.gender())
            .email(req.email())
            .phone(req.phone())
            .address(req.address())
            .city(req.city())
            .state(req.state())
            .pincode(req.pincode())
            .nationality(req.nationality())
            .program(req.program())
            .semester(req.semester())
            .batchYear(req.batchYear())
            .prevSchool(req.prevSchool())
            .prevBoard(req.prevBoard())
            .prevYear(req.prevYear())
            .prevPercentage(req.prevPercentage())
            .guardianName(req.guardianName())
            .guardianRelation(req.guardianRelation())
            .guardianPhone(req.guardianPhone())
            .guardianEmail(req.guardianEmail())
            .status(Admission.Status.PENDING)
            .build();

        return AdmissionResponse.from(repo.save(a));
    }

    @Transactional(readOnly = true)
    public Page<AdmissionResponse> list(String search, String status, int page, int size) {
        Admission.Status statusEnum = null;
        if (status != null && !status.isBlank()) {
            statusEnum = Admission.Status.valueOf(status.toUpperCase());
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return repo.search(search, statusEnum, pageable).map(AdmissionResponse::from);
    }

    @Transactional(readOnly = true)
    public AdmissionResponse getByAdmissionId(String admissionId) {
        return repo.findByAdmissionId(admissionId)
            .map(AdmissionResponse::from)
            .orElseThrow(() -> new NoSuchElementException("Admission not found: " + admissionId));
    }

    @Transactional
    public AdmissionResponse updateStatus(String admissionId, Admission.Status status) {
        Admission a = repo.findByAdmissionId(admissionId)
            .orElseThrow(() -> new NoSuchElementException("Admission not found: " + admissionId));
        a.setStatus(status);
        Admission saved = repo.save(a);
        if (status == Admission.Status.APPROVED) {
            studentService.provisionFromAdmission(saved);
        }
        return AdmissionResponse.from(saved);
    }

    private String generateAdmissionId() {
        int year = Year.now().getValue();
        long count = repo.count() + 1;
        String candidate;
        do {
            candidate = String.format("ADM-%d-%03d", year, count++);
        } while (repo.existsByAdmissionId(candidate));
        return candidate;
    }
}
