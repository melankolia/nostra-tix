package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.service.MovieService;
import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.dto.MovieRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movies")
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

    @PostMapping
    public ResponseEntity<ResultResponseDTO<Movie>> createMovie(@RequestBody @Valid MovieRequestDTO movieRequestDTO) {
        Movie movie = movieService.createMovie(movieRequestDTO);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponseDTO<Movie>> updateMovie(
            @PathVariable Long id,
            @RequestBody @Valid MovieRequestDTO movieRequestDTO) {
        Movie movie = movieService.updateMovie(id, movieRequestDTO);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movie));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<ResultResponseDTO<Movie>> updateMovieImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        Movie movie = movieService.updateMovie(id, file);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movie));
    }

    @PutMapping("/{id}/image-uri")
    public ResponseEntity<ResultResponseDTO<Movie>> updateMovieImageURI(
            @PathVariable Long id,
            @RequestParam("imageURI") String imageURI) {
        Movie movie = movieService.updateMovie(id, imageURI);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponseDTO<Boolean>> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", true));
    }
}
