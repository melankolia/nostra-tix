package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.service.MovieService;

import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<ResultResponseDTO<List<Movie>>> findAll() {
        List<Movie> movies = movieService.findAll();
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponseDTO<Movie>> findById(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movie));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<ResultResponseDTO<List<Movie>>> findAllUpcoming() {
        List<Movie> movies = movieService.findAllUpcoming();
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movies));
    }

}
