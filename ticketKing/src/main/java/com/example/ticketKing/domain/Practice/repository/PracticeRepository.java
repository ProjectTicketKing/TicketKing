package com.example.ticketKing.domain.Practice.repository;

import com.example.ticketKing.domain.Practice.entity.Practice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PracticeRepository extends JpaRepository<Practice,Long> {

}
