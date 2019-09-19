package com.timemanager.core.src.service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.timemanager.core.src.constant.UserType;
import com.timemanager.core.src.dto.AuthResponseDto;
import com.timemanager.core.src.dto.UserRequestDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.model.User;
import com.timemanager.core.src.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public static final Pattern pattern = 
    Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
        Pattern.CASE_INSENSITIVE);

    public User getUserByEmail(String email, boolean trigger) {
        User user = null;

        if (!email.isEmpty()) {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email.toLowerCase()));            
            List<User> users = userRepository.find(query);
            if (users.size() == 0) {
                if (trigger) {
                    throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Invalid email or password");             
                }
            } else 
                user = users.get(0);
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid value for email");     
        }
        return user;
    }

    public UserResponseDto createUser(UserRequestDto in) {
        User user = null;
        String userID = "";
        Matcher matcher = pattern.matcher(in.getEmail());

        if (!matcher.matches() || in.getFirstName().isEmpty() || in.getLastName().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Incorrect value for mail");     
        } else if (getUserByEmail(in.getEmail(), false) != null) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "This email is already used");     
        } else {
            user = new User();
            userID = "USR" + UUID.randomUUID().toString();
            user.setUserID(userID);
            user.setEmail(in.getEmail().toLowerCase());
            user.setFirstName(in.getFirstName());
            user.setLastName(in.getLastName());
            user.setPassword(in.getPassword());
            user.setType(in.getType().name());
            userRepository.create(user);
        }
        return new UserResponseDto(user.getUserID(), user.getEmail(), user.getFirstName(),
            user.getLastName(), UserType.valueOf(user.getType()));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(String userID, User in) {
        Matcher matcher = pattern.matcher(in.getEmail());
        User user = getUserById(userID, true);

        if (user != null && !matcher.matches() && in.getFirstName().isEmpty() && in.getLastName().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");
        } else {
            user.setEmail(in.getEmail());
            user.setFirstName(in.getFirstName());
            user.setLastName(in.getLastName());
            //user.setType(in.getType());
            //user.setPassword(in.getPassword());
            userRepository.update(user);
        }
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public User getUserById(String userID, boolean trigger) {
        List<User> users = null;

        if (userID.isEmpty()) {
            if (trigger) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Invalid parameters");     
            } else {
                return null;
            }
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("userID").is(userID));            

            users = userRepository.find(query);
        }
        
        if (users == null || users.size() == 0) {
            if (trigger) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No user found");    
            } else {
                return null;
            }
        }

        return users.get(0);
    }

    public void deleteUser(String userID) {
        User user = getUserById(userID, true);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}