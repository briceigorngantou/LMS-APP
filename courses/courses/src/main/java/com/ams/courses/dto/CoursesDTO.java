package com.ams.courses.dto;

import com.ams.courses.entity.Courses;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class CoursesDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 20, message = "Title must be between 3 and 20 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Code is required")
    @Size(min = 5, max = 5, message = "Code must be between 5 and 5 characters")
    private  String code;

    @NotBlank(message = "certification is required")
    private String certification;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public CoursesDTO(Courses course) {
        this.setTitle(course.getTitle());
        this.setDescription(course.getDescription());
        this.setCertification(course.getCertification());
        this.setCode(course.getCode());
        this.setCreatedAt(course.getCreatedAt());
        this.setUpdatedAt(course.getUpdatedAt());
    }

    public CoursesDTO(){

    }

    public Courses toCoursesEntity(CoursesDTO course) {
        Courses newCourse= new Courses() ;
        newCourse.setTitle(course.getTitle());
        newCourse.setDescription(course.getDescription());
        newCourse.setCertification(course.getCertification());
        newCourse.setCode(course.getCode());
        newCourse.setCreatedAt(course.getCreatedAt());
        newCourse.setUpdatedAt(course.getUpdatedAt());
        return newCourse;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
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
}
