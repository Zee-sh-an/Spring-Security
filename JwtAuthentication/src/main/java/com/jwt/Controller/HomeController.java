package com.jwt.Controller;

import com.jwt.models.User;
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
        return  userService.getAllusers();
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody User user){
        return userService.addUser(user);
    }


}
