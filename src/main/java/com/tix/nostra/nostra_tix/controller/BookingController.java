package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.tix.nostra.nostra_tix.service.BookingService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<BookingScheduleResponseDTO> createBooking(
            @PathVariable @NotNull(message = "Schedule ID is required") Long scheduleId,
            @RequestBody @Valid BookingDTO bookingDTO) {
        Long bookingId = bookingService.createBooking(scheduleId, bookingDTO);

        BookingScheduleResponseDTO bookingScheduleResponseDTO = new BookingScheduleResponseDTO(bookingId);

        return ResponseEntity.ok(bookingScheduleResponseDTO);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<BookingSeatResponseDTO> findAll(@PathVariable Long scheduleId,
            @RequestParam @NotNull Long studioId) {
        return ResponseEntity.ok(bookingService.findAll(scheduleId, studioId));
    }

}
