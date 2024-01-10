package com.ams.courses.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ams.courses.dto.CoursesDTO;
import com.ams.courses.entity.Courses;
import com.ams.courses.exception.CoursesAlreadyExistsException;
import com.ams.courses.repository.CoursesRepository;

@Service
public class CoursesService {

    private final CoursesRepository coursesRepository;

    @Autowired
    public CoursesService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    /**
     * @return List<Courses>
     * @throws Exception
     */
    public List<Courses> getCourses() throws Exception {
        return coursesRepository.findAll();
    }

    /**
     * @param title
     * @return Courses
     * @throws Exception
     */
    public Courses getByTitle(String title) throws Exception {
        return coursesRepository.findByTitle(title);
    }

    /**
     * @param code
     * @return Courses
     * @throws Exception
     */
    public Courses getByCode(String code) throws Exception {
        return coursesRepository.findByCode(code);
    }

    /**
     * @param id
     * @return Courses
     * @throws Exception
     */
    public Courses getById(Long id) throws Exception {
        return coursesRepository.findById(id).orElseThrow(Exception::new);
    }

    /**
     * @param courses
     * @return Courses
     * @throws CoursesAlreadyExistsException
     * @throws Exception
     */
    public Courses saveCourses(CoursesDTO courses) throws CoursesAlreadyExistsException, Exception {
        if (coursesRepository.findByCode(courses.getCode()) != null
                || coursesRepository.findByTitle(courses.getTitle()) != null) {
            throw new CoursesAlreadyExistsException("Course already exist");
        }
        return coursesRepository.save(new CoursesDTO().toCoursesEntity(courses));
    }

    /**
     * @param courses
     * @param id
     * @return Courses
     * @throws Exception
     */
    public Courses updateCourses(CoursesDTO courses, Long id) throws Exception {
        Courses currentCourses = coursesRepository.findById(id).orElseThrow(Exception::new);
        currentCourses.setTitle(courses.getTitle());
        currentCourses.setCode(courses.getCode());
        currentCourses.setDescription(courses.getDescription());
        currentCourses.setCertification(courses.getCertification());
        return coursesRepository.save(currentCourses);
    }

    /**
     * @param idCourses
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> deleteCourses(Long idCourses) {
        if (this.coursesRepository.findById(idCourses).isPresent()) {
            coursesRepository.deleteById(idCourses);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("courses are deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("resource not found");
    }

    /**
     * @param id
     * @param courses
     * @return Courses
     * @throws Exception
     */
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

    /**
     * @param keyword
     * @return List<Courses>
     * @throws Exception
     */
    public List<Courses> searchCourseByTitleOrCode(String keyword) throws Exception {
        return coursesRepository.findByTitleOrCodeContaining(keyword, keyword);
    }
}
