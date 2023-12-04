package com.crud.spring.bank.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.crud.spring.bank.dto.AccountDTO;
import com.crud.spring.bank.entity.Account;
import com.crud.spring.bank.mapper.AccountMapper;
import com.crud.spring.bank.mapper.AccountMapperImpl;

@SpringBootTest
public class AccountMapperTest {
	
	private final AccountMapper accountMapper = new AccountMapperImpl();

    @Test
    public void testToEntity() {
    	
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("123456789");
        accountDTO.setBalance(BigDecimal.valueOf(1000));

        Account result = accountMapper.toEntity(accountDTO);

        assertEquals(accountDTO.getId(), result.getId());
        assertEquals(accountDTO.getAccountNumber(), result.getAccountNumber());
        assertEquals(accountDTO.getBalance(), result.getBalance());
    }

    @Test
    public void testToDTO() {
    	
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("123456789");
        account.setBalance(BigDecimal.valueOf(1000));

        AccountDTO result = accountMapper.toDTO(account);

        assertEquals(account.getId(), result.getId());
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        assertEquals(account.getBalance(), result.getBalance());
    }
}
