package com.learn.services;

import com.learn.models.Error;
import com.learn.models.User;
import com.learn.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity addUser(User user) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser.isPresent()){
                Error error = Error.builder().Code(HttpStatus.BAD_REQUEST.getReasonPhrase()).message("Email is already existed It should be Uniqe").build();
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savingUser = userRepository.save(user);
            return new ResponseEntity<>(savingUser, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            Error error = Error.builder().Code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("Error while adding the user").build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity updateUser(User user,int id) {
        try {

            Optional<User> userId = userRepository.findById(id);

            if (userId.isEmpty()) {
                Error error = Error.builder().Code(HttpStatus.NOT_FOUND.getReasonPhrase()).message("user not found").build();
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User updatingUser = userRepository.save(user);
            return new ResponseEntity<>(updatingUser, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            Error error = Error.builder().Code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("Error while updating the user").build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
