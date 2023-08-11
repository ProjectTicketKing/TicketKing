package com.example.ticketKing.domain.Practice.repository;

import com.example.ticketKing.domain.Practice.entity.Practice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PracticeRepository extends JpaRepository<Practice,Long> {

    List<Practice> findByMemberId(Long memberId);
    // 추가 메서드
    Optional<Practice> findTopByMemberIdOrderBySeatSelectionTimeDesc(Long memberId);
}
