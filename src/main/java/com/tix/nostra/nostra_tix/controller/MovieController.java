package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.service.MovieService;
import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;

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

}
