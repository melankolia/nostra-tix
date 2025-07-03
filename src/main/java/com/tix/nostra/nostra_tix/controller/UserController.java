package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.UserDetailResponseDTO;
import com.tix.nostra.nostra_tix.dto.UserTicketResponseDTO;
import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.service.BookingService;
import com.tix.nostra.nostra_tix.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@PreAuthorize("hasRole('USER')")

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<ResultResponseDTO<UserDetailResponseDTO>> getUserDetail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserDetailResponseDTO userDetail = userService.getUserDetail(email);

        return ResponseEntity.ok(new ResultResponseDTO<>("OK", userDetail));
    }

    @GetMapping("/tickets")
    public ResponseEntity<ResultResponseDTO<List<UserTicketResponseDTO>>> getUserTickets() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserTicketResponseDTO> tickets = bookingService.findByUserEmail(email);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", tickets));
    }

    @GetMapping("/tickets/{bookingId}")
    public ResponseEntity<ResultResponseDTO<UserTicketResponseDTO>> getUserTicket(@PathVariable Long bookingId) {
        UserTicketResponseDTO ticket = bookingService.findByBookingId(bookingId);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", ticket));
    }

}
