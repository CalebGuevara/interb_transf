package com.crud.spring.bank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.spring.bank.dto.AccountDTO;
import com.crud.spring.bank.dto.TransferDTO;
import com.crud.spring.bank.service.AccountService;
import com.crud.spring.bank.service.TransferService;
import com.crud.spring.bank.service.TransferServiceImpl;

@RestController
@RequestMapping("/api")
public class TransferController {

	@Autowired
    private TransferServiceImpl transferServiceImpl;

	@Autowired
	private AccountService accountService;

	@PostMapping("/transfer")
	public ResponseEntity<Map<String, Object>> startTransfer(@RequestBody TransferDTO transferDTO) {
        // Lógica para realizar la transferencia
		try {
			transferServiceImpl.validateTransfer(transferDTO);
		} catch (IllegalArgumentException ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
		transferServiceImpl.startTransfer(transferDTO);

        // Get de datos despues de la transferencia
        AccountDTO sourceAccountDTO = accountService.getAccountByNumber(transferDTO.getSourceAccount().getAccountNumber());
        AccountDTO destinationAccountDTO = accountService.getAccountByNumber(transferDTO.getDestinationAccount().getAccountNumber());

        // Mapeo
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Transfer completed successfully");
        response.put("sourceAccountBalance", sourceAccountDTO != null ? sourceAccountDTO.getBalance() : null);
        response.put("destinationAccountBeneficiary", destinationAccountDTO != null ? getBeneficiaryData(destinationAccountDTO) : null);

        // Respuesta
        return ResponseEntity.ok(response);
    }

	private Map<String, Object> getBeneficiaryData(AccountDTO destinationAccountDTO) {
        // Datos del beneficiario
        Map<String, Object> beneficiaryData = new HashMap<>();
        beneficiaryData.put("id", destinationAccountDTO.getId());
        beneficiaryData.put("accountNumber", destinationAccountDTO.getAccountNumber());

        return beneficiaryData;
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable String accountNumber) {

    	AccountDTO accountDTO;

    	try {
			accountDTO = accountService.getAccountByNumber(accountNumber);
		} catch (IllegalArgumentException ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
		}

        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {

        List<AccountDTO> accountDTOs = accountService.getAllAccounts();

        return new ResponseEntity<>(accountDTOs, HttpStatus.OK);
    }

	@GetMapping("accounts/id/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountId) {

		AccountDTO accountDTO;

		try {
			accountDTO = accountService.getAccountById(accountId);
		}catch (IllegalArgumentException ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
		}

        return ResponseEntity.ok(accountDTO);
    }
}