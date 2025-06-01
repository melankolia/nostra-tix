package com.tix.nostra.nostra_tix.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.Schedule;
import com.tix.nostra.nostra_tix.domain.Studio;
import com.tix.nostra.nostra_tix.domain.Theater;
import com.tix.nostra.nostra_tix.dto.ScheduleStudioDTO;
import com.tix.nostra.nostra_tix.dto.ScheduleTheaterDTO;
import com.tix.nostra.nostra_tix.exception.ResourceNotFoundException;
import com.tix.nostra.nostra_tix.repository.ScheduleRepository;
import com.tix.nostra.nostra_tix.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleTheaterDTO> findAll(Long movieId, Long studioTypeId, Long cityId, Long currentDate) {
        Date convertDate = currentDate != null ? new Date(currentDate * 1000) : new Date();
        List<Schedule> schedules;

        if (movieId != null && cityId != null && studioTypeId != null) {
            schedules = scheduleRepository
                    .findAllByMovieIdAndStudioStudioTypeIdAndStudioTheaterCityIdAndMovieShowingStartDateLessThanEqualAndMovieShowingEndDateGreaterThanEqual(
                            movieId, studioTypeId, cityId, convertDate, convertDate);
            if (schedules.isEmpty()) {
                throw new ResourceNotFoundException(
                        "No schedules found for the given movie, studio type, city, and date");
            }
        } else if (movieId != null && cityId != null) {
            schedules = scheduleRepository
                    .findAllByMovieIdAndAndStudioTheaterCityIdAndMovieShowingStartDateLessThanEqualAndMovieShowingEndDateGreaterThanEqual(
                            movieId, cityId, convertDate, convertDate);
            if (schedules.isEmpty()) {
                throw new ResourceNotFoundException("No schedules found for the given movie, city, and date");
            }
        } else {
            schedules = scheduleRepository.findAll();
        }

        Map<Theater, Map<Studio, List<Schedule>>> schedulesByTheaterAndStudio = schedules.stream()
                .collect(
                        Collectors.groupingBy(schedule -> schedule.getStudio().getTheater(),
                                Collectors.groupingBy(schedule -> schedule.getStudio())));

        return schedulesByTheaterAndStudio.entrySet().stream()
                .map(theaterEntry -> {
                    List<ScheduleStudioDTO> studioDTOs = theaterEntry.getValue().entrySet().stream()
                            .map(studioEntry -> new ScheduleStudioDTO(studioEntry.getKey(), studioEntry.getValue()))
                            .collect(Collectors.toList());
                    return new ScheduleTheaterDTO(theaterEntry.getKey(), studioDTOs);
                })
                .collect(Collectors.toList());
    }
}
