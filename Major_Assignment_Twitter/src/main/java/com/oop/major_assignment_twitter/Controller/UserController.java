package com.oop.major_assignment_twitter.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oop.major_assignment_twitter.Service.UserService;
import com.oop.major_assignment_twitter.entity.ErrorObject;
import com.oop.major_assignment_twitter.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private View error;




    static class LoginParameter{
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    static class SignupParameter{
        private String email;
        private String name;
        private String password;


        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }
    }
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginParameter request) {
        String email= request.getEmail();
        String password = request.getPassword();
        String response = userService.login(email, password);
        if(response.equals("User does not exist")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else if(response.equals("Username/Password Incorrect")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else {
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupParameter request) {
        String email= request.getEmail();
        String name = request.getName();
        String password= request.getPassword();
        String response = userService.signup(email, name, password);
        if (response.equals("Forbidden, Account already exists")) {
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else{return ResponseEntity.ok(response);}
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam int userID) {
        User user = userService.getUserDetails(userID);
        if (user != null) {
            UserResponse userResponse = new UserResponse(user.getName(), user.getUserID(), user.getEmail());
            return ResponseEntity.ok(userResponse);
        } else {
            ErrorObject errorObject = new ErrorObject("User does not exist");
            return ResponseEntity.ok(errorObject);
        }
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(user.getName(), user.getUserID(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }


    private static class UserResponse {
        private final String name;
        private final int userID;
        private final String email;

        public UserResponse(String name, int userID, String email) {
            this.name = name;
            this.userID = userID;
            this.email = email;
        }

        public int getUserID() {
            return userID;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}
