package com.crud.spring.bank.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.crud.spring.bank.dao.AccountDAO;
import com.crud.spring.bank.dto.AccountDTO;
import com.crud.spring.bank.entity.Account;
import com.crud.spring.bank.mapper.AccountMapper;

@SpringBootTest
public class AccountServiceImplTest {

	@Autowired
    private AccountService accountService;

    @MockBean
    private AccountDAO accountDAO;

    @MockBean
    private AccountMapper accountMapper;

    @Test
    public void testGetAccountByNumberAccountTrue() {

        String accountNumber = "1234567891234567";
        Account mockAccount = new Account();
        when(accountDAO.findByAccountNumber(accountNumber)).thenReturn(mockAccount);
        when(accountMapper.toDTO(mockAccount)).thenReturn(new AccountDTO());

        AccountDTO accDTO = accountService.getAccountByNumber(accountNumber);

        assertNotNull(accDTO);
    }

    @Test
    public void testGetAccountByNumberAccountFalse() {

        String accountNumber = ":)";
        when(accountDAO.findByAccountNumber(accountNumber)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> accountService.getAccountByNumber(accountNumber));
    }
    
    @Test
    public void testGetAccountByIdAccountTrue() {

        Long accountId = 1L;
        Account mockAccount = new Account();
        when(accountDAO.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(accountMapper.toDTO(mockAccount)).thenReturn(new AccountDTO());

        AccountDTO accDTO = accountService.getAccountById(accountId);

        assertNotNull(accDTO);
    }

    @Test
    public void testGetAccountByIdAccountFalse() {

        Long accountId = 404L;
        when(accountDAO.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountService.getAccountById(accountId));
    }
    
    @Test
    public void testGetAllAccounts() {
        List<Account> mockAccounts = new ArrayList<>();
        when(accountDAO.findAll()).thenReturn(mockAccounts);

        List<AccountDTO> accList = accountService.getAllAccounts();

        assertNotNull(accList);
    }

}
