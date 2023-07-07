package com.example.ticketKing.domain.Seat.service;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.repository.HallRepository;
import com.example.ticketKing.domain.Robot.Robot;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.repository.SeatRepository;
import com.example.ticketKing.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.hibernate.resource.beans.container.spi.BeanLifecycleStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {
    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    private Integer numRobots = 5;

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

    public int[][] getValidSeats(String hallName, String type) {
        Hall hall = hallRepository.findByName(hallName);
       List<Seat> seats = seatRepository.findByHallAndSeatType(hall, type);


        List<Seat> validSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.getStatus().equals("valid")) {
                validSeats.add(seat);
            }
        }

        Seat[] validSeatsArray = validSeats.toArray(new Seat[0]);

        int[][] seatRowCol = new int[validSeatsArray.length][2];
        for (int i = 0; i < validSeatsArray.length; i++) {
            seatRowCol[i][0] = validSeatsArray[i].getSeatRow();
            seatRowCol[i][1] = validSeatsArray[i].getSeatNumber();
        }
        return seatRowCol;
    }



    public String getSeatStatus(String hallName, String type, int row, int column) {

        Hall hall = hallRepository.findByName(hallName);
        Seat targetSeat = seatRepository.findByHallAndSeatTypeAndSeatRowAndSeatNumber(hall,type,row,column);
        return targetSeat.getStatus();

    }


    @Transactional
    public void updateRandomSeatStatusToInvalid(String hallName, String type) {
        Hall hall = hallRepository.findByName(hallName);
        List<Seat> seats = seatRepository.findByHallAndSeatTypeAndStatus(hall, type, "valid");

        if (!seats.isEmpty()) {
            // 랜덤으로 Seat 선택
            Seat randomSeat = seats.get(new Random().nextInt(seats.size()));
            randomSeat.setStatus("invalid");
            seatRepository.save(randomSeat);
        }
    }




    // Robot 도입
    public void manageRobots(String hallName, String type){

        while(numRobots>0){
            randomSeatUpdate(hallName, type);
            numRobots--;
        }
    }


    @Transactional
    public void randomSeatUpdate(String hallName, String type) {
        Hall hall = hallRepository.findByName(hallName);
        List<Seat> seats = seatRepository.findByHallAndSeatTypeAndStatus(hall, type, "valid");

        // 로봇 객체 생성
        Robot robot = new Robot();
        boolean reservationSuccess = robot.tryReserve();

        if (!seats.isEmpty() && reservationSuccess) {
            // 랜덤으로 Seat 선택
            Seat randomSeat = seats.get(new Random().nextInt(seats.size()));
            randomSeat.setStatus("invalid");
            randomSeat.setReservedBy(robot.getId());
            seatRepository.save(randomSeat);
        }

    }






    public String checkSeatStatus(String hall, String type, Integer row, Integer col){
    Hall targetedHall = hallRepository.findByName(hall);

     Seat seat = seatRepository.findByHallAndSeatTypeAndSeatRowAndSeatNumber(targetedHall, type, row, col);

        if (seat != null) {
            if (seat.getStatus().equals("valid")) {
                return "valid";
            } else if (seat.getStatus().equals("invalid")) {
                return "invalid";
            }
        }
        return "none-exist";
    }


}