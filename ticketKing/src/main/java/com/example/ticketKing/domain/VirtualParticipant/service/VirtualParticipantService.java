package com.example.ticketKing.domain.VirtualParticipant.service;

import com.example.ticketKing.domain.VirtualParticipant.entity.VirtualParticipant;
import com.example.ticketKing.domain.VirtualParticipant.repository.VirtualParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VirtualParticipantService {

    private final VirtualParticipantRepository virtualParticipantRepository;
    @Transactional
    public void create(VirtualParticipant participant) {
        virtualParticipantRepository.save(participant);
    }
}
