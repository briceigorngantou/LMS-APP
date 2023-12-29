package com.ams.users.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ams.users.dto.LoginBody;
import com.ams.users.dto.LoginResponse;
import com.ams.users.dto.UsersDTO;
import com.ams.users.exception.UserAlreadyExistsException;
import com.ams.users.service.UsersService;

/**
 * Rest Controller for handling authentication requests.
 */
@RestController
@CrossOrigin(origins = "http://localhost:8086", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    /** The user service. */
    private UsersService userService;

    /**
     * Spring injected constructor.
     * @param userService
     */
    public AuthController(UsersService userService) {
        this.userService = userService;
    }

    /**
     * Post Mapping to handle registering users.
     * @param UsersDTO The registration information.
     * @return Response to front end.
     */
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid UsersDTO users) {
        try {
            System.out.print(users.getUsername());
            System.out.print(users.getEmail());
            userService.registerUser(users);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Post Mapping to handle user logins to provide authentication token.
     * @param loginBody The login information.
     * @return The authentication token if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = null;
        System.out.print(loginBody.getUsername());
        System.out.print(loginBody.getPassword());
        try {
            jwt = userService.loginUser(loginBody);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }

//    /**
//     * Gets the profile of the currently logged-in user and returns it.
//     * @param user The authentication principal object.
//     * @return The user profile.
//     */
//    @GetMapping("/currentUser")
//    public Users getLoggedInUserProfile(@AuthenticationPrincipal Users user) {
//        System.out.print(user.getUsername());
//        System.out.print(user.getEmail());
//        return user;
//    }

}

