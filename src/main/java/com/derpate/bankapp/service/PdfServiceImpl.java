package com.derpate.bankapp.service;

import com.derpate.bankapp.model.dto.ReportCreateRequest;
import com.derpate.bankapp.model.dto.ReportResponse;
import com.derpate.bankapp.model.dto.TransferResponse;
import com.derpate.bankapp.model.entity.DepositEntity;
import com.derpate.bankapp.model.entity.TransferEntity;
import com.derpate.bankapp.model.entity.WithdrawEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfServiceImpl implements PdfService {

    private final ReportServiceImpl reportService;
    private final UserServiceImpl userService;

    @Autowired
    public PdfServiceImpl(ReportServiceImpl reportService, UserServiceImpl userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    // TODO: 09.04.2023 HEAVY REFACTOR NEEDED
    @Override
    public void createPdfReport(ReportCreateRequest reportCreateRequest, List<ReportResponse> responses) throws DocumentException, IOException {
        String userFullName = userService.getUserEntity().getFirstName() + " "
                + userService.getUserEntity().getSecondName() + " "
                + userService.getUserEntity().getPatronymicName();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("test1.pdf"));
        document.open();
        BaseFont unicodeFont = BaseFont.createFont("src/main/resources/static/LiberationSans-Reqular.ttf" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font lsansFontRegular = new Font(unicodeFont, 18, Font.NORMAL, BaseColor.BLACK);
        Font lsansFontRegularSubTitles = new Font(unicodeFont, 14, Font.NORMAL, BaseColor.BLACK);
        Paragraph p = new Paragraph(userFullName, lsansFontRegular);
        StringBuilder sb = new StringBuilder();

        sb.append("\nОтчёт по датам с " + reportCreateRequest.getFromDate() + " по " + reportCreateRequest.getToDate());

        p.add(sb.toString());
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        BigDecimal allSumsOfDeposits = responses.stream()
                .map(ReportResponse::getTotalDeposits)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal allSumsOfWithdrawal = responses.stream()
                .map(ReportResponse::getTotalWithdrawals)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal allSumsOfSendTransfer = responses.stream()
                .map(ReportResponse::getTotalSendTransfers)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal allSumsOfReceivedTransfer = responses.stream()
                .map(ReportResponse::getTotalReceivedTransfers)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Font lsansFontRegularForTitles = new Font(unicodeFont, 16, Font.NORMAL, BaseColor.BLACK);
        Paragraph title1 = new Paragraph("", lsansFontRegularForTitles);
        title1.add("\nПо всем картам: ");
        StringBuilder totalAllCards = new StringBuilder();
        Font lsansFontRegularForText = new Font(unicodeFont, 12, Font.NORMAL, BaseColor.BLACK);
        Paragraph text = new Paragraph("", lsansFontRegularForText);
        totalAllCards.append("\nВсего пополнений: " + allSumsOfDeposits + " руб."
        + "\nВсего выводов: " + allSumsOfWithdrawal + " руб."
                + "\nВсего отправлено переводами: " + allSumsOfSendTransfer + " руб."
        + "\nВсего получено переводами: " + allSumsOfReceivedTransfer + " руб.\n");
        text.add(totalAllCards.toString());
        text.setAlignment(Element.ALIGN_JUSTIFIED);

        document.add(title1);
        document.add(text);

        Paragraph repCards = new Paragraph("", lsansFontRegularForTitles);
        repCards.add("\nПо картам:\n\n");
        document.add(repCards);
        for (ReportResponse response: responses) {
            Paragraph title = new Paragraph("ID карты: " + response.getCardId().toString() + "", lsansFontRegularForTitles);
            Paragraph textCard = new Paragraph("", lsansFontRegularForText);
            StringBuilder sbCard = new StringBuilder();
            sbCard.append("\nВсего пополнений: " + response.getTotalDeposits() + " руб."
                    + "\nВсего выводов: " + response.getTotalWithdrawals() + " руб."
                    + "\nВсего отправлено переводами: " + response.getTotalSendTransfers() + " руб."
                    + "\nВсего получено переводами: " + response.getTotalReceivedTransfers() + " руб.\n");
            textCard.add(sbCard.toString());
            document.add(title);
            document.add(textCard);

            Paragraph historyDepositsTitle = new Paragraph("\nИстория транзакций (Пополнение):\n", lsansFontRegularSubTitles);
            document.add(historyDepositsTitle);
            StringBuilder sbDepositsHistory = new StringBuilder();
            Paragraph historyDepositsText = new Paragraph("", lsansFontRegularForText);
            for (DepositEntity deposit : response.getDeposits()) {
                sbDepositsHistory.append(
                        "Сумма: " + deposit.getAmount()
                        + "    Время: " + deposit.getCreatedAt() + "\n"
                );
            }
            if (response.getDeposits() == null || response.getDeposits().size() == 0) {
                sbDepositsHistory.append("\nНет пополнений в заданный период времени.\n");
            }
            historyDepositsText.add(sbDepositsHistory.toString() + "\n");
            document.add(historyDepositsText);

            Paragraph historyWithdrawalsTitle = new Paragraph("\nИстория транзакций (Вывод):\n", lsansFontRegularSubTitles);
            document.add(historyWithdrawalsTitle);
            StringBuilder sbWithdrawalsHistory = new StringBuilder();
            Paragraph historyWithdrawalsText = new Paragraph("", lsansFontRegularForText);

            for (WithdrawEntity withdraw : response.getWithdrawals()) {
                sbWithdrawalsHistory.append(
                        "Сумма: " + withdraw.getAmount()
                                + "    Время: " + withdraw.getCreatedAt() + "\n"
                );
            }

            if (response.getWithdrawals() == null || response.getWithdrawals().size() == 0) {
                sbWithdrawalsHistory.append("\nНет выводов в заданный период времени.\n");
            }
            historyWithdrawalsText.add(sbWithdrawalsHistory.toString() + "\n");
            document.add(historyWithdrawalsText);

            Paragraph historySendTransfersTitle = new Paragraph("\nИстория транзакций (Отправлено переводами):\n",
                    lsansFontRegularSubTitles);
            document.add(historySendTransfersTitle);
            StringBuilder sbSendTransferHistory = new StringBuilder();
            Paragraph historySendTransferText = new Paragraph("", lsansFontRegularForText);
            int count = 1;
            //PdfPTable table = new PdfPTable(7);
            //addTableHeader(table);
            for (TransferResponse t: response.getSendTransfers()) {
/*                table.addCell(String.valueOf(count));
                table.addCell(String.valueOf(t.getSenderName()));
                table.addCell(String.valueOf(t.getSenderCardId()));
                table.addCell(String.valueOf(t.getReceiverName()));
                table.addCell(String.valueOf(t.getReceiverCardId()));
                table.addCell(String.valueOf(t.getAmount()));
                table.addCell(String.valueOf(t.getCreatedAt()));*/
                sbSendTransferHistory.append("\nНомер: " + count++ + ""
                        + "\nОтправитель: " + t.getSenderName()
                        + "\nКарта отправителя: " + t.getSenderCardId()
                        + "\nПолучатель: " + t.getReceiverName()
                        + "\nКарта получателя: " + t.getReceiverCardId()
                        + "\nСумма: " + t.getAmount()
                        + "\nВремя: " + t.getCreatedAt() + "\n"
                );
            }

            if (response.getSendTransfers() == null || response.getSendTransfers().size() == 0) {
                sbSendTransferHistory.append("\nНет отправленных переводов в заданный период времени.\n");
            }

            historySendTransferText.add(sbSendTransferHistory.toString() + "\n");
            document.add(historySendTransferText);
            count = 1;

            Paragraph historyReceivedTransfersTitle = new Paragraph("\nИстория транзакций (Получено переводами):\n",
                    lsansFontRegularSubTitles);
            document.add(historyReceivedTransfersTitle);
            //document.add(table);
            StringBuilder sbReceivedTransferHistory = new StringBuilder();
            Paragraph historyReceivedTransferText = new Paragraph("", lsansFontRegularForText);
            for (TransferResponse t: response.getReceivedTransfers()) {
                sbReceivedTransferHistory.append("\nНомер: " + count++ + ""
                        + "\nОтправитель: " + t.getSenderName()
                        + "\nКарта отправителя: " + t.getSenderCardId()
                        + "\nПолучатель: " + t.getReceiverName()
                        + "\nКарта получателя: " + t.getReceiverCardId()
                        + "\nСумма: " + t.getAmount()
                        + "\nВремя: " + t.getCreatedAt() + "\n"
                );
            }

            if (response.getReceivedTransfers() == null || response.getReceivedTransfers().size() == 0) {
                sbReceivedTransferHistory.append("\nНет отправленных переводов в заданный период времени.\n");
            }

            historyReceivedTransferText.add(sbReceivedTransferHistory.toString() + "\n");
            document.add(historyReceivedTransferText);

        }

        document.close();
    }

 /*   private void addTableHeader(PdfPTable table) {
        Stream.of("Номер", "Отправитель", "Карта отправителя", "Получатель",
                "Карта получателя", "Сумма", "Время")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });

    }*/
}
