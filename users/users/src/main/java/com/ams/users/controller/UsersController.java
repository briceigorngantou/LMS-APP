package com.ams.users.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.ams.users.dto.ResponseBody;
import com.ams.users.dto.UsersDTO;
import com.ams.users.entity.Users;
import com.ams.users.exception.UserNotFoundException;
import com.ams.users.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> getUsers() throws InternalServerError {
        ResponseBody response = new ResponseBody();
        response.setData(usersService.getUsers());
        response.setMessage("Success");
        response.setError(null);
        response.setStatusCode(201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getByUsername/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> getByUsername(@PathVariable String username) throws UserNotFoundException {
        Users user = usersService.getByUsername(username);
        ResponseBody response = new ResponseBody();
        response.setData(user);
        response.setMessage("Success");
        response.setError(null);
        response.setStatusCode(201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> getById(@PathVariable Long id) throws Exception {
        try {
            Users user = usersService.getById(id);
            ResponseBody response = new ResponseBody();
            response.setData(user);
            response.setMessage("Success");
            response.setError(null);
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalServerError e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setError(e);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> updateUser(@PathVariable Long id, @RequestBody @Valid UsersDTO users)
            throws Exception {
        try {
            Users user = usersService.updateUser(users, id);
            ResponseBody response = new ResponseBody();
            response.setData(user);
            response.setMessage("Success");
            response.setError(null);
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalServerError e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setError(e);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/patch/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> users)
            throws Exception {
        try {
            Users user = usersService.patchUser(id, users);
            ResponseBody response = new ResponseBody();
            response.setData(user);
            response.setMessage("Success");
            response.setError(null);
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalServerError e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setError(e);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ResponseBody> deleteUser(@PathVariable Long id) throws Exception {
        try {
            ResponseEntity<String> res = usersService.deleteUser(id);
            ResponseBody response = new ResponseBody();
            response.setData(res);
            response.setMessage("Success");
            response.setError(null);
            response.setStatusCode(202);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalServerError e) {
            ResponseBody response = new ResponseBody();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setError(e);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}