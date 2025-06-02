package com.tix.nostra.nostra_tix.projection;

public interface BookingListProjection {
        Long getId();

        String getCityName();

        String getTheaterName();

        String getStudioName();

        String getMovieName();

        String getBookingStatus();
}