package com.tix.nostra.nostra_tix.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.dto.MovieRequestDTO;

public interface MovieService {

    List<Movie> findAll();

    Movie findById(Long id);

    List<Movie> findAllUpcoming();

    Movie createMovie(MovieRequestDTO movieRequestDTO);

    Movie updateMovie(Long id, MovieRequestDTO movieRequestDTO);

    Movie updateMovie(Long movieId, MultipartFile file);

    Movie updateMovie(Long movieId, String imageURI);

    void deleteMovie(Long id);
}
