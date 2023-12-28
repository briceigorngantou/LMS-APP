package com.ams.enrollmentCourse.controller;

import com.ams.enrollmentCourse.dto.EnrollDTO;
import com.ams.enrollmentCourse.entity.Enroll;
import com.ams.enrollmentCourse.entity.Progress;
import com.ams.enrollmentCourse.service.EnrollService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http:localhost.com", maxAge = 3600)
@RestController
@RequestMapping("/api/enroll")
public class EnrollController {

    private final EnrollService enrollService;

    @Autowired
    public EnrollController(EnrollService enrollService) {
        this.enrollService = enrollService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Enroll> getEnroll() {
        return enrollService.getEnroll();
    }

    @GetMapping("/getByStatus/{status}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Enroll getByStatus(@PathVariable Progress status) {
        return enrollService.getByStatus(status);
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Enroll getById(@PathVariable Long id) throws Exception {
        return enrollService.getById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Enroll addUser(@RequestBody @Valid EnrollDTO enroll) {
        return enrollService.saveEnroll(enroll);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Enroll updateUser(@PathVariable Long id, @RequestBody @Valid EnrollDTO enroll) throws Exception {
        return enrollService.updateEnroll(enroll, id);
    }

    @PatchMapping("/patch/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Enroll patchUser(@PathVariable Long id, @RequestBody Map<String, Object> enroll) throws Exception {
        return enrollService.patchEnroll(id, enroll);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteUser(@PathVariable Long id)  {
        return enrollService.deleteEnroll(id);
    }

}