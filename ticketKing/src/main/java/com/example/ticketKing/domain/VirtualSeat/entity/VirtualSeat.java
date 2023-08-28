package com.example.ticketKing.domain.VirtualSeat.entity;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.VirtualGame.entity.VirtualGame;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
@Builder
@Setter
public class VirtualSeat extends BaseEntity {
    private String seatType;
    private Integer seatRow;
    private Integer seatNumber;
    private String status;

    @ManyToOne(fetch = LAZY)
    private Hall hall;

    @ManyToOne(fetch = LAZY)
    private VirtualGame virtualGame;

    public VirtualSeat(String seatType, int seatRow, int seatNumber, String status) {
        this.seatType = seatType;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.status = status;
    }
}
