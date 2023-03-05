package com.payment.db.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.payment.db.entities.Transactions;

public interface TransactionsRepo extends JpaRepository<Transactions, Serializable> {
	
	@Query(value = "SELECT t FROM Transactions t WHERE t.transactionTo = ?1 OR t.transactionBy = ?1")
	List<Transactions> findByTransactionByOrTransactionTo(Long transactionBy);

}
