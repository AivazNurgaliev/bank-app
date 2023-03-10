package com.derpate.bankapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vendors")
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Integer userId;

    @Column(name = "vendor_name")
    private String vendorName;

    @OneToMany(mappedBy = "vendorId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> cards;
}
