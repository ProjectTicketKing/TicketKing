package com.example.ticketKing.domain.Hall.entity;

import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;


@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
public class Hall extends BaseEntity {

    private String name; // KSPO, OLYPS
    private String area; // A,S ,VIP

}
