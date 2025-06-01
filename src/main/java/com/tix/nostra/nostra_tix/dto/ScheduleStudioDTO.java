package com.tix.nostra.nostra_tix.dto;

import java.util.List;
import com.tix.nostra.nostra_tix.domain.Schedule;
import com.tix.nostra.nostra_tix.domain.Studio;

public record ScheduleStudioDTO(
        Studio studio,
        List<Schedule> schedules) {
}