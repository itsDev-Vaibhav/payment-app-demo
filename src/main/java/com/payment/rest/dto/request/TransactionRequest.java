package com.payment.rest.dto.request;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class TransactionRequest {
	
	@Min(value= 1, message= "must be equal or greater than 1")  
	private Long from;
	
	@Min(value= 2, message="must be equal or greater than 2")  
	private Long to;
	
	@NotEmpty(message = "Description is required.")
	private String tranasactionDescription;
	
	@DecimalMin(value = "0.1", inclusive = true)
	private Double amount;
}
