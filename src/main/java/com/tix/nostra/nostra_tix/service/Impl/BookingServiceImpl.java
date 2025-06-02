package com.tix.nostra.nostra_tix.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.Booking;
import com.tix.nostra.nostra_tix.domain.Schedule;
import com.tix.nostra.nostra_tix.domain.Seat;
import com.tix.nostra.nostra_tix.dto.BookingSeatDTO;
import com.tix.nostra.nostra_tix.dto.BookingSeatResponseDTO;
import com.tix.nostra.nostra_tix.dto.MovieScheduleDTO;
import com.tix.nostra.nostra_tix.exception.ResourceNotFoundException;
import com.tix.nostra.nostra_tix.repository.BookingRepository;
import com.tix.nostra.nostra_tix.repository.ScheduleRepository;
import com.tix.nostra.nostra_tix.repository.SeatRepository;
import com.tix.nostra.nostra_tix.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public BookingSeatResponseDTO findAll(Long scheduleId, Long studioId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        MovieScheduleDTO movieScheduleDTO = new MovieScheduleDTO(
                schedule.getMovie().getId(),
                schedule.getMovie().getName(),
                schedule.getMovie().getImageURI(),
                schedule.getShowTime(),
                schedule.getEndShowTime(),
                schedule.getStudio().getTheater().getName(),
                schedule.getStudio().getName());

        List<Booking> bookings = bookingRepository.findByScheduleId(scheduleId);
        List<Seat> seats = seatRepository.findByStudioId(studioId);
        List<BookingSeatDTO> bookingSeats = new ArrayList<>();

        for (Seat seat : seats) {
            boolean isBooked = false;
            for (Booking booking : bookings) {
                if (booking.getSeats().contains(seat)) {
                    isBooked = true;
                    break;
                }
            }

            bookingSeats.add(new BookingSeatDTO(
                    seat.getId(),
                    seat.getSeatNumber(),
                    seat.getRowIndex(),
                    seat.getColumnIndex(),
                    seat.getSeatType().getName(),
                    seat.getSeatType().getAdditionalPrice(),
                    isBooked,
                    seat.getAvailable(),
                    seat.getVisible()));
        }

        BookingSeatResponseDTO bookingSeatResponseDTO = new BookingSeatResponseDTO(movieScheduleDTO, bookingSeats);

        return bookingSeatResponseDTO;
    }
}
