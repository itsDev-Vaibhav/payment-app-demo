package com.payment.rest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.db.entities.Transactions;
import com.payment.db.entities.User;
import com.payment.db.repository.TransactionsRepo;
import com.payment.db.repository.UserRepository;
import com.payment.rest.dto.request.TransactionRequest;
import com.payment.rest.dto.responses.FailureResponse;
import com.payment.rest.dto.responses.TransactionResponse;
import com.payment.rest.exceptions.InsufficientBalanceException;
import com.payment.rest.service.TransactionService;
import com.payment.security.service.UserDetailsImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService tService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionsRepo transactionsRepo;
	
	@PostMapping("/transfer")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> doTransactions(@Valid @RequestBody TransactionRequest request) throws InsufficientBalanceException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		if(request.getTo() == userDetails.getId()) {
			return new ResponseEntity<FailureResponse>(new FailureResponse(1, "FAILURE", "To and From id cannot be same."), HttpStatus.BAD_REQUEST);
		}
		Optional<User> findByUserId = userRepository.findByUserId(userDetails.getId(), "Active");
		if(findByUserId.isPresent()) {
			User user = findByUserId.get();
			Optional<User> findByUserToId = userRepository.findByUserId(request.getTo(), "Active");
			if(findByUserToId.isPresent()) {
				User userTo = findByUserToId.get();
				if(user.getBalance() - request.getAmount() >= 0 || user.getUserId() == 1) {
					user.setBalance(user.getBalance() - request.getAmount());
					user.setUpdatedBy("Transaction");
					userTo.setBalance(userTo.getBalance() + request.getAmount());
					userTo.setUpdatedBy("Transaction");
					Transactions transactions = new Transactions();
					transactions.setTransactionTo(request.getTo());
					transactions.setAmount(request.getAmount());
					transactions.setTranasactionDescription(request.getTranasactionDescription());
					transactions.setTransactionBy(userDetails.getId());
					TransactionResponse response = new TransactionResponse(0, "SUCCESS", "");
					try {
						 Transactions save = transactionsRepo.save(transactions);
						 userRepository.save(user);
						 userRepository.save(userTo);
						 response.setTransactionId(save.getTransactionId());
						 response.setTransactionTimestamp(save.getTransactionTimestamp());
					} catch (Exception e) {
					       TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				    }
					response.setFrom(user.getUserId());
					response.setFromBalance(user.getBalance());
					response.setTo(userTo.getUserId());
					response.setToBalance(userTo.getBalance());
					response.setAmount(request.getAmount());
					return new ResponseEntity<TransactionResponse>(response, HttpStatus.OK);
				}
				return new ResponseEntity<FailureResponse>(new FailureResponse(1, "FAILURE", "Not sufficient balance."), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<FailureResponse>(new FailureResponse(1, "FAILURE", "From id not found!"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FailureResponse>(new FailureResponse(1, "FAILURE", "From id not found!"), HttpStatus.BAD_REQUEST);
	}

}
