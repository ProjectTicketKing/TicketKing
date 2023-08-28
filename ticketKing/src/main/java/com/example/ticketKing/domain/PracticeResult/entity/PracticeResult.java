package com.example.ticketKing.domain.PracticeResult.entity;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.RealParticipant.entity.RealParticipant;
import com.example.ticketKing.domain.VirtualParticipant.entity.VirtualParticipant;
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
public class PracticeResult extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private Member member;
    private LocalDateTime seatSelectionTime;
    private Boolean success;

    @ManyToOne(fetch = LAZY)
    private RealParticipant realParticipant;

    @ManyToOne(fetch = LAZY)
    private VirtualParticipant virtualParticipant;

}

