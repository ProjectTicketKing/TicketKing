package com.example.ticketKing.domain.Seat.entity;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
public class Seat extends BaseEntity {


    private String seatType;
    private Integer seatRow;
    private Integer seatNumber;
    private Boolean status;


    @ManyToOne(fetch = LAZY)
    private Hall hall;



}

