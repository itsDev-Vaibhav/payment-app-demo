package com.payment.rest.dto.responses;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TransactionResponse {
	
	
	private Integer statusCode;
	private String statusDescription;
	private String errorDescription;
	private Long transactionId;
	private String transactionDescription;
	private Long from;
	private Double fromBalance;
	private Long to;
	private Double toBalance;
	private Double amount;
	private LocalDateTime transactionTimestamp;
	
	public TransactionResponse(int i, String string, String string2) {
		statusCode = i;
		statusDescription = string;
		errorDescription = string2;
	}
		
}
