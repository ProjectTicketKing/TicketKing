package com.example.ticketKing.domain.VirtualSeat.service;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.repository.HallRepository;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.VirtualSeat.entity.VirtualSeat;
import com.example.ticketKing.domain.VirtualSeat.repository.VirtualSeatRepository;
import com.example.ticketKing.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VirtualSeatService {

    private final VirtualSeatRepository virtualSeatRepository;
    private final HallRepository hallRepository;


    @Transactional
    public RsData<VirtualSeat> register(String seatType, Integer seatRow, Integer seatNumber, String status, Hall hall) {
        VirtualSeat seat = VirtualSeat
                .builder()
                .seatType(seatType)
                .seatRow(seatRow)
                .seatNumber(seatNumber)
                .status(status)
                .hall(hall)
                .build();

        virtualSeatRepository.save(seat);
        return RsData.of("S-1", "좌석 등록이 완료되었습니다", seat);
    }



}
