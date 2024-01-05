package com.ams.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.users.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    /**
     * @param username
     * @return Users
     */
    Users findByUsername(String username);

    /**
     * @param userName
     * @param password
     * @return Users
     */
    Users findByUsernameAndPassword(String userName, String password);

    /**
     * @param email
     * @return Users
     */
    Users findByEmail(String email);
}
