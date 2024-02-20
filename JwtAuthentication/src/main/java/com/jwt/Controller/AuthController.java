package com.jwt.Controller;

import com.jwt.helper.UserDetailsServiceImpl;
import com.jwt.models.JwtRequest;
import com.jwt.models.JwtResponse;
import com.jwt.models.Users;
import com.jwt.repo.TokenRepository;
import com.jwt.repo.UserRepository;
import com.jwt.security.JwtHelper;
import com.jwt.token.Token;
import com.jwt.token.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    String token;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails =userDetailsServiceImpl.loadUserByUsername(request.getEmail());

        Users user = userRepository.findByEmail(request.getEmail());

        String token =this.jwtHelper.generateToken((userDetails));

        JwtResponse response=JwtResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername()).build();
        revokeAllUserTokens(user);
        savedUserToken(user,token);

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

    private void savedUserToken(Users user,String token){

            var jwtToken = Token.builder()
                    .token(token)
                    .tokenType(TokenType.BEARER)
                    .revoked(false)
                    .expired(false)
                    .user(user)
                    .build();
            tokenRepository.save(jwtToken);

        }

    private void revokeAllUserTokens(Users user){

        var validUserTokens = tokenRepository.findAllValidTokensByUsers(user.getUserId());

        if (validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Credentials Invalid";
    }
}