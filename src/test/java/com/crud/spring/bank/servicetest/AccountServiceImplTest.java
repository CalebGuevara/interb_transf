package com.crud.spring.bank.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import com.crud.spring.bank.service.AccountService;

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
        Account tAccount = new Account();
        
        when(accountDAO.findByAccountNumber(accountNumber)).thenReturn(tAccount);
        when(accountMapper.toDTO(tAccount)).thenReturn(new AccountDTO());

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
        Account tAccount = new Account();
        
        when(accountDAO.findById(accountId)).thenReturn(Optional.of(tAccount));
        when(accountMapper.toDTO(tAccount)).thenReturn(new AccountDTO());

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
        /*
    	List<Account> mockAccounts = new ArrayList<>();
        when(accountDAO.findAll()).thenReturn(mockAccounts);

        List<AccountDTO> accList = accountService.getAllAccounts();

        assertNotNull(accList);
        */

        List<Account> tAccounts = new ArrayList<>();
        
        tAccounts.add(new Account("1234567891234567", BigDecimal.valueOf(1000)));
        tAccounts.add(new Account("7654321987654321", BigDecimal.valueOf(500)));

        when(accountDAO.findAll()).thenReturn(tAccounts);
        when(accountMapper.toDTO(any(Account.class))).thenAnswer(invocation -> {
            Account acc = invocation.getArgument(0);
            return new AccountDTO(acc.getAccountNumber(), acc.getBalance());
        });

        List<AccountDTO> list = accountService.getAllAccounts();

        assertNotNull(list);
        assertEquals(2, list.size());
    	
    }

}
