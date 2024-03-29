package com.learn;

import com.learn.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		User user= new User();
//		user.setEmail("vs@gmail.com");
//		user.setPassword(bCryptPasswordEncoder.encode("vishwajeet"));
//		user.setName("vishwajeet");
//		user.setRole("ROLE_ADMIN");
//
//		User user= new User();
//		user.setEmail("zk@gmail.com");
//		user.setPassword(this.bCryptPasswordEncoder.encode("zeeshan"));
//		user.setName("khan");
//		user.setRole("ROLE_ADMIN");

//		this.userRepository.save(user1);

//		this.userRepository.save(user);


	}
}
