package com.example.ticketKing.domain.Seat.repository;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Seat.entity.Seat;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {

    List<Seat> findByHall(Hall hall);

    List<Seat> findBySeatType(String seatType);

    List<Seat> findByHallAndSeatType(Hall hall, String seatType);

    Seat findByHallAndSeatTypeAndSeatRowAndSeatNumber(Hall hall, String seatType,Integer seatRow, Integer seatNumber);


    List<Seat> findByHallAndSeatTypeAndStatus(Hall hall, String type, String valid);
}
