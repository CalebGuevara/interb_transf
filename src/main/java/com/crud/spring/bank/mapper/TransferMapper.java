package com.crud.spring.bank.mapper;

import org.mapstruct.Mapper;

import com.crud.spring.bank.dto.TransferDTO;
import com.crud.spring.bank.entity.Transfer;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    Transfer toEntity(TransferDTO transferDTO);
    TransferDTO toDTO(Transfer transfer);
}