package com.tix.nostra.nostra_tix.repository;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tix.nostra.nostra_tix.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
        Page<Schedule> findAllByMovieIdAndStudioStudioTypeIdAndStudioTheaterCityIdAndMovieShowingStartDateLessThanEqualAndMovieShowingEndDateGreaterThanEqual(
                        Long movieId,
                        Long studioTypeId,
                        Long cityId,
                        Date movieShowingStartDate,
                        Date movieShowingEndDate,
                        Pageable pageable);

        Page<Schedule> findAllByMovieIdAndAndStudioTheaterCityIdAndMovieShowingStartDateLessThanEqualAndMovieShowingEndDateGreaterThanEqual(
                        Long movieId,
                        Long cityId,
                        Date movieShowingStartDate,
                        Date movieShowingEndDate,
                        Pageable pageable);
}
