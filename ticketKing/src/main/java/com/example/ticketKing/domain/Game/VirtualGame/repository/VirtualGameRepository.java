package com.example.ticketKing.domain.Game.VirtualGame.repository;

import com.example.ticketKing.domain.Game.VirtualGame.entity.VirtualGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualGameRepository extends JpaRepository<VirtualGame,Long> {

}
