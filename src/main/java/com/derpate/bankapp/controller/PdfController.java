package com.derpate.bankapp.controller;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.model.dto.ReportCreateRequest;
import com.derpate.bankapp.service.PdfService;
import com.derpate.bankapp.service.ReportService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class PdfController {

    private final PdfService pdfService;
    private final ReportService reportService;

    @Autowired
    public PdfController(PdfService pdfService, ReportService reportService) {
        this.pdfService = pdfService;
        this.reportService = reportService;
    }

    @PostMapping("/report/pdf")
    public void createPdfReport(@RequestBody ReportCreateRequest reportCreateRequest) throws CardNotFoundException, DocumentException, IOException {
        var responses = reportService.getAllReports(reportCreateRequest);
        pdfService.createPdfReport(reportCreateRequest, responses);
    }
}
