package com.ams.enrollmentCourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.enrollmentCourse.entity.Enroll;
import com.ams.enrollmentCourse.entity.Progress;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Enroll findByStatus(Progress status);

    Enroll findByUserId(Long userId);

    Enroll findByCourseId(Long courseId);

    Enroll findByUserIdAndCourseIdAndSubscribe(Long userId, Long courseId, Boolean subscribe);

    Enroll findByUserIdAndCourseId(Long userId, Long courseId);
}
