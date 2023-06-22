package com.example.ticketKing.domain.Seat.entity;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import static jakarta.persistence.FetchType.LAZY;

public class Seat extends BaseEntity {


    private String seatType;
    private Integer row;
    private Integer seatNumber;
    private Boolean status;


    @ManyToOne(fetch = LAZY)
    private Hall hall;



}

