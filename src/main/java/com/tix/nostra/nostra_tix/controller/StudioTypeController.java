package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.StudioTypeLovDTO;
import com.tix.nostra.nostra_tix.service.StudioTypeService;

@RestController
@RequestMapping("/api/studio-types")
public class StudioTypeController {

    @Autowired
    private StudioTypeService studioTypeService;

    @GetMapping
    public ResponseEntity<List<StudioTypeLovDTO>> findAll() {
        return ResponseEntity.ok(studioTypeService.findAll());
    }
}
