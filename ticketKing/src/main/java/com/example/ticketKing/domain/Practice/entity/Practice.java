package com.example.ticketKing.domain.Practice.entity;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
@Getter
@RequiredArgsConstructor
@Entity
public class Practice extends BaseEntity {


    @ManyToOne(fetch = LAZY)
    private Seat seat;




}
