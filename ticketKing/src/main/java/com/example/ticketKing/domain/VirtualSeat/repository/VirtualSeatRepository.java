package com.example.ticketKing.domain.VirtualSeat.repository;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.VirtualSeat.entity.VirtualSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirtualSeatRepository extends JpaRepository<VirtualSeat,Long> {
    List<VirtualSeat> findByHallAndSeatTypeAndStatus(Hall hall, String type, String validStatus);
}
