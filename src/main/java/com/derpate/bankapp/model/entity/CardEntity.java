package com.derpate.bankapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "vendor_id")
    private Integer vendorId;

    @Column(name = "user_id")
    private Integer userId;

    // TODO: 10.03.2023 user id vendor id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expire_at")
    private Timestamp expireAt;

    @Column(name = "cvv", length = 3)
    private String cvv;

    @Column(name = "pin")
    private String pin;

    @Column(name = "balance", precision = 9, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id", updatable = false, insertable = false)
    @JsonIgnore
    private VendorEntity vendorByVendorId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    @JsonIgnore
    private UserEntity userByUserId;

    @OneToMany(mappedBy = "usersCardsByCardId")
    @JsonIgnore
    private List<DepositEntity> depositsByCardId;

    @OneToMany(mappedBy = "usersCardsByCardId")
    @JsonIgnore
    private List<WithdrawEntity> withdrawalsByCardId;

    @OneToMany(mappedBy = "usersCardsBySenderCardId")
    @JsonIgnore
    private List<TransferEntity> transfersBySenderCardId;

    @OneToMany(mappedBy = "usersCardsByReceiverCardId")
    @JsonIgnore
    private List<TransferEntity> transfersByReceiverCardId;

}
