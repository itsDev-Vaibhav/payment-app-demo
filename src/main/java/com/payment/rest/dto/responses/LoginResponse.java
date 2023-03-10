package com.payment.rest.dto.responses;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private List<String> roles;
	
	
	public LoginResponse(String token, Long id, String username, List<String> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
	

}
