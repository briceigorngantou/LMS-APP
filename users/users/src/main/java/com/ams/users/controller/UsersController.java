package com.ams.users.controller;

import java.util.*;

import com.ams.users.dto.UsersDTO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ams.users.entity.Users;
import com.ams.users.service.UsersService;

@CrossOrigin(origins = "http:localhost.com", maxAge = 3600)
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
    public List<Users> getUsers() {
        return usersService.getUsers();
    }

    @GetMapping("/getByUsername/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Users getByUsername(@PathVariable String username) {
        return usersService.getByUsername(username);
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Users getById(@PathVariable Long id) throws Exception {
        return usersService.getById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Users addUser(@RequestBody @Valid UsersDTO users) {
        return usersService.saveUser(users);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Users updateUser(@PathVariable Long id, @RequestBody @Valid UsersDTO users) throws Exception {
        return usersService.updateUser(users, id);
    }

    @PatchMapping("/patch/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Users patchUser(@PathVariable Long id, @RequestBody Map<String, Object> users) throws Exception {
        return usersService.patchUser(id, users);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteUser(@PathVariable Long id)  {
        return usersService.deleteUser(id);
    }

}