package com.derpate.bankapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateRequest {
    private Integer vendorId;
    private String pin;
}