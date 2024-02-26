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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



import java.util.Date;

import javax.crypto.SecretKey;



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
            String token = generateToken(validUser.getEmailId());
            return ResponseEntity.ok("Logged In SUccessfully..Token: " + token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials..   ");
    }

    //   create  method  generateToken  to generate jwt token  , take secret key is "CTS-SUCCESS"
    // and   pass  emailId as an argument, 
    // return type is String
    //use Jwts.builder() to generate token

 private String generateToken(String emailId) {
        // SecretKey secretKey = Keys.hmacShaKeyFor("CTS-SECRET-KEY-SUCCESS".getBytes());
        // return Jwts.builder()
        //         .setSubject(emailId)
        //         .setIssuedAt(new Date())
        //         .signWith(secretKey,SignatureAlgorithm.HS256)
        //         .compact();

         
        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "CTS-BATCH1-SECRET")
                .compact();
    }
}
