package com.crud.spring.bank.mapper;

import org.mapstruct.Mapper;

import com.crud.spring.bank.dto.AccountDTO;
import com.crud.spring.bank.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	Account toEntity(AccountDTO accountDTO);
	AccountDTO toDTO(Account transfer);
}
