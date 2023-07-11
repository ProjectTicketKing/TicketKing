package com.example.ticketKing.domain.Seat.entity;

import com.example.ticketKing.domain.Competition.entity.Competition;
import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
@Builder
@Setter
public class Seat extends BaseEntity {


    private String seatType;
    private Integer seatRow;
    private Integer seatNumber;
    private String status;


    @ManyToOne(fetch = LAZY)
    private Hall hall;

    @ManyToOne(fetch = LAZY)
    private Competition competition;

    public Seat(String seatType, int seatRow, int seatNumber, String status) {
        this.seatType = seatType;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.status = status;
    }
}

