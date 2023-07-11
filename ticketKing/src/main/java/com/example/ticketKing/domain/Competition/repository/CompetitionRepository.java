package com.example.ticketKing.domain.Competition.repository;

import com.example.ticketKing.domain.Competition.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
