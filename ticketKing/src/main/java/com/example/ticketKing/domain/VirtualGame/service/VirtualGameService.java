package com.example.ticketKing.domain.VirtualGame.service;

import com.example.ticketKing.domain.VirtualGame.entity.VirtualGame;
import com.example.ticketKing.domain.VirtualParticipant.entity.VirtualParticipant;
import com.example.ticketKing.domain.VirtualParticipant.repository.VirtualParticipantRepository;
import com.example.ticketKing.domain.VirtualSeat.entity.VirtualSeat;
import com.example.ticketKing.domain.VirtualSeat.repository.VirtualSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VirtualGameService {

    private final VirtualSeatRepository virtualSeatRepository;
    private final VirtualParticipantRepository virtualParticipantRepository;

    @Transactional
    public void create(VirtualGame game, List<VirtualSeat> seats, VirtualParticipant participant) {
        // 먼저 VirtualParticipant를 저장
        virtualParticipantRepository.save(participant);

        // VirtualGame과 VirtualSeat를 연결하고 저장
        game.setVirtualParticipant(participant);
        game.setVirtualSeats(seats);
        for (VirtualSeat seat : seats) {
            seat.setVirtualGame(game);
        }
        virtualSeatRepository.saveAll(seats);
    }


}
