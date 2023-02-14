package com.derpate.bankapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
public class UsersEntity {

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

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RolesEntity roleId;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private StatusesEntity statusId;
}
