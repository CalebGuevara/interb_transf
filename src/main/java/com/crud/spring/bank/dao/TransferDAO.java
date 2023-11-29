package com.crud.spring.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.spring.bank.entity.Transfer;

@Repository
public interface TransferDAO extends JpaRepository<Transfer, Long>{
	
}
