package com.ams.courses.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String  certification;

    @Column(nullable = false)
    private LocalDate  createdAt;

    @Column(nullable = false)
    private LocalDate  updatedAt;

    public Courses(Long id, String title, String code, String description,
                   String certification, LocalDate createdAt, LocalDate updatedAt) {
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setCode(code);
        this.setCertification(certification);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);

    }

    public Courses() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
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
}
