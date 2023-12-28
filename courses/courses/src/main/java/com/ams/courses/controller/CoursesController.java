package com.ams.courses.controller;

import com.ams.courses.dto.CoursesDTO;
import com.ams.courses.entity.Courses;
import com.ams.courses.service.CoursesService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http:localhost.com", maxAge = 3600)
@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CoursesService coursesService;

    @Autowired
    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Courses> getCourses() {
        return coursesService.getCourses();
    }

    @GetMapping("/getByTitle/{title}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Courses getByTitle(@PathVariable String title) {
        return coursesService.getByTitle(title);
    }

    @GetMapping("/getByCode/{code}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Courses getByCode(@PathVariable String code) {
        return coursesService.getByCode(code);
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Courses getById(@PathVariable Long id) throws Exception {
        return coursesService.getById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Courses addCourses(@RequestBody @Valid CoursesDTO courses) {
        return coursesService.saveCourses(courses);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Courses updateCourses(@PathVariable Long id, @RequestBody @Valid CoursesDTO courses) throws Exception {
        return coursesService.updateCourses(courses, id);
    }

    @PatchMapping("/patch/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Courses patchCourses(@PathVariable Long id, @RequestBody Map<String, Object> courses) throws Exception {
        return coursesService.patchCourses(id, courses);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteCourses(@PathVariable Long id)  {
        return coursesService.deleteCourses(id);
    }

}