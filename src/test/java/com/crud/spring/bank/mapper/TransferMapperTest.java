package com.crud.spring.bank.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.crud.spring.bank.dto.TransferDTO;
import com.crud.spring.bank.entity.Transfer;
import com.crud.spring.bank.mapper.TransferMapperImpl;

@SpringBootTest
public class TransferMapperTest {
	
	@InjectMocks
    private TransferMapperImpl transferMapper;

    @Test
    public void testToEntity() {

        TransferDTO tDTO = new TransferDTO();
        tDTO.setId(1L);
        tDTO.setAmount(BigDecimal.valueOf(100));

        Transfer transfer = transferMapper.toEntity(tDTO);

        assertNotNull(transfer);
        assertEquals(tDTO.getId(), transfer.getId());
        assertEquals(tDTO.getAmount(), transfer.getAmount());
    }
    
    @Test
    public void testToDTO() {

        Transfer transfer = new Transfer();
        transfer.setId(1L);
        transfer.setAmount(BigDecimal.valueOf(100));

        TransferDTO tDTO = transferMapper.toDTO(transfer);

        assertNotNull(tDTO);
        assertEquals(transfer.getId(), tDTO.getId());
        assertEquals(transfer.getAmount(), tDTO.getAmount());
    }
}
