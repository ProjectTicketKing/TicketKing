package com.example.ticketKing.domain.Seat.service;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.repository.HallRepository;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.repository.SeatRepository;
import com.example.ticketKing.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {
    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    private static final String INVALID_STATUS = "invalid";
    private static final String VALID_STATUS = "valid";

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }


    @Transactional
    public RsData<Seat> register(String seatType, Integer seatRow, Integer seatNumber, String status, Hall hall)
    {
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

    @Transactional
    public void seatStatusSave(String hallName,String type,Integer row, Integer column){
        Hall hall = hallRepository.findByName(hallName);
        Seat seat = seatRepository.findByHallAndSeatTypeAndSeatRowAndSeatNumber(hall,type,row,column);
        seat.setStatus(INVALID_STATUS);
        seatRepository.save(seat);
    }

    public List<Seat> getSeatsByHallAndType(String hallName, String type)
    {
        Hall hall = hallRepository.findByName(hallName);
        return seatRepository.findByHallAndSeatType(hall, type);
    }


    public List<Seat> getSeatsByType(String type) {

        return seatRepository.findBySeatType(type);
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
        List<Seat> seats = seatRepository.findByHallAndSeatType(hall, type);

        List<Seat> validSeats = new ArrayList<>();
        for (Seat seat : seats) {
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



    public String getSeatStatus(String hallName, String type, int row, int column)
    {

        Hall hall = hallRepository.findByName(hallName);
        Seat targetSeat = seatRepository.findByHallAndSeatTypeAndSeatRowAndSeatNumber(hall,type,row,column);
        return targetSeat.getStatus();

    }


    @Transactional
    public void updateRandomSeatStatusToInvalid(String hallName, String type)
    {
        Hall hall = hallRepository.findByName(hallName);
        List<Seat> seats = seatRepository.findByHallAndSeatTypeAndStatus(hall, type, VALID_STATUS);

        if (!seats.isEmpty()) {
            // 랜덤으로 Seat 선택
            Seat randomSeat = seats.get(new Random().nextInt(seats.size()));
            randomSeat.setStatus(INVALID_STATUS);
            seatRepository.save(randomSeat);
        }
    }


    public String checkSeatStatus(String hall, String type, Integer row, Integer col)
    {
        Hall targetedHall = hallRepository.findByName(hall);
        Seat seat = seatRepository.findByHallAndSeatTypeAndSeatRowAndSeatNumber(targetedHall, type, row, col);

        if (seat != null) {
            if (seat.getStatus().equals(VALID_STATUS)) {
                return VALID_STATUS;
            } else if (seat.getStatus().equals(INVALID_STATUS)) {
                return INVALID_STATUS;
            }
        }

        return "none-exist";
    }



    @Transactional
    public void initializeAllSeats() {
        List<Seat> seats = getAllSeats();
        for (Seat seat : seats) {
            seat.setStatus(VALID_STATUS);
            seatRepository.save(seat);
        }
    }

}