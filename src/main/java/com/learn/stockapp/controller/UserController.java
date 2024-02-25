package com.learn.stockapp.controller;

/* create RestController  for 
 * 1. user registration
 * 2. user login
 * autowire the UserService
 *  */

import com.learn.stockapp.exceptions.UserAlreadyExistException;
import com.learn.stockapp.model.User;
import com.learn.stockapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserAlreadyExistException {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User validUser = userService.validateUser(user);
        if (validUser != null) {
            return ResponseEntity.ok("Logged In SUccessfully..");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials..   ");
    }
}
