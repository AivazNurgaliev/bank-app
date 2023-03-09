package com.derpate.bankapp.model.dto;

import com.derpate.bankapp.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String phone;
    private String firstName;
    private String secondName;
    private String patronymicName;
    private Timestamp lastLogin;

}
