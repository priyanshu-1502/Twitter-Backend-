package com.oop.major_assignment_twitter.Service;

import com.oop.major_assignment_twitter.entity.User;

import java.util.List;

public interface UserService {
    String login(String email, String password);
    String signup(String email, String name, String password);
    User getUserDetails(int userID);
    List<User> getAllUsers();
}