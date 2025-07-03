package com.tix.nostra.nostra_tix.projection;

import com.tix.nostra.nostra_tix.util.BookingStatusEnum;

public interface BookingWithSeatsProjection {
    Long getId();

    Long getSeatId();

    Long getScheduleId();

    Long getStudioId();

    BookingStatusEnum getStatus();
}