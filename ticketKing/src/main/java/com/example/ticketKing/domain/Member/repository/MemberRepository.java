package com.example.ticketKing.domain.Member.repository;

import com.example.ticketKing.domain.Member.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Member a SET a.photo = null WHERE a.photo.id = :photoId")
    void updatePhotoIdToNull(Long photoId);
}