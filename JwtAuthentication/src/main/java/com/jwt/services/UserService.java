package com.jwt.services;

import com.jwt.models.Error;
import com.jwt.models.Users;
import com.jwt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity getAllusers() {

        try {
            List<Users> users = userRepository.findAll();
            if (users.isEmpty()) {

                Error error = Error.builder().code(HttpStatus.NOT_FOUND.getReasonPhrase()).message("Sorry there is no user").build();
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            Error error = Error.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("Fetching of users failed").build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity addUser(Users user) {

        try {
            if (ObjectUtils.isEmpty(user.getName())) {
                Error error = Error.builder().code(HttpStatus.NO_CONTENT.getReasonPhrase()).message("Sorry user is null").build();
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users user1 = userRepository.save(user);
            return new ResponseEntity<>(user1, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            Error error = Error.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("adding user failed").build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
