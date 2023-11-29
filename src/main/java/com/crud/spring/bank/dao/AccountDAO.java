package com.crud.spring.bank.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.spring.bank.entity.Account;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long>{
	
	Account findByAccountNumber(String accountNumber);
	
}
