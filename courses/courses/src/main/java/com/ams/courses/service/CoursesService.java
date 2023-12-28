package com.ams.courses.service;

import com.ams.courses.entity.Courses;
import com.ams.courses.repository.CoursesRepository;
import com.ams.courses.dto.CoursesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoursesService {

    private final CoursesRepository coursesRepository;

    @Autowired
    public CoursesService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public List<Courses> getCourses() {
        return coursesRepository.findAll();
    }

    public Courses getByTitle(String title) {
        return coursesRepository.findByTitle(title);
    }

    public Courses getByCode(String code) {
        return coursesRepository.findByCode(code);
    }

    public Courses getById(Long id) throws Exception {
        return coursesRepository.findById(id).orElseThrow(Exception::new);
    }

    public Courses saveCourses(CoursesDTO courses) {
        return coursesRepository.save(new CoursesDTO().toCoursesEntity(courses));
    }

    public Courses updateCourses(CoursesDTO courses, Long id) throws Exception {
        Courses currentCourses = coursesRepository.findById(id).orElseThrow(Exception::new);
        currentCourses.setTitle(courses.getTitle());
        currentCourses.setCode(courses.getCode());
        currentCourses.setDescription(courses.getDescription());
        currentCourses.setCertification(courses.getCertification());
        currentCourses.setUpdatedAt(courses.getUpdatedAt());
        return coursesRepository.save(currentCourses);
    }

    public ResponseEntity<String> deleteCourses(Long idCourses) {
        if (this.coursesRepository.findById(idCourses).isPresent()) {
            coursesRepository.deleteById(idCourses);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("courses are deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("resource not found");
    }

    public Courses patchCourses(Long id, Map<String, Object> courses) throws Exception {
        Courses currentCourses = coursesRepository.findById(id).orElseThrow(RuntimeException::new);
        if (courses.containsKey("title"))
            currentCourses.setTitle(courses.get("title").toString());
        if (courses.containsKey("description"))
            currentCourses.setDescription(courses.get("description").toString());
        if (courses.containsKey("code"))
            currentCourses.setCode(courses.get("code").toString());
        if (courses.containsKey("certification"))
            currentCourses.setCertification(courses.get("certification").toString());
        return coursesRepository.save(currentCourses);
    }
}
