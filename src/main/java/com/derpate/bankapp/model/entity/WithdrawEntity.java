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
@Table(name = "withdrawals")
public class WithdrawEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawal_id")
    private Long withdrawalId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "amount", precision = 9, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    @JsonIgnore
    private UserEntity usersByUserId;

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id", updatable = false, insertable = false)
    @JsonIgnore
    private CardEntity usersCardsByCardId;
}
