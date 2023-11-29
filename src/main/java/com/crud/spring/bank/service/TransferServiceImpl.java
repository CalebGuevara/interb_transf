package com.crud.spring.bank.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.spring.bank.dao.AccountDAO;
import com.crud.spring.bank.dao.TransferDAO;
import com.crud.spring.bank.dto.TransferDTO;
import com.crud.spring.bank.entity.Account;
import com.crud.spring.bank.entity.Transfer;
import com.crud.spring.bank.mapper.TransferMapper;

@Service
public class TransferServiceImpl implements TransferService{
	
	@Autowired
	private TransferDAO transferRepository;
	
	@Autowired
	private TransferMapper transferMapper;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public void startTransfer(TransferDTO transferDTO) {
		
		Account sourceAccount = accountDAO.findByAccountNumber(transferDTO.getSourceAccount().getAccountNumber());
	    Account destinationAccount = accountDAO.findByAccountNumber(transferDTO.getDestinationAccount().getAccountNumber());

        // Verificar que el importe no exceda al balance de sourceAccount
        if (transferDTO.getAmount().compareTo(sourceAccount.getBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient balance in the source account");
        }

        // Realizar transferencia
        BigDecimal newSourceBalance = sourceAccount.getBalance().subtract(transferDTO.getAmount());
        BigDecimal newDestinationBalance = destinationAccount.getBalance().add(transferDTO.getAmount());

        sourceAccount.setBalance(newSourceBalance);
        destinationAccount.setBalance(newDestinationBalance);

        // Actualizar cuentas
        accountDAO.save(sourceAccount);
        accountDAO.save(destinationAccount);

        // Guardar transferencia
        Transfer tEntity = transferMapper.toEntity(transferDTO);
        tEntity.getSourceAccount().setId(sourceAccount.getId());
        tEntity.getDestinationAccount().setId(destinationAccount.getId());
        transferRepository.save(tEntity);
		
	}

	@Override
	public void validateTransfer(TransferDTO transferDTO) {
		
		if (transferDTO.getSourceAccount().equals(transferDTO.getDestinationAccount())) {
            throw new IllegalArgumentException("Cannot transfer to your own account");
        }
		
		if (transferDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Import must be greater than ZERO");
        }
		
		if (!"soles".equalsIgnoreCase(transferDTO.getCurrency()) && !"dollars".equalsIgnoreCase(transferDTO.getCurrency())) {
            throw new IllegalArgumentException("Only soles and dollars are accepted");
        }
		
		System.out.println("Validation Completed");
		
	}

}
