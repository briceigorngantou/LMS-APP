package com.ams.users.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ams.users.entity.ROLE;
import com.ams.users.entity.Users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UsersDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Role is required")
    @Email(message = "Please provide a valid role")
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @NotBlank(message = "FullName is required")
    @Size(min = 3, max = 20, message = "FullName must be between 3 and 20 characters")
    private String fullName;

    @NotBlank(message = "Age is required")
    private Integer age;

    // @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(min = 6, max = 32)
    private String password;

    public UsersDTO(Users user) {
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setFullName(user.getFullName());
        this.setRole(user.getRole());
        this.setAge(user.getAge());
    }

    public UsersDTO() {

    }

    public Users toUsersEntity(UsersDTO user) {
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setRole(user.getRole());
        newUser.setEmail(user.getEmail());
        newUser.setAge(user.getAge());
        newUser.setFullName(user.getFullName());
        return newUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
