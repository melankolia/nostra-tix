package com.tix.nostra.nostra_tix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tix.nostra.nostra_tix.domain.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAll();

    List<Seat> findByStudioId(Long studioId);

}