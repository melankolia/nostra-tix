package com.tix.nostra.nostra_tix.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookingDTO(
        @NotNull(message = "Schedule ID is required") Long scheduleId,
        @NotEmpty(message = "At least one seat must be selected") List<Long> seatIds) {
}
