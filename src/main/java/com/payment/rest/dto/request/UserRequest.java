package com.payment.rest.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserRequest {
	
	@NotEmpty(message = "User name can not be null or empty.")
	private String userName;
	
	@NotEmpty(message = "User description can not be null or empty.")
	private String userDescription;
}
