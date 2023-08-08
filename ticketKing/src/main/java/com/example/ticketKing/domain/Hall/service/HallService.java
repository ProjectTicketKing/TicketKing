package com.example.ticketKing.domain.Hall.service;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.repository.HallRepository;
import com.example.ticketKing.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HallService
{

    private final HallRepository hallRepository;

    @Transactional
    public RsData<Hall> register(String name, Integer seatCount){

        Hall hall = Hall
                .builder()
                .name(name)
                .build();

        hallRepository.save(hall);

        return RsData.of("S-1", "콘서트장 등록이 완료되었습니다.", hall);
    }
}
