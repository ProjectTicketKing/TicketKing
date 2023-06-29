package com.example.ticketKing.domain.Seat.service;

import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.repository.SeatRepository;
import com.example.ticketKing.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.hibernate.resource.beans.container.spi.BeanLifecycleStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {
    private final SeatRepository seatRepository;



    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }



    @Transactional
    public RsData<Seat> register(String seatType,Integer seatRow, Integer seatNumber,String status){

        Seat seat = Seat
                .builder()
                .seatType(seatType)
                .seatRow(seatRow)
                .seatNumber(seatNumber)
                .status(status)
                .build();

    seatRepository.save(seat);

    return RsData.of("S-1", "좌석 등록이 완료되었습니다",seat);
    }


    public List<Seat> getSeatsByType(String type) {

        return seatRepository.findBySeatType(type);
    }

    public int getRow(String type){
        if(Objects.equals(type, "VIP석")){
            return 10;
        }
        else if(Objects.equals(type, "일반석")){
            return 5;
        }
        return 10;
    }


    public int getColumn(String type){
        if(Objects.equals(type, "VIP석")){
            return 10;
        }
        else if(Objects.equals(type, "일반석")){
            return 5;
        }
        return 10;
    }
}

