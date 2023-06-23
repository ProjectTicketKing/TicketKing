package com.example.ticketKing.domain.Member.repository;

import com.example.ticketKing.domain.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByIdAndEmail(Long memberId, String email);
    Optional<Member> findByEmail(String email);
}
