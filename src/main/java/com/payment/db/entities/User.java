package com.payment.db.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
@Data
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(length = 20, nullable = false)
	private String userName;
	
	@Column(length = 100, nullable = false)
	private String userDescription;
	
	@Column(name = "password", updatable = false, nullable = false)
	private String userPin;
	
	@Column(nullable = false)
	private Double balance;
	
	@Column(nullable = false)
	private String status;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime userSince; 
	
	@Column(length =  20, updatable = false)
	private String createdBy;
	
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updatedOn; 
	
	@Column(length =  20)
	private String updatedBy;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(String userName, String userDescription, String userPin, Double balance, String status,
			String createdBy) {
		this.userName = userName;
		this.userDescription = userDescription;
		this.userPin = userPin;
		this.balance = balance;
		this.status = status;
		this.createdBy = createdBy;
	}
}
