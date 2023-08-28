package com.example.ticketKing.domain.PracticeResult.service;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.repository.HallRepository;
import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.repository.MemberRepository;
import com.example.ticketKing.domain.PracticeResult.entity.PracticeResult;
import com.example.ticketKing.domain.PracticeResult.repository.PracticeResultRepository;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.repository.SeatRepository;
import com.example.ticketKing.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PracticeResultService {

    private final PracticeResultRepository practiceRepository;
    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;
    private final HallRepository hallRepository;


    @Transactional
    public RsData<PracticeResult> register(String hallName, String type, Integer row, Integer col, Long userId){

        Member member = null;
        Optional<Member> memData = memberRepository.findById(userId);

        if (memData.isPresent()) {
            member = memData.get();
        } else {
        }

        PracticeResult practice = PracticeResult
                .builder()
                .member(member)
                .seatSelectionTime(LocalDateTime.now())
                .build();

        practiceRepository.save(practice);

        return RsData.of("S-1", "연습 등록이 완료되었습니다.", practice);
    }
}