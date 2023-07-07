package com.example.ticketKing.domain.photo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticketKing.domain.photo.entity.Photo;
public interface PhotoRepository extends JpaRepository<Photo, Long>{
}
