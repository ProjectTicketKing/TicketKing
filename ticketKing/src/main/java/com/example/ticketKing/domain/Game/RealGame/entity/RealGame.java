package com.example.ticketKing.domain.Game.RealGame.entity;

import com.example.ticketKing.domain.RealParticipant.entity.RealParticipant;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class RealGame extends BaseEntity {

    @OneToMany(mappedBy = "realGame", fetch = LAZY)
    private List<RealParticipant> realParticipants;

    @OneToMany(mappedBy = "realGame", fetch = LAZY)
    private List<Seat> seats;
}
