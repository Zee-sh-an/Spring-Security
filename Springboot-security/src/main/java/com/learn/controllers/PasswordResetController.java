package com.learn.controllers;

import com.learn.models.UserPassword;
import com.learn.services.OtpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset")
public class PasswordResetController {

    @Autowired
    private OtpService otpService;

    @GetMapping("/generateOtp")
    public ResponseEntity generateOtp(@RequestParam String userName){
        return otpService.generateOtp(userName);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity resetPassword(@Valid @RequestBody UserPassword userPassword,@RequestParam String userName, @RequestParam int otp){
        return otpService.resetPassword(userPassword,userName,otp);
    }

}
