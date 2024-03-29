package com.jwt.Controller;

import com.jwt.models.Users;
import com.jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok("Access");

    }

    @PostMapping("/register")
    public ResponseEntity addUser(@RequestBody Users user){
        return userService.addUser(user);
    }

}
