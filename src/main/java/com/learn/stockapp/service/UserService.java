package com.learn.stockapp.service;

/* create  a  UserService class with the following  methods 
  validateUser -  take User object as a parameter
  and returns the user Object
  if the user is valid when  emailId and password are exist in the database
 * create a method  saveUser to save the user to the database
 * Use UserAlreadyExistException if user already exist
 * 
 */

import com.learn.stockapp.exceptions.UserAlreadyExistException;
import com.learn.stockapp.model.User;
import com.learn.stockapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User validateUser(User user) {
        Optional<User> userOptional = userRepository.findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public User saveUser(User user) throws UserAlreadyExistException {
        Optional<User> userOptional = userRepository.findById(user.getEmailId());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException("User already exist");
        }
        return userRepository.save(user);
    }
}


 





 