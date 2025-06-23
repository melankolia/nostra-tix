package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.service.BookingService;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/booking-payment")
public class BookingPaymentController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{bookingId}/pay")
    public ResponseEntity<ResultResponseDTO<Boolean>> payBooking(@PathVariable Long bookingId) {
        ResultResponseDTO<Boolean> resultResponseDTO = new ResultResponseDTO<>(
                "OK",
                bookingService.payBooking(bookingId));

        return ResponseEntity.ok(resultResponseDTO);
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<ResultResponseDTO<Boolean>> cancelBooking(@PathVariable Long bookingId) {
        ResultResponseDTO<Boolean> resultResponseDTO = new ResultResponseDTO<>(
                "OK",
                bookingService.cancelBooking(bookingId));

        return ResponseEntity.ok(resultResponseDTO);
    }
}
