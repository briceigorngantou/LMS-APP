package com.ams.courses.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ams.courses.entity.Courses;

public class CoursesDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 20, message = "Title must be between 3 and 20 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Code is required")
    @Size(min = 5, max = 5, message = "Code must be between 5 and 5 characters")
    private String code;

    @NotBlank(message = "certification is required")
    private String certification;

    public CoursesDTO(Courses course) {
        this.setTitle(course.getTitle());
        this.setDescription(course.getDescription());
        this.setCertification(course.getCertification());
        this.setCode(course.getCode());
    }

    public CoursesDTO() {

    }

    public Courses toCoursesEntity(CoursesDTO course) {
        Courses newCourse = new Courses();
        newCourse.setTitle(course.getTitle());
        newCourse.setDescription(course.getDescription());
        newCourse.setCertification(course.getCertification());
        newCourse.setCode(course.getCode());
        newCourse.setCreatedAt(new Date());
        newCourse.setUpdatedAt(new Date());
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
}
