package com.derpate.bankapp.model.entity;

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
@Table(name = "users_cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    // TODO: 10.03.2023 user id vendor id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expire_at")
    private Timestamp expireAt;

    @Column(name = "cvv", length = 3)
    private String cvv;

    @Column(name = "balance", precision = 9, scale = 2)
    private BigDecimal balance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id")
    private VendorEntity vendorId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userId;


}
