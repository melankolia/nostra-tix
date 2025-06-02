package com.tix.nostra.nostra_tix.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.Booking;
import com.tix.nostra.nostra_tix.domain.Schedule;
import com.tix.nostra.nostra_tix.domain.Seat;
import com.tix.nostra.nostra_tix.domain.User;
import com.tix.nostra.nostra_tix.dto.BookingDTO;
import com.tix.nostra.nostra_tix.dto.BookingSeatDTO;
import com.tix.nostra.nostra_tix.dto.BookingSeatResponseDTO;
import com.tix.nostra.nostra_tix.dto.MovieScheduleDTO;
import com.tix.nostra.nostra_tix.dto.UserTicketResponseDTO;
import com.tix.nostra.nostra_tix.exception.DuplicateUserDataException;
import com.tix.nostra.nostra_tix.exception.ResourceNotFoundException;
import com.tix.nostra.nostra_tix.projection.UserTicketProjection;
import com.tix.nostra.nostra_tix.repository.BookingRepository;
import com.tix.nostra.nostra_tix.repository.ScheduleRepository;
import com.tix.nostra.nostra_tix.repository.SeatRepository;
import com.tix.nostra.nostra_tix.repository.UserRepository;
import com.tix.nostra.nostra_tix.service.BookingService;
import com.tix.nostra.nostra_tix.util.BookingStatusEnum;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public List<UserTicketResponseDTO> findByUserId(Long userId) {
        List<UserTicketProjection> tickets = bookingRepository.findTicketsByUserId(userId);

        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("Ticket's not found");
        }

        return tickets.stream()
                .map(ticket -> new UserTicketResponseDTO(
                        ticket.getId(),
                        ticket.getMovieName(),
                        ticket.getMovieImageURI(),
                        ticket.getTheaterName(),
                        ticket.getStudioName(),
                        Arrays.asList(ticket.getSeatNumbers().split(", "))))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean payBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getExpiredDate().before(new Date())) {
            throw new ResourceNotFoundException("Booking expired");
        }

        if (booking.getStatus().equals(BookingStatusEnum.PAID)) {
            throw new ResourceNotFoundException("Booking already paid");
        }

        if (booking.getStatus().equals(BookingStatusEnum.CANCELLED)) {
            throw new ResourceNotFoundException("Booking cancelled");
        }

        booking.setStatus(BookingStatusEnum.PAID);
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public Boolean cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getExpiredDate().before(new Date())) {
            throw new ResourceNotFoundException("Booking expired");
        }

        if (booking.getStatus().equals(BookingStatusEnum.PAID)) {
            throw new ResourceNotFoundException("Booking already paid");
        }

        if (booking.getStatus().equals(BookingStatusEnum.CANCELLED)) {
            throw new ResourceNotFoundException("Booking already cancelled");
        }

        booking.setStatus(BookingStatusEnum.CANCELLED);
        bookingRepository.save(booking);
        return true;
    }

    @Override
    @Transactional
    public Boolean createBooking(Long scheduleId, BookingDTO bookingDTO) {
        List<Booking> existingBookings = bookingRepository.findByUserId(bookingDTO.userId());
        if (!existingBookings.isEmpty()) {
            Booking existingBooking = existingBookings.get(0);
            if (existingBooking.getExpiredDate().after(new Date()) &&
                    existingBooking.getStatus() == BookingStatusEnum.WAITING) {
                throw new DuplicateUserDataException("User already has an active booking for this schedule");
            }
        }

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        User user = userRepository.findById(bookingDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Calculate total price based on schedule price and seat additional prices
        BigDecimal totalPrice = schedule.getPrice();
        Set<Seat> selectedSeats = bookingDTO.seatIds().stream()
                .map(seatId -> seatRepository.findById(seatId)
                        .orElseThrow(() -> new ResourceNotFoundException("Seat not found")))
                .collect(Collectors.toSet());

        for (Seat seat : selectedSeats) {
            if (seat.getSeatType() != null && seat.getSeatType().getAdditionalPrice() != null) {
                totalPrice = totalPrice.add(seat.getSeatType().getAdditionalPrice());
            }
        }

        Booking booking = new Booking();
        booking.setSchedule(schedule);
        booking.setUser(user);
        booking.setSeats(selectedSeats);
        booking.setStatus(BookingStatusEnum.WAITING);
        booking.setOrderDate(new Date());
        booking.setTotalPrice(totalPrice);
        booking.setPaid(false);
        booking.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        bookingRepository.save(booking);

        return true;
    }
}
