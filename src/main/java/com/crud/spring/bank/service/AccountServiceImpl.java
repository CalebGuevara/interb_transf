package com.crud.spring.bank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.spring.bank.dao.AccountDAO;
import com.crud.spring.bank.dto.AccountDTO;
import com.crud.spring.bank.entity.Account;
import com.crud.spring.bank.mapper.AccountMapper;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Override
	public AccountDTO getAccountByNumber(String accountNumber) {
		
		Account account = accountDAO.findByAccountNumber(accountNumber);

        if (account != null) {
            return accountMapper.toDTO(account);
        } else {
            throw new IllegalArgumentException("Account " + accountNumber + " not found");
        }
	}

	@Override
	public AccountDTO getAccountById(Long accountId) {
		
		Account account = accountDAO.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with id " + accountId + " not found"));
        return accountMapper.toDTO(account);
		
	}

	@Override
	public List<AccountDTO> getAllAccounts() {
		
		List<Account> accounts = accountDAO.findAll();
		
		List<AccountDTO> accountDTOs = new ArrayList<>();

        for (Account account : accounts) {
            accountDTOs.add(accountMapper.toDTO(account));
        }

        return accountDTOs;
		
	}

}