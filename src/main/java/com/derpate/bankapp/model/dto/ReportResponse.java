package com.derpate.bankapp.model.dto;

import com.derpate.bankapp.model.entity.DepositEntity;
import com.derpate.bankapp.model.entity.TransferEntity;
import com.derpate.bankapp.model.entity.WithdrawEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private Long cardId;
    private List<DepositEntity> deposits;
    private List<WithdrawEntity> withdrawals;
    private List<TransferEntity> transfers;
}
