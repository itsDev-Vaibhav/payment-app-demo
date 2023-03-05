package com.payment.rest.dto.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.payment.db.entities.Transactions;

import lombok.Data;


@Data
public class UserIdResponse {
	private Long userId;
	private String userName;
	private String userDescription;
	private Double balance;
	private String status;
	private LocalDateTime userSince;
	List<Transactions> transactions;
}
