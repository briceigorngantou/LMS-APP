package com.ams.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/home")
@RestController
public class HomeController {

    public HomeController() {
    }

    @GetMapping("/test")
    public String getMessage() {
        return "Hello Wolrd";
    }

}
