package com.crud.spring.bank.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.crud.spring.bank.dao.AccountDAO;
import com.crud.spring.bank.dao.TransferDAO;
import com.crud.spring.bank.dto.AccountDTO;
import com.crud.spring.bank.dto.TransferDTO;
import com.crud.spring.bank.entity.Account;
import com.crud.spring.bank.entity.Transfer;
import com.crud.spring.bank.mapper.TransferMapper;
import com.crud.spring.bank.service.TransferService;
import com.crud.spring.bank.service.TransferServiceImpl;

@SpringBootTest
public class TransferServiceImplTest {

	@Autowired
    private TransferService transferService;

    @MockBean
    private TransferDAO transferRepository;

    @MockBean
    private TransferMapper transferMapper;

    @MockBean
    private AccountDAO accountDAO;

    @Test
    public void testStartTransfer_SuccessfulTransfer() {

        TransferDTO tDTO = new TransferDTO();
        tDTO.setSourceAccount(new AccountDTO("sourceAccountNumber", BigDecimal.valueOf(1000)));
        tDTO.setDestinationAccount(new AccountDTO("destinationAccountNumber", BigDecimal.valueOf(500)));
        tDTO.setAmount(BigDecimal.valueOf(200));

        Account sourceAccount = new Account();
        sourceAccount.setId(1L);
        sourceAccount.setBalance(BigDecimal.valueOf(1000));

        Account destinationAccount = new Account();
        destinationAccount.setId(2L);
        destinationAccount.setBalance(BigDecimal.valueOf(500));

        when(accountDAO.findByAccountNumber("sourceAccountNumber")).thenReturn(sourceAccount);
        when(accountDAO.findByAccountNumber("destinationAccountNumber")).thenReturn(destinationAccount);
        when(transferMapper.toEntity(tDTO)).thenReturn(new Transfer());
        when(transferRepository.save(any(Transfer.class))).thenReturn(new Transfer());

        transferService.startTransfer(tDTO);

        assertEquals(BigDecimal.valueOf(800), sourceAccount.getBalance());
        assertEquals(BigDecimal.valueOf(700), destinationAccount.getBalance());
    }

    @Test
    public void testStartTransfer_InsufficientBalance() {

        TransferDTO tDTO = new TransferDTO();
        tDTO.setSourceAccount(new AccountDTO("sourceAccountNumber", BigDecimal.valueOf(100)));
        tDTO.setDestinationAccount(new AccountDTO("destinationAccountNumber", BigDecimal.valueOf(500)));
        tDTO.setAmount(BigDecimal.valueOf(200));

        Account sourceAccount = new Account();
        sourceAccount.setBalance(BigDecimal.valueOf(100));

        when(accountDAO.findByAccountNumber("sourceAccountNumber")).thenReturn(sourceAccount);

        assertThrows(IllegalArgumentException.class, () -> transferService.startTransfer(tDTO));
        assertEquals(BigDecimal.valueOf(100), sourceAccount.getBalance());
    }

    @Test
    public void testValidateTransfer_SuccessfulValidation() {
    	
        TransferDTO tDTO = new TransferDTO();
        tDTO.setSourceAccount(new AccountDTO("sourceAccountNumber", BigDecimal.valueOf(1000)));
        tDTO.setDestinationAccount(new AccountDTO("destinationAccountNumber", BigDecimal.valueOf(500)));
        tDTO.setAmount(BigDecimal.valueOf(200));
        tDTO.setCurrency("dollars");

        assertDoesNotThrow(() -> transferService.validateTransfer(tDTO));
    	
    }
    
    @Test
    public void testValidateTransfer_SourceEqualsDestination() {
        TransferDTO tDTO = new TransferDTO();
        tDTO.setSourceAccount(new AccountDTO("sourceAccountNumber", BigDecimal.valueOf(1000)));
        tDTO.setDestinationAccount(new AccountDTO("sourceAccountNumber", BigDecimal.valueOf(1000)));
        tDTO.setAmount(BigDecimal.valueOf(100));
        tDTO.setCurrency("soles");

        TransferService transferService = new TransferServiceImpl();

        assertThrows(IllegalArgumentException.class, () -> transferService.validateTransfer(tDTO));
    }
    
    @Test
    public void testValidateTransfer_ZeroAmount() {
        TransferDTO tDTO = new TransferDTO();
        tDTO.setSourceAccount(new AccountDTO("sourceAccountNumber", BigDecimal.valueOf(1000)));
        tDTO.setDestinationAccount(new AccountDTO("destinationAccountNumber", BigDecimal.valueOf(500)));
        tDTO.setAmount(BigDecimal.ZERO);
        tDTO.setCurrency("soles");

        TransferService transferService = new TransferServiceImpl();

        assertThrows(IllegalArgumentException.class, () -> transferService.validateTransfer(tDTO));
    }
    
    @Test
    public void testValidateTransfer_InvalidCurrency() {
        TransferDTO tDTO = new TransferDTO();
        tDTO.setSourceAccount(new AccountDTO("sourceAccountNumber", BigDecimal.valueOf(1000)));
        tDTO.setDestinationAccount(new AccountDTO("destinationAccountNumber", BigDecimal.valueOf(500)));
        tDTO.setAmount(BigDecimal.valueOf(100));
        tDTO.setCurrency("NoSoyNiSolNiDolar");

        TransferService transferService = new TransferServiceImpl();

        assertThrows(IllegalArgumentException.class, () -> transferService.validateTransfer(tDTO));
    }

}
