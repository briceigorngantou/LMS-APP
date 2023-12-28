package com.ams.users.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 1000)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Column(nullable = false)
    private LocalDate  dateOfBirth;

    @Column(nullable = false)
    private LocalDate  createdAt;

    @Column(nullable = false)
    private LocalDate  updatedAt;

    public Users(Long id, String username, String email, String fullName,
                 LocalDate dateOfBirth,LocalDate createdAt,LocalDate updatedAt) {
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setRole(ROLE.STUDENT);
        this.setDateOfBirth(dateOfBirth);
        this.setFullName(fullName);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);

    }

    public Users() {

    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    public boolean isPersisted() {
        return id != null;
    }

    public boolean isAccountNonExpired(){
        return  true;
    }
    public boolean isAccountNotLocked(){
        return true;
    }

    public  boolean isCredentialsNonExpired(){
        return  true;
    }

    public boolean isEnable(){
        return true;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
