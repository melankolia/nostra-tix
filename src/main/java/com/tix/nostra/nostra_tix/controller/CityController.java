package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<City>> findAll() {
        List<City> cities = cityService.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/count/theater")
    public ResponseEntity<List<TheaterCountCity>> findAllTheaterCount() {
        List<TheaterCountCity> theaterCountCities = cityService.findAllTheaterCount();

        return ResponseEntity.ok(theaterCountCities);
    }
}