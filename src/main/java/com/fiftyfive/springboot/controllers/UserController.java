package com.fiftyfive.springboot.controllers;

import com.fiftyfive.springboot.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {

    @GetMapping
    public List<User> getAllUsers() {

        return List.of(new User("Raman", "Mehta", 1),
                new User("Aman", "Mehta", 2),
                new User("Nial", "Nit", 3));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody User user) {
        log.info("Added new User {}", user);
    }

    @PutMapping
    public User updateUserDetails(@RequestBody User user) {
        log.info("Update User details {}", user);
        return user;
    }
}
