package com.example.ticketKing.domain.RealParticipant.entity;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.PracticeResult.entity.PracticeResult;
import com.example.ticketKing.domain.Game.RealGame.entity.RealGame;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
@Builder
public class RealParticipant extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private RealGame realGame;

    @OneToMany(mappedBy = "realParticipant")
    private List<PracticeResult> practiceResults;

    private Boolean ticketSuccess;
    private Boolean queueSuccess;


}
