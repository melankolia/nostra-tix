package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.BookingDTO;
import com.tix.nostra.nostra_tix.dto.BookingScheduleResponseDTO;
import com.tix.nostra.nostra_tix.dto.BookingSeatResponseDTO;
import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.service.BookingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/v1/booking")
@SecurityRequirement(name = "Bearer Authentication")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<ResultResponseDTO<BookingScheduleResponseDTO>> createBooking(
            @PathVariable @NotNull(message = "Schedule ID is required") Long scheduleId,
            @RequestBody @Valid BookingDTO bookingDTO) {
        Long bookingId = bookingService.createBooking(scheduleId, bookingDTO);

        ResultResponseDTO<BookingScheduleResponseDTO> resultResponseDTO = new ResultResponseDTO<>(
                "OK",
                new BookingScheduleResponseDTO(bookingId));

        return ResponseEntity.ok(resultResponseDTO);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ResultResponseDTO<BookingSeatResponseDTO>> findAll(@PathVariable Long scheduleId,
            @RequestParam @NotNull Long studioId) {
        BookingSeatResponseDTO bookingSeatResponseDTO = bookingService.findAll(scheduleId, studioId);

        ResultResponseDTO<BookingSeatResponseDTO> resultResponseDTO = new ResultResponseDTO<>(
                "OK",
                bookingSeatResponseDTO);

        return ResponseEntity.ok(resultResponseDTO);
    }

}
