package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.ResultPageResponseDTO;
import com.tix.nostra.nostra_tix.dto.ScheduleTheaterDTO;
import com.tix.nostra.nostra_tix.service.ScheduleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;

@PreAuthorize("hasRole('USER')")

@RestController
@RequestMapping("/api/v1/schedules")
@SecurityRequirement(name = "Bearer Authentication")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<ResultPageResponseDTO<ScheduleTheaterDTO>> findAll(
            @RequestParam @NotNull(message = "Movie ID is required") Long movieId,
            @RequestParam(required = false) Long studioTypeId,
            @RequestParam @NotNull(message = "City ID is required") Long cityId,
            @RequestParam(required = false) Long currentDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Set default value at runtime
        if (currentDate == null) {
            currentDate = System.currentTimeMillis() / 1000;
        }

        return ResponseEntity.ok(scheduleService.findAll(movieId, studioTypeId, cityId, currentDate, page, size));
    }
}
