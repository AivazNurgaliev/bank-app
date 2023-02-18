package com.derpate.bankapp.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String password;
    private String repeatPassword;
    private String phone;
    private String firstName;
    private String secondName;
    private String patronymicName;
}
