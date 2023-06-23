package com.example.ticketKing.domain.Concert.entity;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
@Entity
public class Concert extends BaseEntity {

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer price;


    @ManyToOne(fetch = LAZY)
    private Hall hall;


}
