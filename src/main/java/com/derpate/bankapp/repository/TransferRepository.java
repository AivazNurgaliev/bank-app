package com.derpate.bankapp.repository;

import com.derpate.bankapp.model.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {
    List<TransferEntity> findAllBySenderIdAndSenderCardId(Integer senderId, Long senderCardId);
    List<TransferEntity> findAllByReceiverIdAndReceiverCardId(Integer receiverId, Long receiverCardId);
}
