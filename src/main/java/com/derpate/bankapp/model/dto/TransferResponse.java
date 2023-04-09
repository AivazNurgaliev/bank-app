package com.derpate.bankapp.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private Long transferId;
    private Integer senderId;
    private String senderName;
    private Integer receiverId;
    private String receiverName;
    private Long senderCardId;
    private Long receiverCardId;
    private Timestamp createdAt;
    private BigDecimal amount;

}
