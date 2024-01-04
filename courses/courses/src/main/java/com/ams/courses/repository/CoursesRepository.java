package com.ams.courses.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.courses.entity.Courses;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {
    Courses findByTitle(String title);

    Courses findByCode(String code);

    List<Courses> findByTitleOrCodeContaining(String keyword1, String keyword2);
}
