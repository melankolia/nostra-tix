package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.dto.MovieRequestDTO;
import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.dto.UpdateMovieImagePresignedDTO;
import com.tix.nostra.nostra_tix.projection.BookingListProjection;
import com.tix.nostra.nostra_tix.service.BookingService;
import com.tix.nostra.nostra_tix.service.MovieService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/middle")
public class MiddleController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/booking")
    public ResponseEntity<ResultResponseDTO<List<BookingListProjection>>> getBookingList() {

        List<BookingListProjection> result = bookingService.findAllBookingList();

        ResultResponseDTO<List<BookingListProjection>> response = new ResultResponseDTO<>("OK", result);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/booking/{bookingId}/completed")
    public ResponseEntity<ResultResponseDTO<Boolean>> completeBooking(@PathVariable @NotNull Long bookingId) {
        Boolean result = bookingService.completeBooking(bookingId);

        ResultResponseDTO<Boolean> response = new ResultResponseDTO<>("OK", result);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/movie/{movieId}/image")
    public ResponseEntity<ResultResponseDTO<Movie>> updateMovieImage(@PathVariable @NotNull Long movieId,
            @RequestParam("file") MultipartFile file) {
        Movie result = movieService.updateMovie(movieId, file);

        ResultResponseDTO<Movie> response = new ResultResponseDTO<>("OK", result);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/movie/{movieId}/presigned-url")
    public ResponseEntity<ResultResponseDTO<Movie>> updateMovieImagePresignedURL(@PathVariable @NotNull Long movieId,
            @RequestBody @Valid UpdateMovieImagePresignedDTO updateMovieImagePresignedDTO) {

        Movie result = movieService.updateMovie(movieId, updateMovieImagePresignedDTO.imageURI());

        ResultResponseDTO<Movie> response = new ResultResponseDTO<>("OK", result);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/movie")
    public ResponseEntity<ResultResponseDTO<List<Movie>>> getAllMovies() {
        List<Movie> movies = movieService.findAll();
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movies));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ResultResponseDTO<Movie>> getMovie(@PathVariable @NotNull Long movieId) {
        Movie movie = movieService.findById(movieId);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movie));
    }

    @PostMapping("/movie")
    public ResponseEntity<ResultResponseDTO<Movie>> createMovie(@RequestBody @Valid MovieRequestDTO movieRequestDTO) {
        Movie movie = movieService.createMovie(movieRequestDTO);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movie));
    }

    @PutMapping("/movie/{movieId}")
    public ResponseEntity<ResultResponseDTO<Movie>> updateMovie(
            @PathVariable Long movieId,
            @RequestBody @Valid MovieRequestDTO movieRequestDTO) {
        Movie movie = movieService.updateMovie(movieId, movieRequestDTO);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", movie));
    }

    @DeleteMapping("/movie/{movieId}")
    public ResponseEntity<ResultResponseDTO<Boolean>> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(new ResultResponseDTO<>("OK", true));
    }
}
