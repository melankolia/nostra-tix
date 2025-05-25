package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        List<Movie> movies = movieService.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id) {

        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Movie>> findAllUpcoming() {

        List<Movie> movies = movieService.findAllUpcoming();
        return ResponseEntity.ok(movies);
    }

}
