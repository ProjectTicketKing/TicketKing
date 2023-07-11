package com.example.ticketKing.domain.Practice.service;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.repository.HallRepository;
import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.repository.MemberRepository;
import com.example.ticketKing.domain.Practice.entity.Practice;
import com.example.ticketKing.domain.Practice.repository.PracticeRepository;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.repository.SeatRepository;
import com.example.ticketKing.global.rq.Rq;
import com.example.ticketKing.global.rsData.RsData;
import com.fasterxml.classmate.MemberResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PracticeService {

    private final PracticeRepository  practiceRepository;
    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;
    private final HallRepository hallRepository;


    @Transactional
    public RsData<Practice> register(String hallName, String type,Integer row, Integer col,Long userId){

//        Member member = rq.getMember();
        Hall hall = hallRepository.findByName(hallName);
        Seat seat = seatRepository.findByHallAndSeatTypeAndSeatRowAndSeatNumber(hall, type, row, col);
        Member member = null;
        Optional<Member> memData = memberRepository.findById(userId);
        
        if (memData.isPresent()) {
            member = memData.get();
        } else {
        }
     
        Practice practice = Practice
                        .builder()
                        .seat(seat)
                         .member(member)
                         .build();


        practiceRepository.save(practice);

        System.out.println("register practice !!!!!!!!!!!!!!!");
        System.out.println("register practice !!!!!!!!!!!!!!!");
        System.out.println("register practice !!!!!!!!!!!!!!!");
        System.out.println(practice);

        return RsData.of("S-1", "연습 등록이 완료되었습니다.", practice);
    }


}
