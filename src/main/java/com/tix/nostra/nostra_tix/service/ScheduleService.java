package com.tix.nostra.nostra_tix.service;

import com.tix.nostra.nostra_tix.dto.ResultPageResponseDTO;
import com.tix.nostra.nostra_tix.dto.ScheduleTheaterDTO;

public interface ScheduleService {
    ResultPageResponseDTO<ScheduleTheaterDTO> findAll(Long movieId, Long studioTypeId, Long cityId, Long currentDate,
            int page,
            int size);
}
