package com.tix.nostra.nostra_tix.dto;

import java.math.BigDecimal;

public record BookingSeatDTO(
        Long id,
        String seatNumber,
        Integer rowIndex,
        Integer columnIndex,
        String seatType,
        BigDecimal additionalPrice,
        Boolean isBooked,
        Boolean isAvailable,
        Boolean isVisible) {

}