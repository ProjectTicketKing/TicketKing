package com.example.ticketKing.domain.Seat.service;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.repository.HallRepository;
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
    private final HallRepository hallRepository;

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }


    @Transactional
    public RsData<Seat> register(String seatType, Integer seatRow, Integer seatNumber, String status, Hall hall) {

        Seat seat = Seat
                .builder()
                .seatType(seatType)
                .seatRow(seatRow)
                .seatNumber(seatNumber)
                .status(status)
                .hall(hall)
                .build();

        seatRepository.save(seat);

        return RsData.of("S-1", "좌석 등록이 완료되었습니다", seat);
    }

    public List<Seat> getSeatsByHallAndType(String hallName, String type) {
        Hall hall = hallRepository.findByName(hallName);
        return seatRepository.findByHallAndSeatType(hall, type);
    }


    public List<Seat> getSeatsByType(String type) {

        return seatRepository.findBySeatType(type);
    }

    public int getRow(String hall, String type) {
        if (Objects.equals(hall, "KSPO") && Objects.equals(type, "VIP")) {
            return 24;
        } else if (Objects.equals(hall, "KSPO") && Objects.equals(type, "R")) {
            return 22;
        } else if (Objects.equals(hall, "KSPO") && Objects.equals(type, "A")) {
            return 20;
        }
        else if (Objects.equals(hall, "OLYSDM") && Objects.equals(type, "VIP")) {
            return 25;
        }
        else if (Objects.equals(hall, "OLYSDM") && Objects.equals(type, "S")) {
            return 15;
        }
        else if (Objects.equals(hall, "OLYSDM") && Objects.equals(type, "G")) {
            return 25;
        }

        return 10;
    }


    public int getColumn(String hall, String type) {
        if (Objects.equals(hall, "KSPO") && Objects.equals(type, "VIP")) {
            return 20;
        } else if (Objects.equals(hall, "KSPO") && Objects.equals(type, "R")) {
            return 20;
        } else if (Objects.equals(hall, "KSPO") && Objects.equals(type, "A")) {
            return 26;
        }
        else if (Objects.equals(hall, "OLYSDM") && Objects.equals(type, "VIP")) {
            return 40;
        }
        else if (Objects.equals(hall, "OLYSDM") && Objects.equals(type, "S")) {
            return 25;
        }
        else if (Objects.equals(hall, "OLYSDM") && Objects.equals(type, "G")) {
            return 35;
        }

        return 10;
    }

}