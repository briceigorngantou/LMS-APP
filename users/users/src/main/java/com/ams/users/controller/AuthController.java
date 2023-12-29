package com.ams.users.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ams.users.dto.LoginBody;
import com.ams.users.dto.UsersDTO;
import com.ams.users.entity.Users;
import com.ams.users.exception.UserAlreadyExistsException;
import com.ams.users.exception.UserNotFoundException;
import com.ams.users.service.JWTService;
import com.ams.users.service.UsersService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UsersService userService;

    private JWTService jwtGenerator;

    @Autowired
    public AuthController(UsersService userService, JWTService jwtGenerator) {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody @Valid UsersDTO users) {
        try {
            userService.registerUser(users);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            System.out.print("Error message : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            System.out.print("Error message : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginBody user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                throw new UserNotFoundException("UserName or Password is Empty");
            }
            Users userData = userService.getUserByNameAndPassword(user.getUsername(), user.getPassword());
            if (userData == null) {
                throw new UserNotFoundException("UserName or Password is Invalid");
            }
            return new ResponseEntity<>(jwtGenerator.generateJWT(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            System.out.print("Error Messages : " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
