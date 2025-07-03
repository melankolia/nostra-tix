package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@PreAuthorize("hasRole('USER')")

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{email}")
    public ResponseEntity<ResultResponseDTO<UserDetailResponseDTO>> getUserDetail(
            @PathVariable @Valid @NotBlank String email) {
        UserDetailResponseDTO userDetail = userService.getUserDetail(email);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", userDetail));
    }

    @GetMapping("/tickets/{userId}")
    public ResponseEntity<ResultResponseDTO<List<UserTicketResponseDTO>>> getUserTickets(@PathVariable Long userId) {
        List<UserTicketResponseDTO> tickets = bookingService.findByUserId(userId);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", tickets));
    }

    @GetMapping("/tickets/{bookingId}/detail")
    public ResponseEntity<ResultResponseDTO<UserTicketResponseDTO>> getUserTicket(@PathVariable Long bookingId) {
        UserTicketResponseDTO ticket = bookingService.findByBookingId(bookingId);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", ticket));
    }
}
