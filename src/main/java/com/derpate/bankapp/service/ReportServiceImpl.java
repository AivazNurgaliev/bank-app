package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.model.dto.ReportCreateRequest;
import com.derpate.bankapp.model.dto.ReportResponse;
import com.derpate.bankapp.model.dto.TransferResponse;
import com.derpate.bankapp.model.entity.CardEntity;
import com.derpate.bankapp.model.entity.DepositEntity;
import com.derpate.bankapp.model.entity.TransferEntity;
import com.derpate.bankapp.model.entity.WithdrawEntity;
import com.derpate.bankapp.repository.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final CardRepository cardRepository;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final DepositRepository depositRepository;
    private final WithdrawRepository withdrawRepository;
    private final TransferRepository transferRepository;

    @Autowired
    public ReportServiceImpl(CardRepository cardRepository, UserServiceImpl userServiceImpl, UserRepository userRepository, DepositRepository depositRepository, WithdrawRepository withdrawRepository, TransferRepository transferRepository) {
        this.cardRepository = cardRepository;
        this.userServiceImpl = userServiceImpl;
        this.userRepository = userRepository;
        this.depositRepository = depositRepository;
        this.withdrawRepository = withdrawRepository;
        this.transferRepository = transferRepository;
    }


/*
    @Override
    public List<DepositEntity> getDepositsReportByCardId(Long cardId, ReportCreateRequest reportCreateRequest) throws CardNotFoundException {
        */
/*var test = userServiceImpl.getUserEntity().getDepositsByUserId();
        for (DepositEntity d : test) {
            System.out.println(d.getCreatedAt());
        }*//*


        if (!cardRepository.existsByUserIdAndCardId(userServiceImpl.getMyId(), cardId)) {
            throw new CardNotFoundException("There isn't a card");
        }
        Timestamp fromTimestamp = Timestamp.valueOf(reportCreateRequest.getFromDate().atStartOfDay());
        Timestamp toTimestamp = Timestamp.valueOf(reportCreateRequest.getToDate().atStartOfDay());


        var deposits = depositRepository.findAllByUserIdAndCardId(userServiceImpl.getMyId(), cardId).stream()
                .filter(x -> x.getCreatedAt().after(fromTimestamp) && x.getCreatedAt().before(toTimestamp))
                .collect(Collectors.toList());

        BigDecimal total = deposits.stream()
                .map(DepositEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //System.out.println(total);

        return deposits;
    }
*/

    @Override
    public ReportResponse getReportResponseByCardId(Long cardId, ReportCreateRequest reportCreateRequest) throws CardNotFoundException {

        if (!cardRepository.existsByUserIdAndCardId(userServiceImpl.getMyId(), cardId)) {
            throw new CardNotFoundException("There isn't a card");
        }
        Timestamp fromTimestamp = Timestamp.valueOf(reportCreateRequest.getFromDate().atStartOfDay());
        Timestamp toTimestamp = Timestamp.valueOf(reportCreateRequest.getToDate().atStartOfDay());


        var deposits = depositRepository
                .findAllByUserIdAndCardId(userServiceImpl.getMyId(), cardId).stream()
                .filter(x -> x.getCreatedAt().after(fromTimestamp) && x.getCreatedAt().before(toTimestamp))
                .collect(Collectors.toList());

        var withdrawals = withdrawRepository
                .findAllByUserIdAndCardId(userServiceImpl.getMyId(), cardId).stream()
                .filter(x -> x.getCreatedAt().after(fromTimestamp) && x.getCreatedAt().before(toTimestamp))
                .collect(Collectors.toList());

        var sendTransfers = transferRepository
                .findAllBySenderIdAndSenderCardId(userServiceImpl.getMyId(), cardId).stream()
                .filter(x -> x.getCreatedAt().after(fromTimestamp) && x.getCreatedAt().before(toTimestamp))
                .collect(Collectors.toList());

        var receivedTransfers = transferRepository
                .findAllByReceiverIdAndReceiverCardId(userServiceImpl.getMyId(), cardId).stream()
                .filter(x -> x.getCreatedAt().after(fromTimestamp) && x.getCreatedAt().before(toTimestamp))
                .collect(Collectors.toList());

        var modifiedSendTransfers = sendTransfers.stream()
                .map(x -> new TransferResponse(x.getTransferId(),
                        x.getSenderId(),
                        userServiceImpl.getUserEntity().getFirstName() + " "
                                + userServiceImpl.getUserEntity().getSecondName(),
                        x.getReceiverId(),
                        userRepository.findByUserId(x.getReceiverId()).getFirstName() + " "
                                + userRepository.findByUserId(x.getReceiverId()).getSecondName(),
                        x.getSenderCardId(),
                        x.getReceiverCardId(),
                        x.getCreatedAt(),
                        x.getAmount()))
                .collect(Collectors.toList());

        var modifiedReceivedTransfers = receivedTransfers.stream()
                .map(x -> new TransferResponse(x.getTransferId(),
                        x.getSenderId(),
                        userServiceImpl.getUserEntity().getFirstName() + " "
                                + userServiceImpl.getUserEntity().getSecondName(),
                        x.getReceiverId(),
                        userRepository.findByUserId(x.getReceiverId()).getFirstName() + " "
                                + userRepository.findByUserId(x.getReceiverId()).getSecondName(),
                        x.getSenderCardId(),
                        x.getReceiverCardId(),
                        x.getCreatedAt(),
                        x.getAmount()))
                .collect(Collectors.toList());

        BigDecimal sumDeposits = deposits.stream()
                .map(DepositEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumWithdrawals = withdrawals.stream()
                .map(WithdrawEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumSendTransfer = sendTransfers.stream()
                .map(TransferEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumReceivedTransfer = receivedTransfers.stream()
                .map(TransferEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReportResponse response = ReportResponse.builder()
                .cardId(cardId)
                .totalDeposits(sumDeposits)
                .totalWithdrawals(sumWithdrawals)
                .totalSendTransfers(sumSendTransfer)
                .totalReceivedTransfers(sumReceivedTransfer)
                .deposits(deposits)
                .withdrawals(withdrawals)
                .sendTransfers(modifiedSendTransfers)
                .receivedTransfers(modifiedReceivedTransfers)
                .build();

        return response;
    }

    @Override
    public List<ReportResponse> getAllReports(ReportCreateRequest reportCreateRequest) throws CardNotFoundException {
        var userCards = userServiceImpl.getUserEntity().getCardsByUserId();

        if (userCards == null) {
            throw new CardNotFoundException("There're no cards");
        }

        List<ReportResponse> responses = new ArrayList<>();

        for (CardEntity card : userCards) {
            ReportResponse r = getReportResponseByCardId(card.getCardId(), reportCreateRequest);
            responses.add(r);
        }

        return responses;
    }
}
