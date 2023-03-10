package com.payment.rest.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserLoginRequest {
	
	@NotEmpty(message = "Username can not be null or empty.")
	private String userName;
	
	@NotEmpty(message = "Password can not be null or empty.")
	private String pin;

}
