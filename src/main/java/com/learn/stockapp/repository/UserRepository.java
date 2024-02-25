package com.learn.stockapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learn.stockapp.model.User;

//import Optional class from java.util
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String>{

    //create a method to retrieve user based on emailId and password
    //which returns Optional<User>
    public Optional<User> findByEmailIdAndPassword(String emailId, String password);
    




    

}
