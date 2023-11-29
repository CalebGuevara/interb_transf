package com.crud.spring.bank.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
	
	private Long id;
	
	private String accountNumber;
	
    private BigDecimal balance;
	
}
