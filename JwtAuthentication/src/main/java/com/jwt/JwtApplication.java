package com.jwt;

import com.jwt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
//
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

//		Users user= new Users();
//		user.setEmail("zk@gmail.com");
//		user.setPassword(bcryptPasswordEncoder.encode("zeeshan"));
//		user.setName("zeeshan");
//		user.setRole("ROLE_ADMIN");
//
//		userRepository.save(user);

	}
}
