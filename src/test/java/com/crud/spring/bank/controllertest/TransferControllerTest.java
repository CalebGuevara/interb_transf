package com.crud.spring.bank.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.crud.spring.bank.controller.TransferController;
import com.crud.spring.bank.dto.AccountDTO;
import com.crud.spring.bank.dto.TransferDTO;
import com.crud.spring.bank.service.AccountService;
import com.crud.spring.bank.service.TransferService;

@SpringBootTest
public class TransferControllerTest {

	@Mock
    private TransferService transferService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransferController transferController;

    private AccountDTO createAccountDTO(String accountNumber, BigDecimal balance) {
        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountNumber(accountNumber);
        accountDTO.setBalance(balance);

        return accountDTO;
    }

    private TransferDTO createTransferDTO() {
        TransferDTO transferDTO = new TransferDTO();
        
        transferDTO.setSourceAccount(createAccountDTO("sourceAccountNumber", BigDecimal.valueOf(1000)));
        transferDTO.setDestinationAccount(createAccountDTO("destinationAccountNumber", BigDecimal.valueOf(500)));
        transferDTO.setAmount(BigDecimal.valueOf(200));
        
        return transferDTO;
    }

    @Test
    public void testGetAccountByNumberSuccess() {
        AccountDTO accDTO = createAccountDTO("SoyUnNumeroDeCuentaReal", BigDecimal.valueOf(1000));
        
        when(accountService.getAccountByNumber("SoyUnNumeroDeCuentaReal")).thenReturn(accDTO);

        ResponseEntity<AccountDTO> responseEntity = transferController.getAccountByNumber("SoyUnNumeroDeCuentaReal");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(accDTO, responseEntity.getBody());
    }

    @Test
    public void testGetAccountByNumber404() {
        when(accountService.getAccountByNumber("NoSoyUnNumeroDeCuenta"))
                .thenThrow(new IllegalArgumentException("Account not found"));

        ResponseEntity<AccountDTO> responseEntity = transferController.getAccountByNumber("NoSoyUnNumeroDeCuenta");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAccountByIdSuccess() {
        AccountDTO accDTO = createAccountDTO("SoyUnaCuentaDeId1", BigDecimal.valueOf(1000));

        when(accountService.getAccountById(1L)).thenReturn(accDTO);

        ResponseEntity<AccountDTO> responseEntity = transferController.getAccountById(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(accDTO, responseEntity.getBody());
    }

    @Test
    public void testGetAccountById404() {
        when(accountService.getAccountById(404L)).thenThrow(new IllegalArgumentException("Account not found"));

        ResponseEntity<AccountDTO> responseEntity = transferController.getAccountById(404L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllAccounts() {
        List<AccountDTO> accDTOs = Arrays.asList(
                createAccountDTO("acc1", BigDecimal.valueOf(1000)),
                createAccountDTO("acc2", BigDecimal.valueOf(500))
        );

        when(accountService.getAllAccounts()).thenReturn(accDTOs);

        ResponseEntity<List<AccountDTO>> responseEntity = transferController.getAllAccounts();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(accDTOs, responseEntity.getBody());
    }

    @Test
    public void testStartTransferSuccess() {
        TransferDTO transferDTO = createTransferDTO();

        AccountDTO sourceAccountDTO = createAccountDTO("sourceAccountNumber", BigDecimal.valueOf(1000));
        AccountDTO destinationAccountDTO = createAccountDTO("destinationAccountNumber", BigDecimal.valueOf(500));

        when(accountService.getAccountByNumber("sourceAccountNumber")).thenReturn(sourceAccountDTO);
        when(accountService.getAccountByNumber("destinationAccountNumber")).thenReturn(destinationAccountDTO);

        ResponseEntity<Map<String, Object>> responseEntity = transferController.startTransfer(transferDTO);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Map<String, Object> responseBody = responseEntity.getBody();

        assertNotNull(responseBody);
        assertEquals("Transfer completed successfully", responseBody.get("message"));
        assertNotNull(responseBody.get("sourceAccountBalance"));
        assertNotNull(responseBody.get("destinationAccountBeneficiary"));
    }

}
