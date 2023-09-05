package com.example.ticketKing.domain.VirtualGame.entity;
import com.example.ticketKing.domain.VirtualParticipant.entity.VirtualParticipant;
import com.example.ticketKing.domain.VirtualSeat.entity.VirtualSeat;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
@Entity
@SuperBuilder
@Builder
@Setter
public class VirtualGame extends BaseEntity {

    @OneToOne
    private VirtualParticipant virtualParticipant;

    @OneToMany(mappedBy = "virtualGame", fetch=LAZY )
    private List<VirtualSeat> virtualSeats;
}
