package com.payment.rest.dto.responses;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
	
	private Integer statusCode;
	private String statusDescription;
	private String errorDescription;
	private Long userId;
	private String userPin;
	private String userName;
	private String userDescription;
	private String status;
	private LocalDateTime userSince;
	
	public UserResponse(Integer statusCode, String statusDescription, String errorDescription) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.errorDescription = errorDescription;
	}


	public UserResponse(Integer statusCode, String statusDescription, String errorDescription, Long userId,
			String userPin, String userName, String userDescription, String status, LocalDateTime userSince) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.errorDescription = errorDescription;
		this.userId = userId;
		this.userPin = userPin;
		this.userName = userName;
		this.userDescription = userDescription;
		this.status = status;
		this.userSince = userSince;
	}
}
