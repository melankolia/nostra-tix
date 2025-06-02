package com.tix.nostra.nostra_tix.dto;

import java.util.List;

public record BookingSeatResponseDTO(
                MovieScheduleDTO movie,
                List<BookingSeatDTO> bookingSeatDTO) {
}
