package com.tix.nostra.nostra_tix.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookingDTO(
        Long scheduleId,
        @NotNull(message = "User ID is required") Long userId,
        @NotEmpty(message = "At least one seat must be selected") Set<Long> seatIds) {
}
