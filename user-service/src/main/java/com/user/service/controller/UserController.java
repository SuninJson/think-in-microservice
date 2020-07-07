package com.user.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id) {
        System.out.println(port);
        return "User[" + id + "]";
    }
}
