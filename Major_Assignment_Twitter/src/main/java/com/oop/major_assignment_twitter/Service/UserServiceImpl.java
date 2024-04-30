package com.oop.major_assignment_twitter.Service;

import com.oop.major_assignment_twitter.entity.User;
import com.oop.major_assignment_twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return "Login Successful";
        } else if (user == null) {
            return "User does not exist";
        } else {
            return "Username/Password Incorrect";
        }
    }

    @Override
    public String signup(String email, String name, String password) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return "Forbidden, Account already exists";
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(password);
        userRepository.save(newUser);
        return "Account Creation Successful";
    }

    @Override
    public User getUserDetails(int userID) {
        Optional<User> optionalUser = userRepository.findById(userID);
        return optionalUser.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

