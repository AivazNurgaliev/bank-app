package com.derpate.bankapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: 08.04.2023 убрать data -> getter setter без tostring 
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateRequest {
    private Integer vendorId;
    private String pin;
}
