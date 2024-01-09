package com.jwt.Controller;

import com.jwt.helper.UserDetailsServiceImpl;
import com.jwt.models.JwtRequest;
import com.jwt.models.JwtResponse;
import com.jwt.security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper jwtHelper;

    private Logger logger= LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails =userDetailsServiceImpl.loadUserByUsername(request.getEmail());
        String token =this.jwtHelper.generateToken(userDetails);

        JwtResponse response=JwtResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email,password);
        try {
            manager.authenticate(authentication);
        }catch (BadCredentialsException e){
            throw new RuntimeException("Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Credentials Invalid";
    }
    

}
