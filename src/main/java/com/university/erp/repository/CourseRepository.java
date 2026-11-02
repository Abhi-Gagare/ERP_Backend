package com.university.erp.repository;

import com.university.erp.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCourseCode(String courseCode);

    @Query("""
        SELECT c FROM Course c
        WHERE (:search = '' OR
            LOWER(c.name)       LIKE LOWER(CONCAT('%',:search,'%')) OR
            LOWER(c.courseCode) LIKE LOWER(CONCAT('%',:search,'%')) OR
            LOWER(c.program)    LIKE LOWER(CONCAT('%',:search,'%'))
        )
        AND (:program = '' OR LOWER(c.program) = LOWER(:program))
        AND (:status IS NULL OR c.status = :status)
        ORDER BY c.createdAt DESC
        """)
    Page<Course> search(
        @Param("search")  String search,
        @Param("program") String program,
        @Param("status")  Course.Status status,
        Pageable pageable
    );
}
