package com.payment.runners;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void run(String... args) throws Exception {
//		Role role = new Role();
//		role.setRoleName(ERole.ROLE_ADMIN);
//		role.setRoleDescription("role for Admin");
//		roleRepository.save(role);
//		
//		Role role1 = new Role();
//		role1.setRoleName(ERole.ROLE_USER);
//		role1.setRoleDescription("role for Udmin");
//		roleRepository.save(role1);
//		
//		
//		User user = new User();
//		user.setUserName("admin");
//		user.setUserDescription("Administrator");
//		user.setStatus("Active");
//		user.setBalance(0.0);
//		user.setUserPin(encoder.encode("0000"));
//		user.setCreatedBy("System");
//		Role userRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN);
//		Set<Role> roles = new HashSet<>();
//		roles.add(userRole);
//		user.setRoles(roles);
//		userRepository.save(user);
	}

}
