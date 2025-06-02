package com.tix.nostra.nostra_tix.service;

import com.tix.nostra.nostra_tix.dto.BookingSeatResponseDTO;

public interface BookingService {

    BookingSeatResponseDTO findAll(Long scheduleId, Long studioId);
}
