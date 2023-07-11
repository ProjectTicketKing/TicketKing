package com.example.ticketKing.domain.Competition.entity;

import com.example.ticketKing.domain.Practice.entity.Practice;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Setter
@RequiredArgsConstructor
@Entity
@Getter
public class Competition extends BaseEntity {
// 레벨별 경쟁 저장 엔티티

    private String level;

    @OneToMany(mappedBy = "competition", fetch = LAZY)
    private List<Seat> seat;


//    public String getLevel() {
//        return this.level;
//    }
}
