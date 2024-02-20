package com.learn.controllers;

import com.learn.models.User;
import com.learn.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/public")
    public ResponseEntity<String> normalUser(){
        return ResponseEntity.ok("yes I am normal user");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("yes I am admin");
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody User user,@PathVariable int id){
        return userService.updateUser(user,id);
    }

}
