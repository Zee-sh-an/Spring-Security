package com.jwt.security;

import com.jwt.helper.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestHeader= request.getHeader("Authorization");
//        Bearer
        logger.info("Header : {} " , requestHeader);
        String userName = null;
        String token = null;

        if (requestHeader !=null && requestHeader.startsWith("Bearer ")){
//            looking good
            token =requestHeader.substring(7);
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

        if (userName !=null && SecurityContextHolder.getContext().getAuthentication()==null) {

//        fetch user detail from username
            UserDetails userDetails =this.userDetailsServiceImpl.loadUserByUsername(userName);
            Boolean validateToken =jwtHelper.validateToken(token,userDetails);
            if (Boolean.TRUE.equals(validateToken)){
//                set the authentication
                UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                logger.info("Validation fails !!");
            }
        }
        filterChain.doFilter(request,response);
    }
}
