package com.tix.nostra.nostra_tix.service;

import java.util.List;

import com.tix.nostra.nostra_tix.domain.City;
import com.tix.nostra.nostra_tix.dto.TheaterCountCity;

public interface CityService {
    public List<City> findAll();

    public List<TheaterCountCity> findAllTheaterCount();
}
