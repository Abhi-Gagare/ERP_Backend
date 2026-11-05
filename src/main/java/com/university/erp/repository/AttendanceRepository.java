package com.university.erp.repository;

import com.university.erp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentIdAndSubjectCodeAndDate(Long studentId, String subjectCode, LocalDate date);

    Optional<Attendance> findByStudentIdAndSubjectCodeAndDateAndTimeSlot(
        Long studentId, String subjectCode, LocalDate date, String timeSlot);

    @Query("""
        SELECT a FROM Attendance a JOIN a.student s
        WHERE s.program = :program
          AND (:batchYear IS NULL OR s.batchYear = :batchYear)
          AND a.subjectCode = :subjectCode
          AND a.date = :date
          AND a.timeSlot = :timeSlot
        ORDER BY s.rollNumber
        """)
    List<Attendance> findSession(
        @Param("program") String program,
        @Param("batchYear") Short batchYear,
        @Param("subjectCode") String subjectCode,
        @Param("date") LocalDate date,
        @Param("timeSlot") String timeSlot
    );

    @Query("""
        SELECT COUNT(a) FROM Attendance a
        WHERE a.student.id = :studentId
          AND a.subjectCode = :subjectCode
          AND a.status = :status
        """)
    long countByStatus(@Param("studentId") Long studentId, @Param("subjectCode") String subjectCode,
                       @Param("status") Attendance.Status status);

    default long countPresent(Long studentId, String subjectCode) {
        return countByStatus(studentId, subjectCode, Attendance.Status.PRESENT);
    }

    @Query("""
        SELECT COUNT(a) FROM Attendance a
        WHERE a.student.id = :studentId
          AND a.subjectCode = :subjectCode
        """)
    long countTotal(@Param("studentId") Long studentId, @Param("subjectCode") String subjectCode);
}
