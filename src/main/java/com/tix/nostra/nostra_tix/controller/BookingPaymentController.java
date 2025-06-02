package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.service.BookingService;

@RestController
@RequestMapping("/api/booking-payment")
public class BookingPaymentController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{bookingId}/pay")
    public ResponseEntity<Boolean> payBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.payBooking(bookingId));
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }
}
