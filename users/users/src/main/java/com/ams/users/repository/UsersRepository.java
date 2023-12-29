package com.ams.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.users.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Users findByUsernameAndPassword(String userName, String password);
    Users findByEmail(String email);
}
