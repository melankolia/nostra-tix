package com.tix.nostra.nostra_tix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tix.nostra.nostra_tix.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByScheduleId(Long scheduleId);

    List<Booking> findByUserId(Long userId);

}
