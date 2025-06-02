package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.ResultPageResponseDTO;
import com.tix.nostra.nostra_tix.dto.ScheduleTheaterDTO;
import com.tix.nostra.nostra_tix.service.ScheduleService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<ResultPageResponseDTO<ScheduleTheaterDTO>> findAll(
            @RequestParam @NotNull(message = "Movie ID is required") Long movieId,
            @RequestParam(required = false) Long studioTypeId,
            @RequestParam @NotNull(message = "City ID is required") Long cityId,
            @RequestParam @NotNull(message = "Current date is required") Long currentDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(scheduleService.findAll(movieId, studioTypeId, cityId, currentDate, page, size));
    }
}
