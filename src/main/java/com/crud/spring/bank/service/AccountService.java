package com.crud.spring.bank.service;

import java.util.List;

import com.crud.spring.bank.dto.AccountDTO;

public interface AccountService {
	
	AccountDTO getAccountByNumber(String accountNumber);
	
    AccountDTO getAccountById(Long accountId);
    
    List<AccountDTO> getAllAccounts();
    
}
