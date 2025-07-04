package com.tix.nostra.nostra_tix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tix.nostra.nostra_tix.domain.Booking;
import com.tix.nostra.nostra_tix.projection.BookingListProjection;
import com.tix.nostra.nostra_tix.projection.BookingWithSeatsProjection;
import com.tix.nostra.nostra_tix.projection.UserTicketProjection;

public interface BookingRepository extends JpaRepository<Booking, Long> {

       @Query("""
                     SELECT b.id as id,
                            b.schedule.studio.theater.city.name as cityName,
                            b.schedule.studio.theater.name as theaterName,
                            b.schedule.studio.name as studioName,
                            b.schedule.movie.name as movieName,
                            b.bookingStatusEnum as bookingStatus
                     FROM Booking b
                     """)
       List<BookingListProjection> findAllProjectedBy();

       List<Booking> findByScheduleId(Long scheduleId);

       List<Booking> findByUserId(Long userId);

       List<Booking> findByUserEmail(String email);

       @Query("""
                     SELECT b.id as id,
                            s.id as seatId,
                            b.schedule.id as scheduleId,
                            b.schedule.studio.id as studioId,
                            b.bookingStatusEnum as status
                     FROM Booking b
                     JOIN b.seats s
                     WHERE b.schedule.id = :scheduleId
                     """)
       List<BookingWithSeatsProjection> findBookingsWithSeatsByScheduleId(@Param("scheduleId") Long scheduleId);

       @Query("""
                     SELECT b.id as id,
                            b.schedule.movie.name as movieName,
                            b.schedule.movie.imageURI as movieImageURI,
                            b.schedule.studio.theater.name as theaterName,
                            b.schedule.studio.name as studioName,
                            function('string_agg', s.seatNumber, ', ') as seatNumbers,
                            b.bookingStatusEnum as status
                     FROM Booking b
                     LEFT JOIN b.seats s
                     WHERE b.user.email = :email
                     GROUP BY b.id, b.schedule.movie.name, b.schedule.movie.imageURI,
                              b.schedule.studio.theater.name, b.schedule.studio.name
                     """)
       List<UserTicketProjection> findTicketsByUserEmail(@Param("email") String email);

       @Query("""
                     SELECT b.id as id,
                            b.schedule.movie.name as movieName,
                            b.schedule.movie.imageURI as movieImageURI,
                            b.schedule.studio.theater.name as theaterName,
                            b.schedule.studio.name as studioName,
                            function('string_agg', s.seatNumber, ', ') as seatNumbers,
                            b.bookingStatusEnum as status
                     FROM Booking b
                     LEFT JOIN b.seats s
                     WHERE b.id = :bookingId
                     GROUP BY b.id, b.schedule.movie.name, b.schedule.movie.imageURI,
                              b.schedule.studio.theater.name, b.schedule.studio.name
                     """)
       List<UserTicketProjection> findTicketsByBookingId(@Param("bookingId") Long bookingId);

       @Query("""
                     SELECT b FROM Booking b
                     WHERE b.expiredDate < CURRENT_TIMESTAMP
                     AND (b.bookingStatusEnum = 'PENDING'
                     OR b.bookingStatusEnum = 'WAITING_TO_PAY')
                     """)
       List<Booking> findExpiredBookings();

}
