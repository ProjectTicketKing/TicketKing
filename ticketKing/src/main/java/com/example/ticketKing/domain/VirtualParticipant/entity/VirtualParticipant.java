package com.example.ticketKing.domain.VirtualParticipant.entity;

import com.example.ticketKing.domain.PracticeResult.entity.PracticeResult;
import com.example.ticketKing.domain.Game.VirtualGame.entity.VirtualGame;
import com.example.ticketKing.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class VirtualParticipant extends BaseEntity {

    private Boolean ticketSuccess;

    @OneToMany(mappedBy = "virtualParticipant", fetch=LAZY)
    private List<PracticeResult> practiceResults;

    @OneToOne
    private VirtualGame virtualGame;
}
