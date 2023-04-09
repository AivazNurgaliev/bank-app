package com.derpate.bankapp.controller;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.model.dto.ReportCreateRequest;
import com.derpate.bankapp.model.dto.ReportResponse;
import com.derpate.bankapp.model.entity.DepositEntity;
import com.derpate.bankapp.service.ReportServiceImpl;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class ReportController {
    private final ReportServiceImpl reportService;

    @Autowired
    public ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

/*
    @GetMapping("/report/{cardId}")
    public List<DepositEntity> getDepositReport(@PathVariable(name = "cardId") Long cardId, @RequestBody ReportCreateRequest reportCreateRequest) throws CardNotFoundException {
        return reportService.getDepositsReportByCardId(cardId, reportCreateRequest);
    }
*/

    @GetMapping("/reports")
    public List<ReportResponse> getAllReports(@RequestBody ReportCreateRequest reportCreateRequest) throws CardNotFoundException, DocumentException, FileNotFoundException {
        return reportService.getAllReports(reportCreateRequest);
    }

}
