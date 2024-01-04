package com.ams.enrollmentCourse.dto;

import javax.validation.constraints.NotBlank;

import com.ams.enrollmentCourse.entity.Enroll;

public class EnrollDTO {

    @NotBlank(message = "userId is required")
    private Long userId;

    @NotBlank(message = "courseId is required")
    private Long courseId;

    public EnrollDTO(Enroll enroll) {
        this.setUserId(enroll.getUserId());
        this.setCourseId(enroll.getCourseId());
    }

    public EnrollDTO() {

    }

    public Enroll toEnrollEntity(EnrollDTO enroll) {
        Enroll newEnroll = new Enroll();
        newEnroll.setUserId(enroll.getUserId());
        newEnroll.setCourseId(enroll.getCourseId());
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
}
