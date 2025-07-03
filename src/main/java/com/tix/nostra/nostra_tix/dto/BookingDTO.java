package com.tix.nostra.nostra_tix.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record BookingDTO(@NotEmpty(message = "At least one seat must be selected") List<Long> seatIds) {
}
