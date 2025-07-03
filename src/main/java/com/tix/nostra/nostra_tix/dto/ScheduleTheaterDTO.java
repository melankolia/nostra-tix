package com.tix.nostra.nostra_tix.dto;

import java.util.List;

public record ScheduleTheaterDTO(
        TheaterDTO theater,
        List<ScheduleStudioDTO> studios) {
}