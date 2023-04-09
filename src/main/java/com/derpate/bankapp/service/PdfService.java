package com.derpate.bankapp.service;


import com.derpate.bankapp.model.dto.ReportCreateRequest;
import com.derpate.bankapp.model.dto.ReportResponse;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface PdfService {

    void createPdfReport(ReportCreateRequest reportCreateRequest, List<ReportResponse> responses) throws DocumentException, IOException;

}
