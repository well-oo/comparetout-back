package com.pepit.compareTout;

import com.pepit.compareTout.entity.Role;
import com.pepit.compareTout.entity.User;
import com.pepit.compareTout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class CompareToutApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args)
	{
		SpringApplication.run(CompareToutApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(userService.findByName("admin") == null){
			//userService.delete("admin");
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword("admin");
			admin.setEmail("admin@email.com");
			admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			userService.signup(admin);
		}
		if(userService.findByName("supplier") == null){
			//userService.delete("supplier");
			User supplier = new User();
			supplier.setUsername("supplier");
			supplier.setPassword("supplier");
			supplier.setEmail("supplier@email.com");
			supplier.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_SUPPLIER)));
			userService.signup(supplier);
		}
	}
}
