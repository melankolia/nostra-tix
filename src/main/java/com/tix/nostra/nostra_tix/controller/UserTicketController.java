package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.UserTicketResponseDTO;
import com.tix.nostra.nostra_tix.service.BookingService;

@RestController
@RequestMapping("/api/user-tickets")
public class UserTicketController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserTicketResponseDTO>> getUserTickets(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.findByUserId(userId));
    }

    @GetMapping("/{bookingId}/detail")
    public ResponseEntity<UserTicketResponseDTO> getUserTicket(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.findByBookingId(bookingId));
    }
}
