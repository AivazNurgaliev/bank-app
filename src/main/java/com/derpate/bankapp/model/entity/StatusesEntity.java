package com.derpate.bankapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "statuses")
public class StatusesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private int statusId;

    @Column(name = "status_name")
    private String statusName;

    @OneToMany(mappedBy = "statusesByStatusId")
    @JsonIgnore
    private List<UsersEntity> status_id;

}

