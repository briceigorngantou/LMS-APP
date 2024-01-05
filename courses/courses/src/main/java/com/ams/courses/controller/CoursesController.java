package com.ams.courses.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.ams.courses.dto.CoursesDTO;
import com.ams.courses.dto.ResponseBody;
import com.ams.courses.exception.CoursesAlreadyExistsException;
import com.ams.courses.exception.CoursesNotFoundException;
import com.ams.courses.service.CoursesService;

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
    public ResponseEntity<ResponseBody> getCourses() throws Exception {
        ResponseBody response = new ResponseBody();
        response.setData(coursesService.getCourses());
        response.setMessage("Success");
        response.setStatusCode(201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> searchBy(@PathVariable String keyword) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(coursesService.searchCourseByTitleOrCode(keyword));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CoursesNotFoundException e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByTitle/{title}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> getByTitle(@PathVariable String title) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(coursesService.getByTitle(title));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CoursesNotFoundException e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByCode/{code}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> getByCode(@PathVariable String code) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(coursesService.getByCode(code));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CoursesNotFoundException e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> getById(@PathVariable Long id) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(coursesService.getById(id));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CoursesNotFoundException e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseBody> addCourses(@RequestBody @Valid CoursesDTO courses) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(coursesService.saveCourses(courses));
            response.setMessage("Course created Successfully");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CoursesAlreadyExistsException ex) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(ex.getMessage());
            response.setStatusCode(409);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> updateCourses(@PathVariable Long id, @RequestBody @Valid CoursesDTO courses)
            throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(coursesService.updateCourses(courses, id));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CoursesNotFoundException e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/patch/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> patchCourses(@PathVariable Long id, @RequestBody Map<String, Object> courses)
            throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(coursesService.patchCourses(id, courses));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CoursesNotFoundException e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> deleteCourses(@PathVariable Long id) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(coursesService.deleteCourses(id));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalServerError e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}