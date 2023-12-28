package com.ams.enrollmentCourse.dto;

import com.ams.enrollmentCourse.entity.Enroll;
import com.ams.enrollmentCourse.entity.Progress;
import javax.validation.constraints.*;

import java.time.LocalDate;

public class EnrollDTO {

    @NotBlank(message = "status is required")
    private Progress status;

    @NotBlank(message = "userId is required")
    private Long userId;

    @NotBlank(message = "courseId is required")
    private  Long courseId;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public EnrollDTO(Enroll enroll) {
        this.setUserId(enroll.getUserId());
        this.setCourseId(enroll.getCourseId());
        this.setCreatedAt(enroll.getCreatedAt());
        this.setUpdatedAt(enroll.getUpdatedAt());
    }

    public EnrollDTO(){

    }

    public Enroll toEnrollEntity(EnrollDTO enroll) {
        Enroll newEnroll= new Enroll() ;
        newEnroll.setUserId(enroll.getUserId());
        newEnroll.setCourseId(enroll.getCourseId());
        newEnroll.setCreatedAt(enroll.getCreatedAt());
        newEnroll.setUpdatedAt(enroll.getUpdatedAt());
        return newEnroll;
    }


    public Long getCourseId() {
        return this.courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Progress getStatus() {
        return status;
    }

    public void setStatus(Progress status) {
        this.status = status;
    }
}
