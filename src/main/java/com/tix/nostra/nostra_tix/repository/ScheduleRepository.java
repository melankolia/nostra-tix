package com.tix.nostra.nostra_tix.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tix.nostra.nostra_tix.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAll();

    List<Schedule> findAllByMovieIdAndStudioStudioTypeIdAndStudioTheaterCityIdAndMovieShowingStartDateLessThanEqualAndMovieShowingEndDateGreaterThanEqual(
            Long movieId,
            Long studioTypeId,
            Long cityId,
            Date movieShowingStartDate,
            Date movieShowingEndDate);

    List<Schedule> findAllByMovieIdAndAndStudioTheaterCityIdAndMovieShowingStartDateLessThanEqualAndMovieShowingEndDateGreaterThanEqual(
            Long movieId,
            Long cityId,
            Date movieShowingStartDate,
            Date movieShowingEndDate);
}
