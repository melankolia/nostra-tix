package com.tix.nostra.nostra_tix.dto;

import java.util.List;

public record ScheduleStudioDTO(
                StudioDTO studio,
                List<ScheduleDTO> schedules) {
}