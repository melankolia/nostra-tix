package com.tix.nostra.nostra_tix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.City;
import com.tix.nostra.nostra_tix.dto.TheaterCountCity;
import com.tix.nostra.nostra_tix.repository.CityRepository;
import com.tix.nostra.nostra_tix.service.CityService;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        List<City> cities = cityRepository.findAll();
        return cities;
    }

    @Override
    public List<TheaterCountCity> findAllTheaterCount() {
        return cityRepository.findAllTheaterCount();
    }
}
