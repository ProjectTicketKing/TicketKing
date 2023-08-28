package com.example.ticketKing.domain.PracticeResult.repository;

import com.example.ticketKing.domain.PracticeResult.entity.PracticeResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PracticeResultRepository extends JpaRepository<PracticeResult,Long> {

    List<PracticeResult> findByMemberId(Long memberId);
    // 추가 메서드
    Optional<PracticeResult> findTopByMemberIdOrderBySeatSelectionTimeDesc(Long memberId);
}
