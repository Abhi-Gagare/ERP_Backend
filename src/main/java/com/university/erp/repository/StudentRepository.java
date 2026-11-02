package com.university.erp.repository;

import com.university.erp.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNumber(String rollNumber);
    Optional<Student> findByUserId(Long userId);
    boolean existsByRollNumber(String rollNumber);
    Page<Student> findByDepartment(String department, Pageable pageable);
}
