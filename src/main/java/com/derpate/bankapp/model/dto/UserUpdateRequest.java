package com.derpate.bankapp.model.dto;

import com.derpate.bankapp.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {
    private String email;
    private String phone;
    private String firstName;
    private String secondName;
    private String patronymicName;

}
