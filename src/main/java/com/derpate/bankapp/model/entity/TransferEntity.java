package com.derpate.bankapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transfers")
public class TransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long transferId;

    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "receiver_id")
    private Integer receiverId;

    @Column(name = "sender_card_id")
    private Long senderCardId;

    @Column(name = "receiver_card_id")
    private Long receiverCardId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "amount", precision = 9, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    @JsonIgnore
    private UserEntity usersBySenderId;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    @JsonIgnore
    private UserEntity usersByReceiverId;

    @ManyToOne
    @JoinColumn(name = "sender_card_id", referencedColumnName = "card_id", updatable = false, insertable = false)
    @JsonIgnore
    private CardEntity usersCardsBySenderCardId;

    @ManyToOne
    @JoinColumn(name = "receiver_card_id", referencedColumnName = "card_id", updatable = false, insertable = false)
    @JsonIgnore
    private CardEntity usersCardsByReceiverCardId;
}
