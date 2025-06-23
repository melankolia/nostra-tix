package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.dto.UserTicketResponseDTO;
import com.tix.nostra.nostra_tix.service.BookingService;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/user-tickets")
public class UserTicketController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResultResponseDTO<List<UserTicketResponseDTO>>> getUserTickets(@PathVariable Long userId) {
        List<UserTicketResponseDTO> tickets = bookingService.findByUserId(userId);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", tickets));
    }

    @GetMapping("/{bookingId}/detail")
    public ResponseEntity<ResultResponseDTO<UserTicketResponseDTO>> getUserTicket(@PathVariable Long bookingId) {
        UserTicketResponseDTO ticket = bookingService.findByBookingId(bookingId);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", ticket));
    }
}
