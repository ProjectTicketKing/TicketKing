package com.example.ticketKing.domain.Game.entitiy;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@RequiredArgsConstructor
@Entity
@Getter
public class Game extends BaseEntity {
// 레벨별 경쟁 저장 엔티티

    private String level;

    @OneToOne
    private Member member;


    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<Seat> seats;

}
