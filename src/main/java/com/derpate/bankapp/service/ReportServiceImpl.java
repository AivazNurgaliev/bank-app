package com.derpate.bankapp.service;

import com.derpate.bankapp.exception.CardNotFoundException;
import com.derpate.bankapp.model.dto.ReportCreateRequest;
import com.derpate.bankapp.model.entity.DepositEntity;
import com.derpate.bankapp.repository.CardRepository;
import com.derpate.bankapp.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final CardRepository cardRepository;
    private final UserServiceImpl userServiceImpl;
    private final DepositRepository depositRepository;

    @Autowired
    public ReportServiceImpl(CardRepository cardRepository, UserServiceImpl userServiceImpl, DepositRepository depositRepository) {
        this.cardRepository = cardRepository;
        this.userServiceImpl = userServiceImpl;
        this.depositRepository = depositRepository;
    }


    @Override
    public List<DepositEntity> getDepositsReportByCardId(Long cardId, ReportCreateRequest reportCreateRequest) throws CardNotFoundException {
        if (!cardRepository.existsByUserIdAndCardId(userServiceImpl.getMyId(), cardId)) {
            throw new CardNotFoundException("There isn't a card");
        }
        Timestamp fromTimestamp = Timestamp.valueOf(reportCreateRequest.getFromDate().atStartOfDay());
        Timestamp toTimestamp = Timestamp.valueOf(reportCreateRequest.getToDate().atStartOfDay());


        var deposits = depositRepository.findAllByUserIdAndCardId(userServiceImpl.getMyId(), cardId).stream()
                .filter(x -> x.getCreatedAt().after(fromTimestamp) && x.getCreatedAt().before(toTimestamp))
                .collect(Collectors.toList());


        return deposits;
    }
}
