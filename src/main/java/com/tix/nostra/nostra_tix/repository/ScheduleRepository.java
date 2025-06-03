package com.tix.nostra.nostra_tix.repository;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tix.nostra.nostra_tix.domain.Schedule;
import com.tix.nostra.nostra_tix.projection.ScheduleByIdProjection;

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

        @Query("""
                            SELECT s.id as id,
                                   s.movie.name as movieName,
                                   s.movie.imageURI as movieImageURI,
                                   s.showTime as showTime,
                                   s.endShowTime as endShowTime,
                                   s.studio.theater.name as theaterName,
                                   s.studio.name as studioName
                            FROM Schedule s
                            WHERE s.id = :id
                        """)
        ScheduleByIdProjection findByIdProjectedBy(Long id);
}
