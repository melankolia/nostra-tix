package com.tix.nostra.nostra_tix.service;

import java.util.List;

import com.tix.nostra.nostra_tix.domain.Movie;

public interface MovieService {

    List<Movie> findAll();

    List<Movie> findAllUpcoming();
}
