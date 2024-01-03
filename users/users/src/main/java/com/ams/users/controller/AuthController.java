package com.ams.users.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ams.users.dto.LoginBody;
import com.ams.users.dto.LoginResponse;
import com.ams.users.dto.ResponseBody;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseBody> registerUser(@RequestBody @Valid UsersDTO users) throws Exception {
        try {
            Users user = userService.registerUser(users);
            ResponseBody response = new ResponseBody();
            response.setData(user);
            response.setMessage("User created Successfully");
            response.setError(null);
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserAlreadyExistsException ex) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(ex.getMessage());
            response.setError(ex);
            response.setStatusCode(409);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setError(e);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginBody user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                throw new UserNotFoundException("Invalid username or password");
            }
            Users userData = userService.getUserByNameAndPassword(user.getUsername(), user.getPassword());
            if (userData == null) {
                throw new UserNotFoundException("Invalid username or password");
            }
            LoginResponse response = new LoginResponse();
            response.setJwt(jwtGenerator.generateJWT(user.getUsername()));
            response.setMessage("Successfull Authentication");
            response.setSuccess(true);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            // System.out.print("Error Messages : " + e.getMessage());
            LoginResponse response = new LoginResponse();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

}
