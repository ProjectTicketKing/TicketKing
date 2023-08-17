package com.example.ticketKing.domain.TimerHistory.repostitory;

import com.example.ticketKing.domain.TimerHistory.entity.TimerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimerHistoryRepository extends JpaRepository<TimerHistory, Long> {
    List<TimerHistory> findByMemberId(Long memberId);
}