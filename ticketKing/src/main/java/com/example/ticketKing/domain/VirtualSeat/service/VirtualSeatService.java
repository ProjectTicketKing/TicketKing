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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VirtualSeatService {
    private static final String INVALID_STATUS = "invalid";
    private static final String VALID_STATUS = "valid";
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

    public int getRow(String hall, String type) {
        Map<String, Map<String, Integer>> hallTypeToRows = new HashMap<>();

        hallTypeToRows.put("KSPO", Map.of("VIP", 24, "R", 22, "A", 20));
        hallTypeToRows.put("OLYSDM", Map.of("VIP", 25, "S", 20, "G", 25));

        return hallTypeToRows.getOrDefault(hall, Map.of()).getOrDefault(type, 10);
    }

    public int getColumn(String hall, String type) {
        Map<String, Map<String, Integer>> hallTypeToColumns = new HashMap<>();

        hallTypeToColumns.put("KSPO", Map.of("VIP", 20, "R", 20, "A", 26));
        hallTypeToColumns.put("OLYSDM", Map.of("VIP", 35, "S", 25, "G", 35));

        return hallTypeToColumns.getOrDefault(hall, Map.of()).getOrDefault(type, 10);
    }

    public int[][] getValidSeats(String hallName, String type) {
        Hall hall = hallRepository.findByName(hallName);
        List<VirtualSeat> seats = virtualSeatRepository.findByHallAndSeatType(hall, type);

        List<VirtualSeat> validSeats = new ArrayList<>();
        for (VirtualSeat seat : seats) {
            if (seat.getStatus().equals(VALID_STATUS)) {
                validSeats.add(seat);
            }
        }

        int[][] seatRowCol = new int[validSeats.size()][2];
        for (int i = 0; i < validSeats.size(); i++) {
            seatRowCol[i][0] = validSeats.get(i).getSeatRow();
            seatRowCol[i][1] = validSeats.get(i).getSeatNumber();
        }
        return seatRowCol;
    }


    public String checkSeatStatus(String hall, String type, Integer row, Integer col)
    {
        Hall targetedHall = hallRepository.findByName(hall);
        VirtualSeat seat = virtualSeatRepository.findByHallAndSeatTypeAndSeatRowAndSeatNumber(targetedHall, type, row, col);

        if (seat != null) {
            if (seat.getStatus().equals(VALID_STATUS)) {
                return VALID_STATUS;
            } else if (seat.getStatus().equals(INVALID_STATUS)) {
                return INVALID_STATUS;
            }
        }

        return "none-exist";
    }

}
