package com.tix.nostra.nostra_tix.projection;

import java.util.Date;

public interface ScheduleByIdProjection {

    Long getId();

    String getMovieName();

    String getMovieImageURI();

    Date getShowTime();

    Date getEndShowTime();

    String getTheaterName();

    String getStudioName();
}
