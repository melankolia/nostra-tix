package com.tix.nostra.nostra_tix.projection;

public interface UserTicketProjection {
    Long getId();

    String getMovieName();

    String getMovieImageURI();

    String getTheaterName();

    String getStudioName();

    String getSeatNumbers();

    String getStatus();
}