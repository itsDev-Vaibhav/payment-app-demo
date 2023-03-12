package com.payment.rest.dto.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString 
@EqualsAndHashCode
public class FailureResponse {
	
	private Integer statusCode;
	private String statusDescription;
	private String errorDescription;
	
	
	public FailureResponse() {
		this.statusCode = 1;
		this.statusDescription = "FAILURE";
	}


	public FailureResponse(String errorDescription) {
		this();
		this.errorDescription = errorDescription;
	}
	
	
	public FailureResponse(Integer statusCode, String errorDescription) {
		this();
		this.statusCode = statusCode;
		this.errorDescription = errorDescription;
	}


	public FailureResponse(Integer statusCode, String statusDescription, String errorDescription) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.errorDescription = errorDescription;
	}
}
