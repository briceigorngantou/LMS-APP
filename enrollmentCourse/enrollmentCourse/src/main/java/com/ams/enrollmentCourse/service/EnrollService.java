package com.ams.enrollmentCourse.service;

import com.ams.enrollmentCourse.dto.EnrollDTO;
import com.ams.enrollmentCourse.entity.Enroll;
import com.ams.enrollmentCourse.entity.Progress;
import com.ams.enrollmentCourse.repository.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EnrollService {

    private final EnrollRepository enrollRepository;

    @Autowired
    public EnrollService(EnrollRepository enrollRepository) {
        this.enrollRepository = enrollRepository;
    }

    public List<Enroll> getEnroll() {
        return enrollRepository.findAll();
    }

    public Enroll getByStatus(Progress status) {
        return enrollRepository.findByStatus(status);
    }

    public Enroll getById(Long id) throws Exception {
        return enrollRepository.findById(id).orElseThrow(Exception::new);
    }

    public Enroll saveEnroll(EnrollDTO enroll) {
        return enrollRepository.save(new EnrollDTO().toEnrollEntity(enroll));
    }

    public Enroll updateEnroll(EnrollDTO enroll, Long id) throws Exception {
        Enroll currentEnroll = enrollRepository.findById(id).orElseThrow(Exception::new);
        currentEnroll.setUserId(enroll.getUserId());
        currentEnroll.setStatus(enroll.getStatus());
        currentEnroll.setCourseId(enroll.getCourseId());
        currentEnroll.setUpdatedAt(enroll.getUpdatedAt());
        return enrollRepository.save(currentEnroll);
    }

    public ResponseEntity<String> deleteEnroll(Long idEnroll) {
        if (this.enrollRepository.findById(idEnroll).isPresent()) {
            enrollRepository.deleteById(idEnroll);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("enroll are deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("resource not found");
    }

    public Enroll patchEnroll(Long id, Map<String, Object> enroll) throws Exception {
        Enroll currentEnroll = enrollRepository.findById(id).orElseThrow(RuntimeException::new);
        if (enroll.containsKey("status"))
            currentEnroll.setStatus((Progress) enroll.get("status"));
        if (enroll.containsKey("userId"))
            currentEnroll.setUserId((Long) enroll.get("userId"));
        if (enroll.containsKey("courseId"))
            currentEnroll.setCourseId((Long) enroll.get("courseId"));
        return enrollRepository.save(currentEnroll);
    }
}
