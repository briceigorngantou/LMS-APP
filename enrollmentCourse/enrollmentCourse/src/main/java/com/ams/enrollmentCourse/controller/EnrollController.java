package com.ams.enrollmentCourse.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ams.enrollmentCourse.dto.EnrollDTO;
import com.ams.enrollmentCourse.dto.ResponseBody;
import com.ams.enrollmentCourse.entity.Enroll;
import com.ams.enrollmentCourse.entity.Progress;
import com.ams.enrollmentCourse.exception.AlreadySubscribeException;
import com.ams.enrollmentCourse.service.EnrollService;

@CrossOrigin(origins = "http:localhost.com", maxAge = 3600)
@RestController
@RequestMapping("/api/enroll")
public class EnrollController {

    private final EnrollService enrollService;

    @Autowired
    public EnrollController(EnrollService enrollService) {
        this.enrollService = enrollService;
    }

    @GetMapping("/listOfSubscribers/{courseId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> listOfSubscribers(@PathVariable Long courseId) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(enrollService.listOfSubscribers(courseId));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
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

    @GetMapping("/listOfUnSubscribers/{courseId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> listOfUnSubscribers(@PathVariable Long courseId) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(enrollService.listOfUnSubscribers(courseId));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
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

    @GetMapping("/listOfCourses/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> listOfCourses(@PathVariable Long userId) throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(enrollService.listOfCourses(userId));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
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

    @GetMapping("/getStatus/{userId}/{courseId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> getStatus(@PathVariable Long userId, @PathVariable Long courseId)
            throws Exception {
        try {
            ResponseBody response = new ResponseBody();
            response.setData(enrollService.getStatus(userId, courseId));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
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
            response.setData(enrollService.getById(id));
            response.setMessage("Success");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
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

    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseBody> subscribe(@RequestBody @Valid EnrollDTO enroll) throws Exception {
        try {
            Enroll newSubscription = enrollService.subscribe(enroll);
            ResponseBody response = new ResponseBody();
            response.setData(newSubscription);
            response.setMessage("User successfully registered");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AlreadySubscribeException ex) {
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

    @PostMapping("/unSubscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseBody> unSubscribe(@RequestBody @Valid EnrollDTO enroll) {
        try {
            Enroll newSubscription = enrollService.unSubscribe(enroll);
            ResponseBody response = new ResponseBody();
            response.setData(newSubscription);
            response.setMessage("User successfully registered");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AlreadySubscribeException ex) {
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

    @PutMapping("/updateStatusOfCourse/{courseId}/{userId}/{status}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> updateStatusOfCourse(@PathVariable Long courseId, @PathVariable Long userId,
            @PathVariable Progress status)
            throws Exception {
        try {
            Enroll newSubscription = enrollService.updateStatusOfCourse(courseId, userId, status);
            ResponseBody response = new ResponseBody();
            response.setData(newSubscription);
            response.setMessage("status of course successfully updated");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
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
}