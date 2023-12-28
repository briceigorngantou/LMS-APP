package com.ams.courses.repository;

import com.ams.courses.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {
    Courses findByTitle(String username);

    Courses findByCode(String email);
}
