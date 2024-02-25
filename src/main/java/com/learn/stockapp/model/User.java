package com.learn.stockapp.model;

/* create with following fields
 * emailId,password,username,mobile
 * Use Lombok to generate the getters and setters, toString
 * Use Lombok to generate the constructor with all fields , no argus constructor
 * Use  @Document to map the class to the collection in MongoDB
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String emailId;
    private String password;
    private String username;
    private String mobile;
}