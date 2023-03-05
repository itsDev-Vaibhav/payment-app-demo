package com.payment.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ROLES")
@Data
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private ERole roleName;
	
	@Column(length = 50,nullable = false)
	private String roleDescription;

}
