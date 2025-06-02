package com.tix.nostra.nostra_tix.service;

import java.util.List;

import com.tix.nostra.nostra_tix.dto.BookingDTO;
import com.tix.nostra.nostra_tix.dto.BookingSeatResponseDTO;
import com.tix.nostra.nostra_tix.dto.UserTicketResponseDTO;
import com.tix.nostra.nostra_tix.projection.BookingListProjection;

public interface BookingService {

    BookingSeatResponseDTO findAll(Long scheduleId, Long studioId);

    List<BookingListProjection> findAllBookingList();

    List<UserTicketResponseDTO> findByUserId(Long userId);

    UserTicketResponseDTO findByBookingId(Long bookingId);

    Long createBooking(Long scheduleId, BookingDTO bookingDTO);

    Boolean payBooking(Long bookingId);

    Boolean cancelBooking(Long bookingId);

    Boolean completeBooking(Long bookingId);
}
