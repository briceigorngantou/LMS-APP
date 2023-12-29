package com.ams.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/home")
@RestController
public class HomeController {

    @Autowired
    public HomeController() {
    }

    @GetMapping("/test")
    public String getMessage() {
        return "Hello Wolrd";
    }

}
