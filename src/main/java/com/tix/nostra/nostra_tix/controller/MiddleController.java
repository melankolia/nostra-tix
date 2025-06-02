package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.projection.BookingListProjection;
import com.tix.nostra.nostra_tix.service.BookingService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/middle")
public class MiddleController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/booking")
    public ResponseEntity<List<BookingListProjection>> getBookingList() {
        return ResponseEntity.ok(bookingService.findAllBookingList());
    }

    @PutMapping("/booking/{bookingId}/completed")
    public ResponseEntity<Boolean> completeBooking(@PathVariable @NotNull Long bookingId) {
        return ResponseEntity.ok(bookingService.completeBooking(bookingId));
    }
}
