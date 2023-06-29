package com.example.ticketKing.domain.Hall.repository;

import com.example.ticketKing.domain.Hall.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall, Long> {

    Hall findByName(String name);
}
