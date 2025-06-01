package com.tix.nostra.nostra_tix.dto;

import java.util.List;
import com.tix.nostra.nostra_tix.domain.Theater;

public record ScheduleTheaterDTO(
                Theater theater,
                List<ScheduleStudioDTO> studios) {
}