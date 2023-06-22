package com.example.ticketKing.domain.Hall.entity;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Seat.entity.Seat;
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
public class Hall extends BaseEntity {

    private String name;
    private Integer seatCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hall_id")
    private Hall hall;




}
