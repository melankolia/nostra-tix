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
import com.tix.nostra.nostra_tix.projection.BookingListProjection;
import com.tix.nostra.nostra_tix.projection.BookingWithSeatsProjection;
import com.tix.nostra.nostra_tix.projection.ScheduleByIdProjection;
import com.tix.nostra.nostra_tix.projection.SeatByStudioIdProjection;
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
        ScheduleByIdProjection schedule = scheduleRepository.findByIdProjectedBy(scheduleId);
        if (schedule == null) {
            throw new ResourceNotFoundException("Schedule not found");
        }

        MovieScheduleDTO movieScheduleDTO = new MovieScheduleDTO(
                schedule.getId(),
                schedule.getMovieName(),
                schedule.getMovieImageURI(),
                schedule.getShowTime(),
                schedule.getEndShowTime(),
                schedule.getTheaterName(),
                schedule.getStudioName());

        List<BookingWithSeatsProjection> bookingSeats = bookingRepository.findBookingsWithSeatsByScheduleId(scheduleId);
        List<SeatByStudioIdProjection> seats = seatRepository.findByStudioIdProjectedBy(studioId);
        List<BookingSeatDTO> bookingSeatsDTO = new ArrayList<>();

        for (SeatByStudioIdProjection seat : seats) {
            boolean isBooked = bookingSeats.stream()
                    .anyMatch(booking -> booking.getSeatId().equals(seat.getId()));

            bookingSeatsDTO.add(new BookingSeatDTO(
                    seat.getId(),
                    seat.getSeatNumber(),
                    seat.getRowIndex(),
                    seat.getColumnIndex(),
                    seat.getSeatTypeName(),
                    seat.getAdditionalPrice(),
                    isBooked,
                    seat.getAvailable(),
                    seat.getVisible()));
        }

        return new BookingSeatResponseDTO(movieScheduleDTO, bookingSeatsDTO);
    }

    @Override
    public List<BookingListProjection> findAllBookingList() {
        return bookingRepository.findAllProjectedBy();
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
    public UserTicketResponseDTO findByBookingId(Long bookingId) {
        List<UserTicketProjection> tickets = bookingRepository.findTicketsByBookingId(bookingId);

        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("Ticket's not found");
        }

        return new UserTicketResponseDTO(
                tickets.get(0).getId(),
                tickets.get(0).getMovieName(),
                tickets.get(0).getMovieImageURI(),
                tickets.get(0).getTheaterName(),
                tickets.get(0).getStudioName(),
                Arrays.asList(tickets.get(0).getSeatNumbers().split(", ")));
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
    public Long createBooking(Long scheduleId, BookingDTO bookingDTO) {
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

        booking = bookingRepository.save(booking);
        Long bookingId = booking.getId();
        return bookingId;
    }

    @Override
    @Transactional
    public Boolean completeBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getExpiredDate().before(new Date())) {
            throw new ResourceNotFoundException("Booking expired");
        }

        if (booking.getStatus().equals(BookingStatusEnum.CANCELLED)) {
            throw new ResourceNotFoundException("Booking cancelled");
        }

        if (booking.getStatus().equals(BookingStatusEnum.COMPLETED)) {
            throw new ResourceNotFoundException("Booking already completed");
        }

        booking.setStatus(BookingStatusEnum.COMPLETED);
        bookingRepository.save(booking);
        return true;
    }
}
