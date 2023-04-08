package com.derpate.bankapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferCreateRequest {
    private Integer receiverId;
    private Long senderCardId;
    private Long receiverCardId;
    private BigDecimal amount;
    private String pin;
}
