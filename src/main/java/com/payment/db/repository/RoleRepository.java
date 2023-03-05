package com.payment.db.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.db.entities.ERole;
import com.payment.db.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Serializable> {

	Role findByRoleName(ERole roleUser);

}
