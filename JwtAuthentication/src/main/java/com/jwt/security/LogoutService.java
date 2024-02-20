package com.jwt.security;

import com.jwt.repo.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String requestHeader= request.getHeader("Authorization");
//        Bearer
        logger.info("Header : {} " , requestHeader);
        String userName = null;
        String token = null;

        if (requestHeader !=null && requestHeader.startsWith("Bearer ")){
//            looking good
            token =requestHeader.substring(7);

            var storedToken =tokenRepository.findByToken(token)
                    .orElse(null);
            if (storedToken!=null){
                storedToken.setExpired(true);
                storedToken.setRevoked(true);
                tokenRepository.save(storedToken);
            }

            try {
                userName =this.jwtHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            }catch (MalformedJwtException e){
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            logger.info("Invalid Header Value !!");
        }
    }
}
