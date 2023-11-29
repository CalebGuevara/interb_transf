package com.crud.spring.bank.service;

import com.crud.spring.bank.dto.TransferDTO;

public interface TransferService {
	
	void startTransfer(TransferDTO transferDTO);
	
    void validateTransfer(TransferDTO transferDTO);
    
}
