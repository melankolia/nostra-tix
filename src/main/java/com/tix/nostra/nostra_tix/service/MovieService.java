package com.tix.nostra.nostra_tix.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.domain.Movie;

public interface MovieService {

    List<Movie> findAll();

    Movie findById(Long number);

    List<Movie> findAllUpcoming();

    Movie updateMovie(Long movieId, MultipartFile file);

    Movie updateMovie(Long movieId, String imageURI);
}
