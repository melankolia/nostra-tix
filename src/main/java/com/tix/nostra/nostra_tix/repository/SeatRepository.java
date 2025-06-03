package com.tix.nostra.nostra_tix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tix.nostra.nostra_tix.domain.Seat;
import com.tix.nostra.nostra_tix.projection.SeatByStudioIdProjection;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAll();

    @Query("""
                SELECT s.id as id,
                       s.seatNumber as seatNumber,
                       s.rowIndex as rowIndex,
                       s.columnIndex as columnIndex,
                       s.seatType.name as seatTypeName,
                       s.seatType.additionalPrice as additionalPrice,
                       s.isAvailable as available,
                       s.isVisible as visible
                FROM Seat s
                WHERE s.studio.id = :studioId
            """)
    List<SeatByStudioIdProjection> findByStudioIdProjectedBy(Long studioId);

}