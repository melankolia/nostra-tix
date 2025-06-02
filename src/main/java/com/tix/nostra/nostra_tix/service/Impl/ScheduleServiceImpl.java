package com.tix.nostra.nostra_tix.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.Schedule;
import com.tix.nostra.nostra_tix.domain.Studio;
import com.tix.nostra.nostra_tix.domain.Theater;
import com.tix.nostra.nostra_tix.dto.ResultPageResponseDTO;
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
    public ResultPageResponseDTO<ScheduleTheaterDTO> findAll(Long movieId, Long studioTypeId, Long cityId,
            Long currentDate, int page,
            int size) {
        Date convertDate = currentDate != null ? new Date(currentDate * 1000) : new Date();
        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> schedulePage;
        List<Schedule> schedules;

        if (movieId != null && cityId != null && studioTypeId != null) {
            schedulePage = scheduleRepository
                    .findAllByMovieIdAndStudioStudioTypeIdAndStudioTheaterCityIdAndMovieShowingStartDateLessThanEqualAndMovieShowingEndDateGreaterThanEqual(
                            movieId, studioTypeId, cityId, convertDate, convertDate, pageable);
            if (schedulePage.isEmpty()) {
                throw new ResourceNotFoundException(
                        "No schedules found for the given movie, studio type, city, and date");
            }
            schedules = schedulePage.getContent();
        } else if (movieId != null && cityId != null) {
            schedulePage = scheduleRepository
                    .findAllByMovieIdAndAndStudioTheaterCityIdAndMovieShowingStartDateLessThanEqualAndMovieShowingEndDateGreaterThanEqual(
                            movieId, cityId, convertDate, convertDate, pageable);
            if (schedulePage.isEmpty()) {
                throw new ResourceNotFoundException("No schedules found for the given movie, city, and date");
            }
            schedules = schedulePage.getContent();
        } else {
            schedulePage = scheduleRepository.findAll(pageable);
            schedules = schedulePage.getContent();
        }

        Map<Theater, Map<Studio, List<Schedule>>> schedulesByTheaterAndStudio = schedules.stream()
                .collect(
                        Collectors.groupingBy(schedule -> schedule.getStudio().getTheater(),
                                Collectors.groupingBy(schedule -> schedule.getStudio())));

        List<ScheduleTheaterDTO> theaterDTOs = schedulesByTheaterAndStudio.entrySet().stream()
                .map(theaterEntry -> {
                    List<ScheduleStudioDTO> studioDTOs = theaterEntry.getValue().entrySet().stream()
                            .map(studioEntry -> new ScheduleStudioDTO(studioEntry.getKey(), studioEntry.getValue()))
                            .collect(Collectors.toList());
                    return new ScheduleTheaterDTO(theaterEntry.getKey(), studioDTOs);
                })
                .collect(Collectors.toList());

        return new ResultPageResponseDTO<>(theaterDTOs, schedulePage.getTotalPages(), schedulePage.getTotalElements());
    }
}
