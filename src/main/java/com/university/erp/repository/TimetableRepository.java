package com.university.erp.repository;

import com.university.erp.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    List<Timetable> findByProgramOrderByBatchYearDescSemesterAsc(String program);

    Optional<Timetable> findByProgramAndCourseAndBatchYearAndSemester(
        String program, String course, Short batchYear, Short semester);
}
