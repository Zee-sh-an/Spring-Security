package com.jwt.helper;

import com.jwt.models.Users;
import com.jwt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object user = userRepository.findByEmail(username);

        if (user==null){
            throw new UsernameNotFoundException("could not found user  !!");
        }

        return new  CustomUserDetatils((Users) user);
    }
}
