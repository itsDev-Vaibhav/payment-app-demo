package com.payment.runners;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.payment.db.entities.ERole;
import com.payment.db.entities.Role;
import com.payment.db.entities.User;
import com.payment.db.repository.RoleRepository;
import com.payment.db.repository.UserRepository;


@Component
public class AppCommandLineRunner implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUserName("admin");
		user.setUserDescription("Administrator");
		user.setStatus("Active");
		user.setBalance(0.0);
		user.setUserPin("0000");
		user.setCreatedBy("System");
		
//		Role userRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN);
//		Set<Role> role = new HashSet<>();
//		role.add(userRole);
//		user.setRoles(role);
//		userRepository.save(user);
	}

}
