package com.tix.nostra.nostra_tix.dto;

import java.util.List;

import com.tix.nostra.nostra_tix.domain.StudioType;

public record BookingSeatResponseDTO(
        MovieScheduleDTO movie,
        List<BookingSeatDTO> bookingSeatDTO,
        StudioType studioType) {
}
