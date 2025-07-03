package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.dto.StudioTypeLovDTO;
import com.tix.nostra.nostra_tix.service.StudioTypeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/v1/studio-types")
@SecurityRequirement(name = "Bearer Authentication")
public class StudioTypeController {

    @Autowired
    private StudioTypeService studioTypeService;

    @GetMapping
    public ResponseEntity<ResultResponseDTO<List<StudioTypeLovDTO>>> findAll() {
        List<StudioTypeLovDTO> studioTypes = studioTypeService.findAll();
        ResultResponseDTO<List<StudioTypeLovDTO>> resultResponseDTO = new ResultResponseDTO<>(
                "OK",
                studioTypes);
        return ResponseEntity.ok(resultResponseDTO);
    }
}
