package com.example.ticketKing.domain.VirtualGame.repository;

import com.example.ticketKing.domain.VirtualGame.entity.VirtualGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualGameRepository extends JpaRepository<VirtualGame,Long> {

}
