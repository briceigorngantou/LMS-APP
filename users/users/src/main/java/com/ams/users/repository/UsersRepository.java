package com.ams.users.repository;

import com.ams.users.dto.UsersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.users.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Users findByEmail(String email);
}
