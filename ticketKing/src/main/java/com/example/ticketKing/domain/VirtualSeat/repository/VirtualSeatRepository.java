package com.example.ticketKing.domain.VirtualSeat.repository;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.VirtualSeat.entity.VirtualSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirtualSeatRepository extends JpaRepository<VirtualSeat,Long> {
    List<VirtualSeat> findByHall(Hall hall);

    List<VirtualSeat> findBySeatType(String seatType);

    List<VirtualSeat> findByHallAndSeatType(Hall hall, String seatType);

    VirtualSeat findByHallAndSeatTypeAndSeatRowAndSeatNumber(Hall hall, String seatType,Integer seatRow, Integer seatNumber);


    List<VirtualSeat> findByHallAndSeatTypeAndStatus(Hall hall, String type, String valid);
}
