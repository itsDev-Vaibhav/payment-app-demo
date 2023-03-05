package com.payment.db.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "TRANSACTIONS")
@Data
public class Transactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	@Column(nullable = false)
	private Long transactionTo;
	
	@Column(length =  100,nullable = false)
	private String tranasactionDescription;
	
	@Column(nullable = false)
	private Double amount;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime transactionTimestamp;
	
	@Column(nullable = false)
	private Long transactionBy;

}
