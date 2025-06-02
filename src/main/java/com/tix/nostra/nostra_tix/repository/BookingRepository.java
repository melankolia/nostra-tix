package com.tix.nostra.nostra_tix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tix.nostra.nostra_tix.domain.Booking;
import com.tix.nostra.nostra_tix.projection.UserTicketProjection;

public interface BookingRepository extends JpaRepository<Booking, Long> {

        List<Booking> findByScheduleId(Long scheduleId);

        List<Booking> findByUserId(Long userId);

        @Query("""
                        SELECT b.id as id,
                               b.schedule.movie.name as movieName,
                               b.schedule.movie.imageURI as movieImageURI,
                               b.schedule.studio.theater.name as theaterName,
                               b.schedule.studio.name as studioName,
                               function('string_agg', s.seatNumber, ', ') as seatNumbers
                        FROM Booking b
                        LEFT JOIN b.seats s
                        WHERE b.user.id = :userId
                        GROUP BY b.id, b.schedule.movie.name, b.schedule.movie.imageURI,
                                 b.schedule.studio.theater.name, b.schedule.studio.name
                        """)
        List<UserTicketProjection> findTicketsByUserId(@Param("userId") Long userId);

}
