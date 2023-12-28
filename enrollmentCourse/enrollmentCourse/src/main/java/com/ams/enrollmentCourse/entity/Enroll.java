package com.ams.enrollmentCourse.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "enroll")
public class Enroll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Progress status;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private LocalDate  createdAt;

    @Column(nullable = false)
    private LocalDate  updatedAt;

    public Enroll(Long id, Long courseId, Long userId,Progress status,
                  LocalDate createdAt, LocalDate updatedAt) {
        this.setId(id);
        this.setCourseId(courseId);
        this.setUserId(userId);
        this.setStatus(status);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
    }

    public Enroll() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Progress getStatus() {
        return status;
    }

    public void setStatus(Progress status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
