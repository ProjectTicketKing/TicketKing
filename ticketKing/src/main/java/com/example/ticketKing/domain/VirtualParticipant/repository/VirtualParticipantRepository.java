package com.example.ticketKing.domain.VirtualParticipant.repository;

import com.example.ticketKing.domain.VirtualParticipant.entity.VirtualParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualParticipantRepository extends JpaRepository<VirtualParticipant,Long> {
}
