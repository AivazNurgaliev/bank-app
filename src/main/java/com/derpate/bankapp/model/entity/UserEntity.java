package com.derpate.bankapp.model.entity;

import com.derpate.bankapp.model.dto.UserResponse;
import com.derpate.bankapp.model.security.Role;
import com.derpate.bankapp.model.security.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
// TODO: 18.02.2023 secondName maybe better to lastName 
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "patronymic_name")
    private String patronymicName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> cardsByUserId;

    @OneToMany(mappedBy = "usersByUserId")
    private List<DepositEntity> depositsByUserId;

    public UserResponse fromUserEntity(UserEntity userEntity) {
        UserResponse userResponse = UserResponse.builder()
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .firstName(userEntity.getFirstName())
                .secondName(userEntity.getSecondName())
                .patronymicName(userEntity.getPatronymicName())
                .lastLogin(userEntity.getLastLogin())
                .build();

        return userResponse;
    }
}
