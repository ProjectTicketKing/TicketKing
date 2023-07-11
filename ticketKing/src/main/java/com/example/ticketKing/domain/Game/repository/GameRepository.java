package com.example.ticketKing.domain.Game.repository;

import com.example.ticketKing.domain.Game.entitiy.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}

