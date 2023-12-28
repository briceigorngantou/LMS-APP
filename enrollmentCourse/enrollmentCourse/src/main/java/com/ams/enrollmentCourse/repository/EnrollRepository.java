package com.ams.enrollmentCourse.repository;

import com.ams.enrollmentCourse.entity.Enroll;
import com.ams.enrollmentCourse.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Enroll findByStatus(Progress status);
    Enroll findByUserId(Long userId);
    Enroll findByCourseId(Long courseId);
}
