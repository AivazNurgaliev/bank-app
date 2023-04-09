package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.model.dto.ReportCreateRequest;
import com.derpate.bankapp.model.dto.ReportResponse;
import com.derpate.bankapp.model.entity.DepositEntity;

import java.util.List;

public interface ReportService {
    List<DepositEntity> getDepositsReportByCardId(Long cardId, ReportCreateRequest reportCreateRequest) throws CardNotFoundException;
    List<ReportResponse> getAllReports(ReportCreateRequest reportCreateRequest) throws CardNotFoundException;
    ReportResponse getReportResponseByCardId(Long cardId, ReportCreateRequest reportCreateRequest) throws CardNotFoundException;
}
