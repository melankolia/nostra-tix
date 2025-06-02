package com.tix.nostra.nostra_tix.service;

import java.util.List;

import com.tix.nostra.nostra_tix.dto.BookingDTO;
import com.tix.nostra.nostra_tix.dto.BookingSeatResponseDTO;
import com.tix.nostra.nostra_tix.dto.UserTicketResponseDTO;

public interface BookingService {

    BookingSeatResponseDTO findAll(Long scheduleId, Long studioId);

    List<UserTicketResponseDTO> findByUserId(Long userId);

    UserTicketResponseDTO findByBookingId(Long bookingId);

    Boolean createBooking(Long scheduleId, BookingDTO bookingDTO);

    Boolean payBooking(Long bookingId);

    Boolean cancelBooking(Long bookingId);
}
