package com.example.ticketKing.domain.Practice.entity;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
@Builder
public class Practice extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private Seat seat;

    @ManyToOne(fetch = LAZY)
    private Member member;

    private LocalDateTime seatSelectionTime;

    private String selectedSeatInfo;

}

