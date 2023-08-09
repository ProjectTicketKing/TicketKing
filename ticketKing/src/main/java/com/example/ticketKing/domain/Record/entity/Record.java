package com.example.ticketKing.domain.Record.entity;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Practice.entity.Practice;
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
public class Record extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    private Practice practice;
}