package com.tix.nostra.nostra_tix.service;

import java.util.List;

import com.tix.nostra.nostra_tix.dto.ScheduleTheaterDTO;

public interface ScheduleService {
    List<ScheduleTheaterDTO> findAll(Long movieId, Long studioTypeId, Long cityId, Long currentDate);
}
