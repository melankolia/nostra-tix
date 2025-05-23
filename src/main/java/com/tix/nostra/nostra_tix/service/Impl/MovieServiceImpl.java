package com.tix.nostra.nostra_tix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.repository.MovieRepository;
import com.tix.nostra.nostra_tix.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> findAllUpcoming() {
        return movieRepository.findAllUpcoming();
    }
}
