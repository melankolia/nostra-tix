package com.tix.nostra.nostra_tix.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.Booking;
import com.tix.nostra.nostra_tix.domain.Schedule;
import com.tix.nostra.nostra_tix.domain.Seat;
import com.tix.nostra.nostra_tix.domain.Studio;
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
import com.tix.nostra.nostra_tix.repository.StudioRepository;
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

    @Autowired
    private StudioRepository studioRepository;

    @Override
    public BookingSeatResponseDTO findAll(Long scheduleId) {
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

        Studio studio = studioRepository.findById(schedule.getStudioId())
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found"));

        List<BookingWithSeatsProjection> bookingSeats = bookingRepository.findBookingsWithSeatsByScheduleId(scheduleId);
        List<SeatByStudioIdProjection> seats = seatRepository.findByStudioIdProjectedBy(schedule.getStudioId());
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

        return new BookingSeatResponseDTO(movieScheduleDTO, bookingSeatsDTO, studio.getStudioType());
    }

    @Override
    public List<BookingListProjection> findAllBookingList() {
        return bookingRepository.findAllProjectedBy();
    }

    @Override
    public List<UserTicketResponseDTO> findByUserEmail(String email) {
        List<UserTicketProjection> tickets = bookingRepository.findTicketsByUserEmail(email);

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
                        Arrays.asList(ticket.getSeatNumbers().split(", ")),
                        ticket.getStatus()))
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
                Arrays.asList(tickets.get(0).getSeatNumbers().split(", ")),
                tickets.get(0).getStatus());
    }

    @Override
    public Boolean payBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new ResourceNotFoundException("Booking can only be paid by the owner");
        }

        if (booking.getExpiredDate().before(new Date())) {
            throw new ResourceNotFoundException("Booking expired");
        }

        if (booking.getStatus().equals(BookingStatusEnum.WAITING_TO_PAY)) {
            throw new ResourceNotFoundException("Booking is waiting to pay");
        }

        if (booking.getStatus().equals(BookingStatusEnum.PAID)) {
            throw new ResourceNotFoundException("Booking already paid");
        }

        if (booking.getStatus().equals(BookingStatusEnum.CANCELLED)) {
            throw new ResourceNotFoundException("Booking cancelled");
        }

        booking.setStatus(BookingStatusEnum.WAITING_TO_PAY);
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

        if (booking.getStatus().equals(BookingStatusEnum.WAITING_TO_PAY)) {
            throw new ResourceNotFoundException("Booking is waiting to pay");
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

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // 1. Cek apakah user sudah punya booking aktif
        List<Booking> existingBookings = bookingRepository.findByUserEmail(email);
        if (!existingBookings.isEmpty()) {
            Booking existingBooking = existingBookings.get(0);
            if (existingBooking.getExpiredDate().after(new Date()) &&
                    (existingBooking.getStatus() == BookingStatusEnum.PENDING ||
                            existingBooking.getStatus() == BookingStatusEnum.WAITING_TO_PAY)) {
                throw new DuplicateUserDataException("User sudah memiliki booking aktif");
            }
        }

        // 2. Cek apakah schedule dan user valid
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule tidak ditemukan"));

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("User tidak ditemukan");
        }

        // 3. Ambil data kursi yang sudah dibooking
        List<BookingWithSeatsProjection> bookedSeats = bookingRepository.findBookingsWithSeatsByScheduleId(scheduleId);
        List<Long> bookedSeatIds = bookedSeats.stream()
                .map(BookingWithSeatsProjection::getSeatId)
                .collect(Collectors.toList());

        // 4. Validasi kursi yang dipilih
        Set<Seat> selectedSeats = new HashSet<>();
        for (Long seatId : bookingDTO.seatIds()) {
            // 4a. Cek apakah kursi ada
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException("Kursi tidak ditemukan"));

            // 4b. Cek apakah kursi sudah dibooking
            if (bookedSeatIds.contains(seatId)) {
                throw new DuplicateUserDataException("Kursi " + seat.getSeatNumber() + " sudah dibooking");
            }

            selectedSeats.add(seat);
        }

        // 5. Cek kursi mati (single gap)
        List<Seat> selectedSeatsList = new ArrayList<>(selectedSeats);
        List<SeatByStudioIdProjection> allSeats = seatRepository
                .findByStudioIdAndRowIndexProjectedBy(schedule.getStudio().getId(),
                        selectedSeatsList.get(0).getRowIndex());

        // Cek setiap 3 kursi berurutan
        for (int i = 0; i < allSeats.size() - 2; i++) {
            SeatByStudioIdProjection kursi1 = allSeats.get(i);
            SeatByStudioIdProjection kursi2 = allSeats.get(i + 1);
            SeatByStudioIdProjection kursi3 = allSeats.get(i + 2);

            // Cek apakah kursi dipilih atau sudah dibooking
            boolean kursi1Terisi = selectedSeats.stream().anyMatch(s -> s.getId().equals(kursi1.getId()))
                    || bookedSeatIds.contains(kursi1.getId());
            boolean kursi2Terisi = selectedSeats.stream().anyMatch(s -> s.getId().equals(kursi2.getId()))
                    || bookedSeatIds.contains(kursi2.getId());
            boolean kursi3Terisi = selectedSeats.stream().anyMatch(s -> s.getId().equals(kursi3.getId()))
                    || bookedSeatIds.contains(kursi3.getId());

            // Jika kursi 1 dan 3 terisi tapi kursi 2 kosong = kursi mati
            if (kursi1Terisi && !kursi2Terisi && kursi3Terisi) {
                throw new DuplicateUserDataException(
                        "Tidak boleh ada kursi kosong sendirian di antara kursi " +
                                kursi1.getSeatNumber() + " dan " + kursi3.getSeatNumber());
            }
        }

        // 6. Hitung total harga
        BigDecimal totalPrice = schedule.getPrice();
        for (Seat seat : selectedSeats) {
            if (seat.getSeatType() != null && seat.getSeatType().getAdditionalPrice() != null) {
                totalPrice = totalPrice.add(seat.getSeatType().getAdditionalPrice());
            }
        }

        // 7. Buat booking baru
        Booking booking = new Booking();
        booking.setSchedule(schedule);
        booking.setUser(user);
        booking.setSeats(selectedSeats);
        booking.setStatus(BookingStatusEnum.PENDING);
        booking.setOrderDate(new Date());
        booking.setTotalPrice(totalPrice);
        booking.setPaid(false);
        booking.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 10)); // Expired dalam 10 menit

        // 8. Simpan dan return ID booking
        booking = bookingRepository.save(booking);
        return booking.getId();
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

        if (booking.getStatus().equals(BookingStatusEnum.PAID)) {
            throw new ResourceNotFoundException("Booking already paid");
        }

        booking.setStatus(BookingStatusEnum.PAID);
        bookingRepository.save(booking);
        return true;
    }
}
