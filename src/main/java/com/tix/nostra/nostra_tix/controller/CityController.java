package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.domain.City;
import com.tix.nostra.nostra_tix.dto.TheaterCountCity;
import com.tix.nostra.nostra_tix.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public List<City> findAll() {
        return cityService.findAll();
    }

    @GetMapping("/count/theater")
    public List<TheaterCountCity> findAllTheaterCount() {
        return cityService.findAllTheaterCount();
    }
}
