package com.payment.rest.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.db.entities.ERole;
import com.payment.db.entities.Role;
import com.payment.db.entities.User;
import com.payment.db.repository.RoleRepository;
import com.payment.db.repository.TransactionsRepo;
import com.payment.db.repository.UserRepository;
import com.payment.rest.dto.responses.FailureResponse;
import com.payment.rest.dto.responses.LoginResponse;
import com.payment.rest.dto.responses.UserIdResponse;
import com.payment.rest.dto.responses.UserResponse;
import com.payment.security.jwt.JwtUtils;
import com.payment.security.service.UserDetailsImpl;
import com.payment.rest.dto.request.PatchRequest;
import com.payment.rest.dto.request.UserLoginRequest;
import com.payment.rest.dto.request.UserRequest;
import com.payment.utils.AppUtils;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TransactionsRepo transactionsRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest signUpRequest) {
		if (userRepository.existsByUserName(signUpRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new UserResponse(1, "FAILURE", "Error: Username is already taken!"));
		}
		// Create new user's account
		User user = new User();
		user.setUserName(signUpRequest.getUserName());
		user.setUserDescription(signUpRequest.getUserDescription());
		user.setStatus("Active");
		user.setBalance(0.0);
		String str = AppUtils.generatePin();
		user.setUserPin(encoder.encode(str));
		user.setCreatedBy("Admin");
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER);
		roles.add(userRole);
		user.setRoles(roles);
		User save = userRepository.save(user);
		UserResponse response = new UserResponse(); 
		if(save.getUserId() != null) {
			BeanUtils.copyProperties(save, response);
			response.setStatusCode(0);
			response.setStatusDescription("SUCCESS");
			response.setErrorDescription("");
			response.setUserPin(str);
		}
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPin()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new LoginResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
	}
	
	
	@GetMapping("/all")
	public String getAllResult() {
		return "Hi from secured payment api";
	}
	
	
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getUser(@PathVariable(name = "userId") Long id) {
		Optional<User> findByUserId = userRepository.findByUserId(id, AppUtils.ACTIVE_STATUS);
		if(findByUserId.isPresent()) {
			User user = findByUserId.get();
			UserIdResponse response = new UserIdResponse();
			response.setTransactions(transactionsRepo.findByTransactionByOrTransactionTo(id));
			BeanUtils.copyProperties(user, response);
			return new ResponseEntity<UserIdResponse>(response, HttpStatus.OK);
		}
		return new ResponseEntity<FailureResponse>(new FailureResponse(String.format("User with given user id : %d not found!", id)), HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping("/user/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") Long id) {
		Optional<User> findByUserId = userRepository.findByUserId(id, AppUtils.ACTIVE_STATUS);
		if(findByUserId.isPresent()) {
			User user = findByUserId.get();
			user.setStatus("Inactive");
			user.setUpdatedBy("User");
			userRepository.save(user);
			return new ResponseEntity<FailureResponse>(new FailureResponse(0, "SUCCESS", String.format("User with given user id : %d has been deleted successfully!", id)), HttpStatus.OK);
		}
		return new ResponseEntity<FailureResponse>(new FailureResponse(String.format("User with given user id : %d not found!", id)), HttpStatus.BAD_REQUEST);
	}
	
	
	@PatchMapping("/user/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@PathVariable(name = "userId") Long id,
			@RequestBody PatchRequest request) {
		Optional<User> findByUserId = userRepository.findByUserId(id, AppUtils.ACTIVE_STATUS);
		if(findByUserId.isPresent()) {
			User user = findByUserId.get();
			user.setBalance(request.getBalance());
			user.setUpdatedBy("User");
			userRepository.save(user);
			return new ResponseEntity<FailureResponse>(new FailureResponse(0, "SUCCESS", "User's balance updated successfully!"), HttpStatus.OK);
		}
		return new ResponseEntity<FailureResponse>(new FailureResponse(String.format("User with given user id : %d not found!", id)), HttpStatus.BAD_REQUEST);
	}
	

}
