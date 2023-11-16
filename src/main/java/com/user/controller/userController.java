package com.user.controller;

import com.user.entity.User;
import com.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserService userservice;
    @Autowired
    private RestTemplate resttemplate;



    private static final Logger log = LoggerFactory.getLogger(userController.class);
    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long userId){
    User user = userservice.getUser(userId);
    // http://localhost:9002/contact/user/1311

    List contacts = this.resttemplate
            .getForObject("http://contact-service:9002/contact/user/" + userId, List.class);
        user.setContacts(contacts);
        return user;

    }
    @GetMapping("/string")
    public ResponseEntity<String> getStringResponse() {
        try {
            String responseString = this.resttemplate.getForObject("http://contact-service:9002/contact/user/stringOne", String.class);
            User user = this.resttemplate.getForObject("http://contact2-service:9003/contact2/user/name", User.class);

            if (user != null) {
                String responseString3 = responseString + " " + user.getName() + " " + user.getSurname();
                log.info("Main Method Called");
                return new ResponseEntity<>(responseString3, HttpStatus.OK);
            } else {
                // Handle the case where the user is not found

                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            // Handle exceptions and return an appropriate ResponseEntity
            return new ResponseEntity<>("Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/{userId}")
//    public User getUser(@PathVariable("userId") Long userId){
//
//        return this.userservice.getUser(userId);
//
//    }
}
